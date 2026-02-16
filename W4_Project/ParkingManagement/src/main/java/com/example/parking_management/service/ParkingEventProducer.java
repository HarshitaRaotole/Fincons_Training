package com.example.parking_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ParkingEventProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "parking-events";

    public void sendEvent(String message) {
        // Asynchronous call to Kafka
        kafkaTemplate.send(TOPIC, message);
        System.out.println("🚀 [KAFKA PRODUCER] Sent: " + message);
    }
}