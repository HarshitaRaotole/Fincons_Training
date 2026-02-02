package com.example.parking_management.repository;

import com.example.parking_management.model.ParkingSlot;
import com.example.parking_management.model.SlotStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
    Optional<ParkingSlot> findFirstByParkingLotIdAndStatus(Long parkingLotId, SlotStatus status);
    List<ParkingSlot> findByParkingLotId(Long parkingLotId);
}

