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
    // Fetch all parking lots
    public List<ParkingLot> getAllLots(){
        return lotRepo.findAll();
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

        return sessionRepo.save(session);
    }

    // Vehicle exit logic
    @Transactional
    public ParkingSession exitVehicle(ExitRequestDto request){
        ParkingSession session = sessionRepo.findByVehicle_VehicleNumberAndStatus(request.getVehicleNumber(),
                SessionStatus.ACTIVE)
                .orElseThrow(()-> new ParkingException("No Active Session found for this vehicle"));
        session.setExitTime(LocalDateTime.now());

        //pricing Logic
        calculateCharges(session);
        session.setStatus(SessionStatus.COMPLETED);
        ParkingSlot slot = session.getParkingSlot();
        slot.setStatus(SlotStatus.AVAILABLE);
        slotRepo.save(slot);

        return sessionRepo.save(session);

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

    // Fetch active parking sessions
    public List<ParkingSession> getActiveSessions(){

        return sessionRepo.findByStatus(SessionStatus.ACTIVE);
    }

    // Fetch slots for a parking lot
    public List<ParkingSlot> getSlotsForLot(Long lotId){
        if(!lotRepo.existsById(lotId)){
            throw new ParkingException("Parking Lot not found");
        }
        return slotRepo.findByParkingLotId(lotId);
    }

    // Fetch parking session history
    public List<ParkingSession> getSessionHistory(){
        return sessionRepo.findAll();
    }

}
