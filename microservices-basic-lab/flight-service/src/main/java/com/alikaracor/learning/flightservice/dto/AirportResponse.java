package com.alikaracor.learning.flightservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AirportResponse {

    private String icaoCode;
    private String airportName;
    private String city;
    private String country;
}