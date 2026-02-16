package com.example.parking_management.service;

import com.example.parking_management.AbstractIntegrationTest;
import com.example.parking_management.dto.EntryRequestDto;
import com.example.parking_management.dto.ExitRequestDto;
import com.example.parking_management.model.*;
import com.example.parking_management.repository.ParkingLotRepository;
import com.example.parking_management.repository.ParkingSessionRepository;
import com.example.parking_management.repository.ParkingSlotRepository;
import com.example.parking_management.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.atLeastOnce;

public class ParkingServiceIntegrationTest extends AbstractIntegrationTest {

//    static {
//        // This forces the Java process to use the TCP port we verified
//        System.setProperty("DOCKER_HOST", "tcp://127.0.0.1:2375");
//        System.setProperty("TESTCONTAINERS_CHECKS_DISABLE", "true");
//    }

    @Autowired private ParkingService parkingService;
    @Autowired private ParkingLotRepository lotRepo;
    @Autowired private ParkingSlotRepository slotRepo;
    @Autowired private ParkingSessionRepository sessionRepo;
    @Autowired private VehicleRepository vehicleRepo;

    // This mocks the Kafka producer so no Kafka broker is required for tests


    @BeforeEach
    void cleanDatabase() {
        sessionRepo.deleteAll();
        slotRepo.deleteAll();
        lotRepo.deleteAll();
        vehicleRepo.deleteAll();
    }

    @Test
    void testEndToEndVehicleEntryAndHistory() {
        ParkingLot savedLot = createHelperLot("Integration Test Lot", 50.0);
        ParkingSession session = createHelperSession(savedLot.getId(), "MH-12-TEST", VehicleType.CAR);

        assertNotNull(savedLot.getId());
        assertEquals(SessionStatus.ACTIVE, session.getStatus());
        assertEquals("MH-12-TEST", session.getVehicle().getVehicleNumber());

        Page<ParkingSession> activePage = parkingService.getActiveSessions(0, 10, "MH-12");
        assertEquals(1, activePage.getTotalElements());

        ParkingSlot updatedSlot = slotRepo.findById(session.getParkingSlot().getId()).orElseThrow();
        assertEquals(SlotStatus.OCCUPIED, updatedSlot.getStatus());

        // Optional: Verify that the code at least attempted to send a Kafka event
        verify(eventProducer, atLeastOnce()).sendEvent(anyString());
    }

    @Test
    void testVehicleExit_WithBillingCalculation() {
        ParkingLot savedLot = createHelperLot("R-Mall", 100.0);
        ParkingSession activeSession = createHelperSession(savedLot.getId(), "MH-13-TEST", VehicleType.CAR);

        activeSession.setEntryTime(LocalDateTime.now().minusHours(2));
        sessionRepo.save(activeSession);

        ExitRequestDto exitReq = new ExitRequestDto();
        exitReq.setVehicleNumber("MH-13-TEST");
        ParkingSession completedSession = parkingService.exitVehicle(exitReq);

        assertEquals(200.0, completedSession.getTotalAmount());

        ParkingSlot slot = slotRepo.findById(completedSession.getParkingSlot().getId()).orElseThrow();
        assertEquals(SlotStatus.AVAILABLE, slot.getStatus());

        verify(eventProducer, atLeastOnce()).sendEvent(anyString());
    }

    @Test
    void testVehicleExit_FreeTier() {
        ParkingLot savedLot = createHelperLot("Quick stop", 500.0);
        ParkingSession activeSession = createHelperSession(savedLot.getId(), "MH-14-TEST", VehicleType.BIKE);

        activeSession.setEntryTime(LocalDateTime.now().minusMinutes(15));
        sessionRepo.save(activeSession);

        ExitRequestDto exitReq = new ExitRequestDto();
        exitReq.setVehicleNumber("MH-14-TEST");
        ParkingSession completedSession = parkingService.exitVehicle(exitReq);

        assertEquals(0.0, completedSession.getTotalAmount());
    }

    // --- Helpers kept exactly as provided ---
    private ParkingLot createHelperLot(String name, double price) {
        ParkingLot lot = new ParkingLot();
        lot.setName(name);
        lot.setLocation("Test City");
        lot.setTotalSlots(10);
        lot.setBasePricePerHour(price);
        return parkingService.createParkingLot(lot);
    }

    private ParkingSession createHelperSession(Long lotId, String vehicleNo, VehicleType type) {
        EntryRequestDto request = new EntryRequestDto();
        request.setParkingLotId(lotId);
        request.setVehicleNumber(vehicleNo);
        request.setVehicleType(type);
        return parkingService.enterVehicle(request);
    }
}