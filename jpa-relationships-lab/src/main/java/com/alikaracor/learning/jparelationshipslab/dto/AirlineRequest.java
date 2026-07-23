package com.alikaracor.learning.jparelationshipslab.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AirlineRequest {

    @NotBlank(message = "Havayolu adı boş olamaz")
    @Size(max = 100, message = "Havayolu adı en fazla 100 karakter olabilir")
    private String name;

    @NotBlank(message = "IATA kodu boş olamaz")
    @Pattern(
            regexp = "^[A-Za-z0-9]{2}$",
            message = "IATA kodu 2 harf veya rakamdan oluşmalıdır"
    )
    private String iataCode;

    @NotBlank(message = "ICAO kodu boş olamaz")
    @Pattern(
            regexp = "^[A-Za-z]{3}$",
            message = "ICAO kodu 3 harften oluşmalıdır"
    )
    private String icaoCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIataCode() {
        return iataCode;
    }

    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public void setIcaoCode(String icaoCode) {
        this.icaoCode = icaoCode;
    }
}
