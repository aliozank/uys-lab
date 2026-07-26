package com.alikaracor.learning.referenceservice.service;

import com.alikaracor.learning.referenceservice.model.Airport;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AirportService {

    private final List<Airport> airports = List.of(
            new Airport(
                    "LTFM",
                    "Istanbul Airport",
                    "Istanbul",
                    "Türkiye"
            ),
            new Airport(
                    "LTFJ",
                    "Sabiha Gökçen International Airport",
                    "Istanbul",
                    "Türkiye"
            ),
            new Airport(
                    "LTAC",
                    "Esenboğa Airport",
                    "Ankara",
                    "Türkiye"
            ),
            new Airport(
                    "EDDF",
                    "Frankfurt Airport",
                    "Frankfurt",
                    "Germany"
            ),
            new Airport(
                    "EGLL",
                    "Heathrow Airport",
                    "London",
                    "United Kingdom"
            )
    );

    public List<Airport> getAllAirports() {
        return airports;
    }

    public Airport getAirportByIcaoCode(String icaoCode) {

        return airports.stream()
                .filter(airport ->
                        airport.getIcaoCode().equalsIgnoreCase(icaoCode)
                )
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Bu ICAO koduyla eşleşen havalimanı bulunamadı"
                ));
    }
}