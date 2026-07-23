package com.alikaracor.learning.mapstructlab.model;

public class Aircraft {

    private Long id;
    private String registrationNumber;
    private String modelName;
    private Integer seatCapacity;
    private Airline operatorAirline;

    public Aircraft() {
    }

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

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(Integer seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public Airline getOperatorAirline() {
        return operatorAirline;
    }

    public void setOperatorAirline(Airline operatorAirline) {
        this.operatorAirline = operatorAirline;
    }
}
