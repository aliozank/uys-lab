package com.alikaracor.learning.mapstructlab.dto;

public class AircraftResponse {

    private Long aircraftId;
    private String registrationNumber;
    private String model;
    private Integer capacity;
    private Long operatorAirlineId;
    private String operatorAirlineName;

    public Long getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(Long aircraftId) {
        this.aircraftId = aircraftId;
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

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
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
