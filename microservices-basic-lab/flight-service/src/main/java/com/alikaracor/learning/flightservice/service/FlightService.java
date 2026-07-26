package com.alikaracor.learning.flightservice.service;

import com.alikaracor.learning.flightservice.client.ReferenceServiceClient;
import com.alikaracor.learning.flightservice.dto.AirportResponse;
import com.alikaracor.learning.flightservice.dto.FlightRequest;
import com.alikaracor.learning.flightservice.dto.FlightResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FlightService {

    private final ReferenceServiceClient referenceServiceClient;

    public FlightService(
            ReferenceServiceClient referenceServiceClient
    ) {
        this.referenceServiceClient = referenceServiceClient;
    }

    public FlightResponse createFlight(FlightRequest flightRequest) {

        if (flightRequest.getOriginIcaoCode().equalsIgnoreCase(
                flightRequest.getDestinationIcaoCode()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Kalkış ve varış havalimanı aynı olamaz"
            );
        }

        AirportResponse originAirport =
                referenceServiceClient.getAirportByIcaoCode(
                        flightRequest.getOriginIcaoCode()
                );

        AirportResponse destinationAirport =
                referenceServiceClient.getAirportByIcaoCode(
                        flightRequest.getDestinationIcaoCode()
                );

        return new FlightResponse(
                flightRequest.getFlightNumber(),
                originAirport,
                destinationAirport
        );
    }
}