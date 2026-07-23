package com.alikaracor.learning;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import java.util.Random;

public class FlightDataGenerator {

    private static final String[] AIRLINE_CODES = {
            "TK",
            "PC",
            "XQ",
            "VF"
    };

    private static final String[] AIRCRAFT_TYPES = {

            "Airbus A321",
            "Airbus A330",
            "Boeing 737-800",
            "Boeing 737-700"

    };

    private static final String[] AIRPORT_ICAO_CODES = {
            "LTFM",
            "LTFJ",
            "LTAC",
            "EDDF",
            "EGLL",
            "OMDB"
    };


    private final Random random = new Random();

    public FlightData generateFlight() {

        String airlineCode = AIRLINE_CODES[random.nextInt(AIRLINE_CODES.length)];

        int randomFlightNumber = 1000 + random.nextInt(9000);

        String flightNumber = airlineCode + randomFlightNumber;

        String aircraftType = AIRCRAFT_TYPES[random.nextInt(AIRCRAFT_TYPES.length)];

        String originIcao = AIRPORT_ICAO_CODES[random.nextInt(AIRPORT_ICAO_CODES.length)];

        String destinationIcao;

        do {
            destinationIcao = AIRPORT_ICAO_CODES[random.nextInt(AIRPORT_ICAO_CODES.length)];

        } while (originIcao.equals(destinationIcao));

        LocalTime departureTime = LocalTime.of(
                random.nextInt(20),
                random.nextInt(60)
        );

        int flightDuration = 1 + random.nextInt(4);

        LocalTime arrivalTime =
                departureTime.plusHours(flightDuration);

        LocalDate flightDate = LocalDate.now().plusDays(random.nextInt(31));


        FlightType[] flightTypes = FlightType.values();

        FlightType flightType =
                flightTypes[random.nextInt(flightTypes.length)];

        return new FlightData(
                flightNumber,
                airlineCode,
                aircraftType,
                originIcao,
                destinationIcao,
                flightDate,
                departureTime,
                arrivalTime,
                flightType
        );



    }

    public List<FlightData> generateFlights(int count) {

        if (count <= 0) {
            throw new IllegalArgumentException(
                    "Üretilecek uçuş sayısı sıfırdan büyük olmalıdır"
            );
        }

        List<FlightData> flights = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            FlightData flight = generateFlight();
            flights.add(flight);
        }

        return flights;
    }
}
