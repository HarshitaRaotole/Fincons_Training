package com.example.parking_management.dto;

import lombok.Data;

@Data  // Generates getters and setters
public class ExitRequestDto {
    // Vehicle number used to identify the active parking session
    private String vehicleNumber;
}
