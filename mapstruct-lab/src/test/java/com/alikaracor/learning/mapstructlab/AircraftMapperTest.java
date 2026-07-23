package com.alikaracor.learning.mapstructlab;

import com.alikaracor.learning.mapstructlab.dto.AircraftRequest;
import com.alikaracor.learning.mapstructlab.dto.AircraftResponse;
import com.alikaracor.learning.mapstructlab.mapper.AircraftMapper;
import com.alikaracor.learning.mapstructlab.model.Aircraft;
import com.alikaracor.learning.mapstructlab.model.Airline;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class AircraftMapperTest {

    @Autowired
    private AircraftMapper aircraftMapper;

    @Test
    void shouldMapRequestEntityResponseAndUpdateExistingEntity() {
        AircraftRequest request = new AircraftRequest();
        request.setRegistrationNumber("TC-MAP");
        request.setModel("Airbus A321neo");
        request.setCapacity(240);

        Aircraft aircraft = aircraftMapper.toEntity(request);

        assertNotNull(aircraft);
        assertEquals("TC-MAP", aircraft.getRegistrationNumber());
        assertEquals("Airbus A321neo", aircraft.getModelName());
        assertEquals(240, aircraft.getSeatCapacity());

        aircraft.setId(1L);
        aircraft.setOperatorAirline(new Airline(10L, "Turkish Airlines"));

        AircraftResponse response = aircraftMapper.toResponse(aircraft);

        assertEquals(1L, response.getAircraftId());
        assertEquals(10L, response.getOperatorAirlineId());
        assertEquals(
                "Turkish Airlines",
                response.getOperatorAirlineName()
        );

        AircraftRequest updateRequest = new AircraftRequest();
        updateRequest.setRegistrationNumber("TC-MAP");
        updateRequest.setModel("Airbus A320neo");
        updateRequest.setCapacity(186);

        aircraftMapper.updateEntity(updateRequest, aircraft);

        assertEquals(1L, aircraft.getId());
        assertEquals("Airbus A320neo", aircraft.getModelName());
        assertEquals(186, aircraft.getSeatCapacity());
        assertEquals(10L, aircraft.getOperatorAirline().getId());
    }
}
