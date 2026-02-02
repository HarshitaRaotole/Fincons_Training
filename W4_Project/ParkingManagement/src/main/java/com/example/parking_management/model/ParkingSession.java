package com.example.parking_management.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data // Generates getters and setter
public class ParkingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Vehicle associated with this parking session
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    // Slot in which the vehicle is parked
    @ManyToOne
    @JoinColumn(name = "parking_slot_id")
    private ParkingSlot parkingSlot;

    // Entry and exit timestamps
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private double totalAmount;

    // Current session status (ACTIVE / COMPLETED)
    @Enumerated(EnumType.STRING)
    private SessionStatus status;

}
