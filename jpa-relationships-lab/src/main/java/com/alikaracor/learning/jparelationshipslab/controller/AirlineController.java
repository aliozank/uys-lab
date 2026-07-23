package com.alikaracor.learning.jparelationshipslab.controller;

import com.alikaracor.learning.jparelationshipslab.dto.AirlineRequest;
import com.alikaracor.learning.jparelationshipslab.dto.AirlineResponse;
import com.alikaracor.learning.jparelationshipslab.service.AirlineService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/airlines")
public class AirlineController {

    private final AirlineService airlineService;

    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @PostMapping
    public ResponseEntity<AirlineResponse> createAirline(
            @Valid @RequestBody AirlineRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(airlineService.createAirline(request));
    }

    @GetMapping
    public List<AirlineResponse> getAllAirlines() {
        return airlineService.getAllAirlines();
    }

    @GetMapping("/{airlineId}")
    public AirlineResponse getAirlineById(
            @PathVariable("airlineId") Long airlineId
    ) {
        return airlineService.getAirlineById(airlineId);
    }

    @PutMapping("/{airlineId}")
    public AirlineResponse updateAirline(
            @PathVariable("airlineId") Long airlineId,
            @Valid @RequestBody AirlineRequest request
    ) {
        return airlineService.updateAirline(airlineId, request);
    }

    @DeleteMapping("/{airlineId}")
    public ResponseEntity<Void> deleteAirline(
            @PathVariable("airlineId") Long airlineId
    ) {
        airlineService.deleteAirline(airlineId);

        return ResponseEntity.noContent().build();
    }
}
