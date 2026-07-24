package com.alikaracor.learning.kafkabasiclab.controller;

import com.alikaracor.learning.kafkabasiclab.model.FlightEvent;
import com.alikaracor.learning.kafkabasiclab.producer.FlightEventProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flight-events")
public class FlightEventController {

    private final FlightEventProducer flightEventProducer;

    public FlightEventController(FlightEventProducer flightEventProducer) {
        this.flightEventProducer = flightEventProducer;
    }

    @PostMapping
    public ResponseEntity<String> publishFlightEvent(
            @RequestBody FlightEvent flightEvent
    ) {
        flightEventProducer.sendFlightEvent(flightEvent);

        return ResponseEntity
                .accepted()
                .body("Uçuş olayı Kafka'ya gönderildi");
    }
}