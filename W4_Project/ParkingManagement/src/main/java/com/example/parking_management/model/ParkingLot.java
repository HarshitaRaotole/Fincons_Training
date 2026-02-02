package com.example.parking_management.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name","location"})// Prevent duplicate parking lots
        }
)
@Data //it generates getters,setters,toString automatically using lombok
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Parking lot name cannot be empty")
    private String name;

    @NotBlank(message = "Location cannot be empty")
    private String location;

    @Min(value = 1, message = "totalSlots must be at least 1")
    private int totalSlots;
    private double basePricePerHour;

    private LocalDateTime createdAt = LocalDateTime.now();
}
