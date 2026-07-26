package com.alikaracor.learning.flightarchivepostgresqllab.controller;

import com.alikaracor.learning.flightarchivepostgresqllab.dto.FlightCompletedRequest;
import com.alikaracor.learning.flightarchivepostgresqllab.event.FlightCompletedEvent;
import com.alikaracor.learning.flightarchivepostgresqllab.publisher.FlightEventPublisher;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/flight-events")
public class TestFlightEventController {

    private final FlightEventPublisher flightEventPublisher;

    public TestFlightEventController(
            FlightEventPublisher flightEventPublisher
    ) {
        this.flightEventPublisher = flightEventPublisher;
    }

    @PostMapping("/completed")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public FlightCompletedEvent publishCompletedFlight(
            @Valid @RequestBody FlightCompletedRequest request
    ) {

        return flightEventPublisher.publishCompletedFlight(request);
    }
}
