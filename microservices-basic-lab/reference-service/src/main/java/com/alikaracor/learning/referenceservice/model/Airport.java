package com.alikaracor.learning.referenceservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Airport {

    private String icaoCode;
    private String airportName;
    private String city;
    private String country;
}