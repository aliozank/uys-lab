package com.alikaracor.learning.mapstructlab.controller;

import com.alikaracor.learning.mapstructlab.dto.AircraftRequest;
import com.alikaracor.learning.mapstructlab.dto.AircraftResponse;
import com.alikaracor.learning.mapstructlab.service.MappingDemoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mapping-demo")
public class MappingDemoController {

    private final MappingDemoService mappingDemoService;

    public MappingDemoController(MappingDemoService mappingDemoService) {
        this.mappingDemoService = mappingDemoService;
    }

    @PostMapping("/aircraft")
    public AircraftResponse mapAircraft(
            @Valid @RequestBody AircraftRequest request
    ) {
        return mappingDemoService.mapAircraft(request);
    }
}
