package com.alikaracor.learning.csvflightuploadlab.dto;

import com.alikaracor.learning.csvflightuploadlab.model.FlightType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightCsvRecord {

    @NotBlank(message = "Uçuş numarası boş olamaz")
    private String flightNumber;

    @NotBlank(message = "Havayolu IATA kodu boş olamaz")
    @Pattern(
            regexp = "^[A-Za-z0-9]{2}$",
            message = "Havayolu IATA kodu 2 karakter olmalıdır"
    )
    private String airlineIataCode;

    @NotNull(message = "Aircraft type id boş olamaz")
    @Positive(message = "Aircraft type id pozitif olmalıdır")
    private Long aircraftTypeId;

    @NotBlank(message = "Kalkış havalimanı ICAO kodu boş olamaz")
    @Pattern(
            regexp = "^[A-Za-z]{4}$",
            message = "Kalkış havalimanı ICAO kodu 4 harf olmalıdır"
    )
    private String originIcaoCode;

    @NotBlank(message = "Varış havalimanı ICAO kodu boş olamaz")
    @Pattern(
            regexp = "^[A-Za-z]{4}$",
            message = "Varış havalimanı ICAO kodu 4 harf olmalıdır"
    )
    private String destinationIcaoCode;

    @NotNull(message = "Uçuş tarihi boş olamaz")
    private LocalDate flightDate;

    @NotNull(message = "Kalkış saati boş olamaz")
    private LocalTime std;

    @NotNull(message = "Varış saati boş olamaz")
    private LocalTime sta;

    @NotNull(message = "Uçuş tipi boş olamaz")
    private FlightType flightType;
}