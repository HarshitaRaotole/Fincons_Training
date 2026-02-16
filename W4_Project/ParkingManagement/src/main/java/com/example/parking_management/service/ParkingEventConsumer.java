package com.example.parking_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ParkingEventConsumer {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "parking-events", groupId = "parking-system-group")
    public void consume(String message) {
        try {
            System.out.println("📥 [KAFKA CONSUMER] Received: " + message);
            // Pushing to the WebSocket topic for the Frontend
            messagingTemplate.convertAndSend("/topic/updates", message);
        } catch (Exception e) {
            System.err.println("❌ Error bridging Kafka to WebSocket: " + e.getMessage());
        }
    }
}