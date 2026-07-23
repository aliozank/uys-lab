package com.alikaracor.learning.rediscachelab.Controller;

import com.alikaracor.learning.rediscachelab.Service.AirlineLookupService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/airlines")
public class AirlineController {

    private final AirlineLookupService airlineLookupService;

    public AirlineController(
            AirlineLookupService airlineLookupService
    ) {
        this.airlineLookupService = airlineLookupService;
    }

    @GetMapping("/{airlineId}")
    public String getAirlineById(
            @PathVariable Long airlineId
    ) {
        return airlineLookupService.getAirlineById(airlineId);
    }

    @PutMapping("/{airlineId}")
    public String updateAirline(
            @PathVariable Long airlineId,
            @RequestParam String airlineName
    ) {
        return airlineLookupService.updateAirline(
                airlineId,
                airlineName
        );
    }

    @DeleteMapping("/{airlineId}")
    public void deleteAirline(
            @PathVariable Long airlineId
    ) {
        airlineLookupService.deleteAirline(airlineId);
    }
}