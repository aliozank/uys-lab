package com.alikaracor.learning.flightservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FlightRequest {

    @NotBlank(message = "Uçuş numarası boş olamaz")
    private String flightNumber;

    @NotBlank(message = "Kalkış ICAO kodu boş olamaz")
    @Pattern(
            regexp = "^[A-Za-z]{4}$",
            message = "Kalkış ICAO kodu 4 harf olmalıdır"
    )
    private String originIcaoCode;

    @NotBlank(message = "Varış ICAO kodu boş olamaz")
    @Pattern(
            regexp = "^[A-Za-z]{4}$",
            message = "Varış ICAO kodu 4 harf olmalıdır"
    )
    private String destinationIcaoCode;
}