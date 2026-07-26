package com.alikaracor.learning.referenceservice.controller;

import com.alikaracor.learning.referenceservice.model.Airport;
import com.alikaracor.learning.referenceservice.service.AirportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping
    public List<Airport> getAllAirports() {
        return airportService.getAllAirports();
    }

    @GetMapping("/{icaoCode}")
    public Airport getAirportByIcaoCode(
            @PathVariable String icaoCode
    ) {

        return airportService.getAirportByIcaoCode(icaoCode);
    }
}