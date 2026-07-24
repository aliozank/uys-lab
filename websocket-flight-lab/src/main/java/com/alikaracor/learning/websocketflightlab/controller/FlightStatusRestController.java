package com.alikaracor.learning.websocketflightlab.controller;

import com.alikaracor.learning.websocketflightlab.model.FlightStatusMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/flights")
public class FlightStatusRestController {

    private final SimpMessagingTemplate messagingTemplate;

    public FlightStatusRestController(
            SimpMessagingTemplate messagingTemplate
    ) {
        this.messagingTemplate = messagingTemplate;
    }

    @PutMapping("/{flightNumber}/status")
    public ResponseEntity<String> updateFlightStatus(
            @PathVariable String flightNumber,
            @RequestParam String status
    ) {
        FlightStatusMessage message =
                new FlightStatusMessage(flightNumber, status);

        messagingTemplate.convertAndSend(
                "/topic/flight-status",
                message
        );

        return ResponseEntity.ok("Uçuş durumu güncellendi");
    }
}