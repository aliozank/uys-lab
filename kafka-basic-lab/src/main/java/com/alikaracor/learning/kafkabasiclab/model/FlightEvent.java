package com.alikaracor.learning.kafkabasiclab.model;

public class FlightEvent {

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getOriginAirportCode() {
        return originAirportCode;
    }

    public void setOriginAirportCode(String originAirportCode) {
        this.originAirportCode = originAirportCode;
    }

    public String getDestinationAirportCode() {
        return destinationAirportCode;
    }

    public void setDestinationAirportCode(String destinationAirportCode) {
        this.destinationAirportCode = destinationAirportCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FlightEvent{" +
                "flightNumber='" + flightNumber + '\'' +
                ", originAirportCode='" + originAirportCode + '\'' +
                ", destinationAirportCode='" + destinationAirportCode + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public FlightEvent() {
    }

    public FlightEvent(String flightNumber, String status, String destinationAirportCode, String originAirportCode) {
        this.flightNumber = flightNumber;
        this.status = status;
        this.destinationAirportCode = destinationAirportCode;
        this.originAirportCode = originAirportCode;
    }

    private String flightNumber;
    private String originAirportCode;
    private String destinationAirportCode;
    private String status;
}
