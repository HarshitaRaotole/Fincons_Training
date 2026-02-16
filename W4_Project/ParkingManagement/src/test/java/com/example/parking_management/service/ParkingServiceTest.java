package com.example.parking_management.service;

import com.example.parking_management.dto.EntryRequestDto;
import com.example.parking_management.dto.ExitRequestDto;
import com.example.parking_management.exception.ParkingException;
import com.example.parking_management.model.*;
import com.example.parking_management.repository.ParkingLotRepository;
import com.example.parking_management.repository.ParkingSessionRepository;
import com.example.parking_management.repository.ParkingSlotRepository;
import com.example.parking_management.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {

    @Mock private ParkingLotRepository lotRepo;
    @Mock private ParkingSlotRepository slotRepo;
    @Mock private VehicleRepository vehicleRepo;
    @Mock private ParkingSessionRepository sessionRepo;


    @Mock private ParkingEventProducer eventProducer;

    @InjectMocks
    private ParkingService parkingService;

    private ParkingLot parkingLot;
    private ParkingSlot parkingSlot;
    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        parkingLot = new ParkingLot();
        parkingLot.setId(1L);
        parkingLot.setName("Test Lot");
        parkingLot.setTotalSlots(5);
        parkingLot.setBasePricePerHour(100.0);

        parkingSlot = new ParkingSlot();
        parkingSlot.setId(1L);
        parkingSlot.setSlotNumber(1);
        parkingSlot.setStatus(SlotStatus.AVAILABLE);
        parkingSlot.setParkingLot(parkingLot);

        vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setVehicleNumber("MH-12-AB-1234");
        vehicle.setVehicleType(VehicleType.CAR);
    }

    @Test
    void testCreateParkingLot_Success() {
        when(lotRepo.save(any(ParkingLot.class))).thenReturn(parkingLot);
        ParkingLot createdLot = parkingService.createParkingLot(parkingLot);
        assertNotNull(createdLot);
        assertEquals("Test Lot", createdLot.getName());
        verify(slotRepo, times(5)).save(any(ParkingSlot.class));
    }

    @Test
    void testEnterVehicle_Success() {
        EntryRequestDto request = new EntryRequestDto();
        request.setParkingLotId(1L);
        request.setVehicleNumber("MH-12-AB-1234");
        request.setVehicleType(VehicleType.CAR);

        when(lotRepo.existsById(1L)).thenReturn(true);
        when(sessionRepo.findByVehicle_VehicleNumberAndStatus(anyString(), eq(SessionStatus.ACTIVE))).thenReturn(Optional.empty());
        when(vehicleRepo.findByVehicleNumber(anyString())).thenReturn(Optional.of(vehicle));
        when(slotRepo.findFirstByParkingLotIdAndStatus(1L, SlotStatus.AVAILABLE)).thenReturn(Optional.of(parkingSlot));

        when(sessionRepo.save(any(ParkingSession.class))).thenAnswer(i -> {
            ParkingSession s = i.getArgument(0);
            s.setId(100L);
            return s;
        });

        ParkingSession session = parkingService.enterVehicle(request);

        assertNotNull(session);
        assertEquals(SessionStatus.ACTIVE, session.getStatus());

        // Verify
        verify(eventProducer, times(1)).sendEvent(anyString());
    }

    @Test
    void testEnterVehicle_LotFull_ThrowsException() {
        EntryRequestDto request = new EntryRequestDto();
        request.setParkingLotId(1L);
        request.setVehicleNumber("MH-12-AB-1234");
        request.setVehicleType(VehicleType.CAR);

        when(lotRepo.existsById(1L)).thenReturn(true);
        when(sessionRepo.findByVehicle_VehicleNumberAndStatus(anyString(), eq(SessionStatus.ACTIVE))).thenReturn(Optional.empty());
        when(vehicleRepo.findByVehicleNumber(anyString())).thenReturn(Optional.of(vehicle));
        when(slotRepo.findFirstByParkingLotIdAndStatus(1L, SlotStatus.AVAILABLE)).thenReturn(Optional.empty());

        assertThrows(ParkingException.class, () -> parkingService.enterVehicle(request));
    }

    @Test
    void testExitVehicle_Success() {
        ExitRequestDto request = new ExitRequestDto();
        request.setVehicleNumber("MH-12-AB-1234");

        ParkingSession activeSession = new ParkingSession();
        activeSession.setId(100L);
        activeSession.setVehicle(vehicle);
        activeSession.setParkingSlot(parkingSlot);
        activeSession.setStatus(SessionStatus.ACTIVE);
        activeSession.setEntryTime(LocalDateTime.now().minusHours(2));

        when(sessionRepo.findByVehicle_VehicleNumberAndStatus("MH-12-AB-1234", SessionStatus.ACTIVE)).thenReturn(Optional.of(activeSession));
        when(sessionRepo.save(any(ParkingSession.class))).thenAnswer(i -> i.getArgument(0));

        ParkingSession result = parkingService.exitVehicle(request);

        assertEquals(SessionStatus.COMPLETED, result.getStatus());
        assertEquals(200.0, result.getTotalAmount());

        // Verification of the event logic
        verify(eventProducer, times(1)).sendEvent(anyString());
    }

    @Test
    void testGetAllLots_WithSearch() {
        Page<ParkingLot> page = new PageImpl<>(Collections.singletonList(parkingLot));
        when(lotRepo.findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(eq("Test"), eq("Test"), any(Pageable.class))).thenReturn(page);

        Page<ParkingLot> result = parkingService.getAllLots(0, 5, "Test");
        assertEquals(1, result.getTotalElements());
    }
}