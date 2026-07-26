package com.alikaracor.learning.flightservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FlightResponse {

    private String flightNumber;
    private AirportResponse originAirport;
    private AirportResponse destinationAirport;
}