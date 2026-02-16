package com.example.parking_management.service;

import com.example.parking_management.dto.EntryRequestDto;
import com.example.parking_management.dto.ExitRequestDto;
import com.example.parking_management.exception.ParkingException;
import com.example.parking_management.model.*;
import com.example.parking_management.repository.ParkingLotRepository;
import com.example.parking_management.repository.ParkingSessionRepository;
import com.example.parking_management.repository.ParkingSlotRepository;
import com.example.parking_management.repository.VehicleRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParkingService {
    @Autowired private ParkingLotRepository lotRepo;
    @Autowired private ParkingSlotRepository slotRepo;
    @Autowired private VehicleRepository vehicleRepo;
    @Autowired private ParkingSessionRepository sessionRepo;
    @Autowired private ParkingEventProducer eventProducer;

    // Create parking lot and initialize slots
    public ParkingLot createParkingLot(ParkingLot lot){
        ParkingLot savedLot = lotRepo.save(lot);

        for(int i =1 ; i<= lot.getTotalSlots(); i++){
            ParkingSlot slot = new ParkingSlot();
            slot.setSlotNumber(i);
            slot.setStatus(SlotStatus.AVAILABLE);
            slot.setParkingLot(savedLot);
            slotRepo.save(slot);
        }
        return savedLot;
    }
    // Fetch all parking lots(Paginated)
    public Page<ParkingLot> getAllLots(int pageNo, int pageSize, String keyword){
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").ascending());

        if (keyword != null && !keyword.isEmpty()) {
            return lotRepo.findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(keyword, keyword, pageable);
        }
        return lotRepo.findAll(pageable);

    }


    //Vehicle Entry logic
    @Transactional
    public ParkingSession enterVehicle(EntryRequestDto request) {

        // 1️ Validate parking lot
        if (!lotRepo.existsById(request.getParkingLotId())) {
            throw new ParkingException("Parking lot not found");
        }

        // 2️  Check active session
        if (sessionRepo.findByVehicle_VehicleNumberAndStatus(
                request.getVehicleNumber(),
                SessionStatus.ACTIVE
        ).isPresent()) {
            throw new ParkingException("Vehicle already exists in active session");
        }

        // 3️ Check if vehicle exists
        Vehicle vehicle = vehicleRepo
                .findByVehicleNumber(request.getVehicleNumber())
                .orElse(null);

        // 4️ Validate vehicle type
        if (vehicle != null &&
                !vehicle.getVehicleType().equals(request.getVehicleType())) {
            throw new ParkingException(
                    "Vehicle already registered as " + vehicle.getVehicleType()
            );
        }

        // 5️ Check slot availability
        ParkingSlot slot = slotRepo
                .findFirstByParkingLotIdAndStatus(
                        request.getParkingLotId(),
                        SlotStatus.AVAILABLE
                )
                .orElseThrow(() -> new ParkingException("Parking lot is full"));

        // 6 Create vehicle if new
        if (vehicle == null) {
            vehicle = new Vehicle();
            vehicle.setVehicleNumber(request.getVehicleNumber());
            vehicle.setVehicleType(request.getVehicleType());
            vehicle = vehicleRepo.save(vehicle);
        }

        // 7 Assign slot
        slot.setStatus(SlotStatus.OCCUPIED);
        slotRepo.save(slot);

        ParkingSession session = new ParkingSession();
        session.setVehicle(vehicle);
        session.setParkingSlot(slot);
        session.setEntryTime(LocalDateTime.now());
        session.setStatus(SessionStatus.ACTIVE);

        ParkingSession savedSession = sessionRepo.save(session);

        // KAFKA EVENT: Sending a formatted string for the Frontend
        String eventMessage = "ENTRY: Vehicle " + savedSession.getVehicle().getVehicleNumber() +
                " entered Slot " + slot.getSlotNumber();
        eventProducer.sendEvent(eventMessage);

        return savedSession;
    }

    // Vehicle exit logic
    @Transactional
    public ParkingSession exitVehicle(ExitRequestDto request){
        ParkingSession session = sessionRepo.findByVehicle_VehicleNumberAndStatus(request.getVehicleNumber(),
                        SessionStatus.ACTIVE)
                .orElseThrow(() -> new ParkingException("No Active Session found for this vehicle"));

        session.setExitTime(LocalDateTime.now());
        calculateCharges(session); // Logic for dynamic pricing
        session.setStatus(SessionStatus.COMPLETED);

        ParkingSlot slot = session.getParkingSlot();
        slot.setStatus(SlotStatus.AVAILABLE);
        slotRepo.save(slot);

        ParkingSession savedSession = sessionRepo.save(session);

        // KAFKA EVENT: Sending exit details
        String eventMessage = "EXIT: Vehicle " + savedSession.getVehicle().getVehicleNumber() +
                " exited. Charges: ₹" + savedSession.getTotalAmount();
        eventProducer.sendEvent(eventMessage);

        return savedSession;
    }

    // Pricing calculation based on duration and occupancy
    private void calculateCharges(ParkingSession session){
        long durationMinutes = Duration.between(session.getEntryTime(),
                session.getExitTime()).toMinutes();
        double billableHours = 0;
        // First 30 minutes free
        if(durationMinutes > 30){
            long chargedMinutes = durationMinutes - 30;
            //Rounding logic
            double hoursFraction = chargedMinutes/60.0;
            billableHours = Math.ceil(hoursFraction);
            if(billableHours < 1 ) billableHours = 1;
        }

        long lotId= session.getParkingSlot().getParkingLot().getId();
        long totalSlots = session.getParkingSlot().getParkingLot().getTotalSlots();

        long activeSessions = sessionRepo.countByParkingSlot_ParkingLot_IdAndStatus(lotId,SessionStatus.ACTIVE);

        double occupancyPercentage = ((double) activeSessions / totalSlots) * 100;

        // Dynamic pricing based on occupancy
        double multiplier = 1.0;
        if(occupancyPercentage > 80) multiplier = 1.5;
        else if (occupancyPercentage > 50) multiplier = 1.25;

        double basePrice = session.getParkingSlot().getParkingLot().getBasePricePerHour();
        double finalAmount = billableHours * basePrice * multiplier;

        session.setTotalAmount(finalAmount);
    }

    // Fetch active parking sessions(paginated)
    public Page<ParkingSession> getActiveSessions(int pageNo, int pageSize, String keyword){
        //sort by EntryTime DESC
        Pageable pageable = PageRequest.of(pageNo, pageSize,Sort.by("entryTime").descending());
        if (keyword != null && !keyword.isEmpty()){
            return sessionRepo.findByStatusAndVehicle_VehicleNumberContainingIgnoreCase(
                    SessionStatus.ACTIVE, keyword, pageable
            );
        }

        return sessionRepo.findByStatus(SessionStatus.ACTIVE,pageable);
    }
    // History with pagination
    public Page<ParkingSession> getSessionHistory(int pageNo, int pageSize, String keyword){
        //sort by ID Descending(Latest record first)
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by("id").descending());
        if (keyword != null && !keyword.isEmpty()){
            return sessionRepo.findByVehicle_VehicleNumberContainingIgnoreCase(keyword,pageable);
        }
        return sessionRepo.findAll(pageable);

    }
    public List<ParkingSlot> getSlotsForLot(Long lotId){
        if(!lotRepo.existsById(lotId)){
            throw new ParkingException("Parking Lot not found");
        }
        return slotRepo.findByParkingLotId(lotId);
    }
}
