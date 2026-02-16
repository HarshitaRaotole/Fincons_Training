package com.example.parking_management.repository;

import com.example.parking_management.model.ParkingSession;
import com.example.parking_management.model.SessionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingSessionRepository extends JpaRepository<ParkingSession, Long> {
    Optional<ParkingSession> findByVehicle_VehicleNumberAndStatus(String vehicleNumber, SessionStatus status);

    long countByParkingSlot_ParkingLot_IdAndStatus(Long lotId, SessionStatus status);

    //1 Get all Active sessions (Paginated)
    Page<ParkingSession> findByStatus(SessionStatus status, Pageable pageable);

    //2. Search Active Sessions by vehicle number(Paginated)
    Page<ParkingSession>
    findByStatusAndVehicle_VehicleNumberContainingIgnoreCase(
            SessionStatus status,
            String vehicleNumber,
            Pageable pageable
    );

    //3. Search History (All status) by vehicle Number (Paginated)
    Page<ParkingSession> findByVehicle_VehicleNumberContainingIgnoreCase(
            String vehicleNumber,
            Pageable pageable
    );

}
