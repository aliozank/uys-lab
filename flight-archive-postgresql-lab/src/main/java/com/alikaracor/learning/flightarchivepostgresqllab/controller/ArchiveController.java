package com.alikaracor.learning.flightarchivepostgresqllab.controller;

import com.alikaracor.learning.flightarchivepostgresqllab.dto.ArchivedFlightResponse;
import com.alikaracor.learning.flightarchivepostgresqllab.service.FlightArchiveService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/archived-flights")
public class ArchiveController {

    private final FlightArchiveService flightArchiveService;

    public ArchiveController(
            FlightArchiveService flightArchiveService
    ) {
        this.flightArchiveService = flightArchiveService;
    }

    @GetMapping
    public List<ArchivedFlightResponse> getAllArchivedFlights() {
        return flightArchiveService.getAllArchivedFlights();
    }

    @GetMapping("/{id}")
    public ArchivedFlightResponse getArchivedFlightById(
            @PathVariable Long id
    ) {
        return flightArchiveService.getArchivedFlightById(id);
    }
}
