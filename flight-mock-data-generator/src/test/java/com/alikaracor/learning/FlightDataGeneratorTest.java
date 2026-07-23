package com.alikaracor.learning;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightDataGeneratorTest {

    private final FlightDataGenerator generator =
            new FlightDataGenerator();

    @Test
    void shouldGenerateRequestedNumberOfFlights() {

        List<FlightData> flights =
                generator.generateFlights(50);

        assertEquals(50, flights.size());
    }

    @Test
    void originAndDestinationShouldBeDifferent() {

        List<FlightData> flights =
                generator.generateFlights(100);

        boolean anyInvalidFlight = flights.stream()
                .anyMatch(flight ->
                        flight.getOriginIcao()
                                .equals(flight.getDestinationIcao())
                );

        assertFalse(anyInvalidFlight);
    }

    @Test
    void flightNumberShouldMatchRequiredFormat() {

        List<FlightData> flights =
                generator.generateFlights(100);

        boolean allFlightNumbersValid = flights.stream()
                .allMatch(flight ->
                        flight.getFlightNumber()
                                .matches("[A-Z]{2}\\d{4}")
                );

        assertTrue(allFlightNumbersValid);
    }

    @Test
    void shouldRejectZeroFlightCount() {

        assertThrows(
                IllegalArgumentException.class,
                () -> generator.generateFlights(0)
        );
    }

    @Test
    void shouldRejectNegativeFlightCount() {

        assertThrows(
                IllegalArgumentException.class,
                () -> generator.generateFlights(-10)
        );
    }
}
