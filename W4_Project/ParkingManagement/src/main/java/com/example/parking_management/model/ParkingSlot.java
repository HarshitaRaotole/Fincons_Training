package com.example.parking_management.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class ParkingSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int slotNumber;
    @Enumerated(EnumType.STRING)
    private SlotStatus status;//Available or Occupied

    @ManyToOne
    @JoinColumn(name = "parking_lot_id")
    @JsonIgnore//it prevents infinite recursion in JSON
    @ToString.Exclude
    private ParkingLot parkingLot;
}
