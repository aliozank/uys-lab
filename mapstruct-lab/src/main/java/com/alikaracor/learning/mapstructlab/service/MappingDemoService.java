package com.alikaracor.learning.mapstructlab.service;

import com.alikaracor.learning.mapstructlab.dto.AircraftRequest;
import com.alikaracor.learning.mapstructlab.dto.AircraftResponse;
import com.alikaracor.learning.mapstructlab.mapper.AircraftMapper;
import com.alikaracor.learning.mapstructlab.model.Aircraft;
import com.alikaracor.learning.mapstructlab.model.Airline;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
public class MappingDemoService {

    private final AircraftMapper aircraftMapper;
    private final AtomicLong idGenerator = new AtomicLong(1);

    public MappingDemoService(AircraftMapper aircraftMapper) {
        this.aircraftMapper = aircraftMapper;
    }

    public AircraftResponse mapAircraft(AircraftRequest request) {
        Aircraft aircraft = aircraftMapper.toEntity(request);

        aircraft.setId(idGenerator.getAndIncrement());
        aircraft.setOperatorAirline(
                new Airline(10L, "Turkish Airlines")
        );

        return aircraftMapper.toResponse(aircraft);
    }
}
