package com.alikaracor.learning.jparelationshipslab;

import com.alikaracor.learning.jparelationshipslab.dto.AircraftRequest;
import com.alikaracor.learning.jparelationshipslab.dto.AircraftResponse;
import com.alikaracor.learning.jparelationshipslab.dto.AirlineRequest;
import com.alikaracor.learning.jparelationshipslab.dto.AirlineResponse;
import com.alikaracor.learning.jparelationshipslab.repository.AircraftRepository;
import com.alikaracor.learning.jparelationshipslab.service.AircraftService;
import com.alikaracor.learning.jparelationshipslab.service.AirlineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class RelationshipIntegrationTest {

    @Autowired
    private AirlineService airlineService;

    @Autowired
    private AircraftService aircraftService;

    @Autowired
    private AircraftRepository aircraftRepository;

    @Test
    void shouldCreateAircraftWithAirlineRelationshipAndProtectAirline() {
        AirlineRequest airlineRequest = new AirlineRequest();
        airlineRequest.setName("Relationship Test Airline");
        airlineRequest.setIataCode("Q7");
        airlineRequest.setIcaoCode("QXZ");

        AirlineResponse airline = airlineService.createAirline(airlineRequest);

        AircraftRequest aircraftRequest = new AircraftRequest();
        aircraftRequest.setRegistrationNumber("TC-RLT");
        aircraftRequest.setModel("Relationship Test Aircraft");
        aircraftRequest.setSeatCapacity(180);
        aircraftRequest.setOperatorAirlineId(airline.getId());

        AircraftResponse aircraft =
                aircraftService.createAircraft(aircraftRequest);

        assertEquals(airline.getId(), aircraft.getOperatorAirlineId());
        assertEquals(airline.getName(), aircraft.getOperatorAirlineName());

        List<AircraftResponse> allAircraft =
                aircraftService.getAllAircraft();

        assertTrue(allAircraft.stream().anyMatch(response ->
                response.getId().equals(aircraft.getId())
                        && response.getOperatorAirlineName()
                        .equals(airline.getName())
        ));

        List<AircraftResponse> fleet =
                aircraftService.getAircraftByAirline(airline.getId());

        assertEquals(1, fleet.size());
        assertEquals(aircraft.getId(), fleet.get(0).getId());
        assertEquals(
                1,
                aircraftRepository.findByOperatorAirline_Id(airline.getId())
                        .size()
        );

        ResponseStatusException exception = assertThrows(
                ResponseStatusException.class,
                () -> airlineService.deleteAirline(airline.getId())
        );

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
    }
}
