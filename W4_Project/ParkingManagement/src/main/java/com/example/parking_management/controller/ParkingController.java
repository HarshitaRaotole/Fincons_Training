package com.example.parking_management.controller;

import com.example.parking_management.dto.EntryRequestDto;
import com.example.parking_management.dto.ExitRequestDto;
import com.example.parking_management.model.ParkingLot;
import com.example.parking_management.model.ParkingSession;
import com.example.parking_management.model.ParkingSlot;
import com.example.parking_management.service.ParkingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")// Allow Angular frontend requests
public class ParkingController {
    @Autowired private ParkingService service; // Handles business logic

    // Create a new parking lot
    @PostMapping("/parking-lots")
    public ResponseEntity<ParkingLot> createLot( @Valid @RequestBody ParkingLot lot){
        return ResponseEntity.ok(service.createParkingLot(lot));
    }

    // Get all parking lots
    @GetMapping("/parking-lots")
    public ResponseEntity<Page<ParkingLot>> getLots(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
           @RequestParam(required = false) String keyword
    ){
        return ResponseEntity.ok(service.getAllLots(page, size, keyword));
    }

    // Handle vehicle entry and create parking session
    @PostMapping("/parking/entry")
    public ResponseEntity<ParkingSession> enterVehicle( @Valid @RequestBody EntryRequestDto request){
        return ResponseEntity.ok(service.enterVehicle(request));
    }

    // Handle vehicle exit
    @PostMapping("/parking/exit")
    public ResponseEntity<ParkingSession> exitVehicle(@RequestBody ExitRequestDto request){
        return ResponseEntity.ok(service.exitVehicle(request));
    }

    @GetMapping("/sessions/active")
    public ResponseEntity<Page<ParkingSession>> getActiveSessions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword
    ){
        return ResponseEntity.ok(service.getActiveSessions(page, size, keyword));
    }

    // Get currently active parking sessions
    @GetMapping("/parking-lots/{id}/slots")
    public ResponseEntity<List<ParkingSlot>> getSlotsForLot(@PathVariable Long id){
        return ResponseEntity.ok(service.getSlotsForLot(id));
    }

    // Get parking session history
    @GetMapping("/sessions/history")
    public ResponseEntity<Page<ParkingSession>> getSessionHistory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword
    ){
        return ResponseEntity.ok(service.getSessionHistory(page, size, keyword));
    }
}
