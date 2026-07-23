package com.alikaracor.learning.jparelationshipslab.controller;

import com.alikaracor.learning.jparelationshipslab.dto.AircraftRequest;
import com.alikaracor.learning.jparelationshipslab.dto.AircraftResponse;
import com.alikaracor.learning.jparelationshipslab.service.AircraftService;
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
@RequestMapping("/aircraft")
public class AircraftController {

    private final AircraftService aircraftService;

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @PostMapping
    public ResponseEntity<AircraftResponse> createAircraft(
            @Valid @RequestBody AircraftRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(aircraftService.createAircraft(request));
    }

    @GetMapping
    public List<AircraftResponse> getAllAircraft() {
        return aircraftService.getAllAircraft();
    }

    @GetMapping("/{aircraftId}")
    public AircraftResponse getAircraftById(
            @PathVariable("aircraftId") Long aircraftId
    ) {
        return aircraftService.getAircraftById(aircraftId);
    }

    @GetMapping("/by-airline/{airlineId}")
    public List<AircraftResponse> getAircraftByAirline(
            @PathVariable("airlineId") Long airlineId
    ) {
        return aircraftService.getAircraftByAirline(airlineId);
    }

    @PutMapping("/{aircraftId}")
    public AircraftResponse updateAircraft(
            @PathVariable("aircraftId") Long aircraftId,
            @Valid @RequestBody AircraftRequest request
    ) {
        return aircraftService.updateAircraft(aircraftId, request);
    }

    @DeleteMapping("/{aircraftId}")
    public ResponseEntity<Void> deleteAircraft(
            @PathVariable("aircraftId") Long aircraftId
    ) {
        aircraftService.deleteAircraft(aircraftId);

        return ResponseEntity.noContent().build();
    }
}
