package com.alikaracor.learning.jparelationshipslab.dto;

public class AircraftResponse {

    private Long id;
    private String registrationNumber;
    private String model;
    private Integer seatCapacity;
    private Long operatorAirlineId;
    private String operatorAirlineName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getOperatorAirlineName() {
        return operatorAirlineName;
    }

    public void setOperatorAirlineName(String operatorAirlineName) {
        this.operatorAirlineName = operatorAirlineName;
    }
}
