package com.example.parking_management.repository;

import com.example.parking_management.model.ParkingSession;
import com.example.parking_management.model.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingSessionRepository extends JpaRepository<ParkingSession, Long> {
    Optional<ParkingSession> findByVehicle_VehicleNumberAndStatus(String vehicleNumber, SessionStatus status);

    long countByParkingSlot_ParkingLot_IdAndStatus(Long lotId, SessionStatus status);
    List<ParkingSession> findByStatus(SessionStatus status);

}
