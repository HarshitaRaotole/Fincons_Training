package com.example.parking_management.dto;

import com.example.parking_management.model.VehicleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data // Generates getters, setters, toString, equals, and hashCode
public class EntryRequestDto {
    // Vehicle number entered by the user (validated format)
    @NotBlank(message = "Vehicle number must not be empty")
    @Pattern(
            regexp = "^[A-Z]{2}-?\\d{2}-?[A-Z]{1,2}-?\\d{4}$",
            message = "Invalid vehicle number format (e.g. MH-12-AB-1234)"
    )
    private String vehicleNumber;

    // Type of vehicle (CAR / BIKE)
    @NotNull
    private VehicleType vehicleType;

    // Parking lot where the vehicle wants to park
    @NotNull(message = "Parking lot id is required")
    private Long parkingLotId;

}
