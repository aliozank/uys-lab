package com.alikaracor.learning.jparelationshipslab.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AircraftRequest {

    @NotBlank(message = "Tescil numarası boş olamaz")
    @Size(max = 20, message = "Tescil numarası en fazla 20 karakter olabilir")
    private String registrationNumber;

    @NotBlank(message = "Uçak modeli boş olamaz")
    @Size(max = 100, message = "Uçak modeli en fazla 100 karakter olabilir")
    private String model;

    @NotNull(message = "Koltuk kapasitesi boş olamaz")
    @Min(value = 1, message = "Koltuk kapasitesi en az 1 olmalıdır")
    private Integer seatCapacity;

    @NotNull(message = "Operatör havayolu id boş olamaz")
    private Long operatorAirlineId;

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(Integer seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public Long getOperatorAirlineId() {
        return operatorAirlineId;
    }

    public void setOperatorAirlineId(Long operatorAirlineId) {
        this.operatorAirlineId = operatorAirlineId;
    }
}
