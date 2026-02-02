package com.example.parking_management.model;

import jakarta.persistence.*;
import lombok.Data;

import javax.annotation.processing.Generated;
@Entity
@Data
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String vehicleNumber;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
}



