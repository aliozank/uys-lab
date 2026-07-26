package com.alikaracor.learning.flightservice.controller;

import com.alikaracor.learning.flightservice.dto.FlightRequest;
import com.alikaracor.learning.flightservice.dto.FlightResponse;
import com.alikaracor.learning.flightservice.service.FlightService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FlightResponse createFlight(
            @Valid @RequestBody FlightRequest flightRequest
    ) {

        return flightService.createFlight(flightRequest);
    }
}