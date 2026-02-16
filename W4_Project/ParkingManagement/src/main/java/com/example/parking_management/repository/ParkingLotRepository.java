package com.example.parking_management.repository;

import com.example.parking_management.model.ParkingLot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Long> {
    //Return a page instead of list
    Page<ParkingLot>
    findByNameContainingIgnoreCaseOrLocationContainingIgnoreCase(
            String name,
            String location,
            Pageable pageable
    );

}

