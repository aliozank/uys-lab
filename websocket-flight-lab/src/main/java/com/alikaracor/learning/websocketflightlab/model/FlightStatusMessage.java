package com.alikaracor.learning.websocketflightlab.model;

public class FlightStatusMessage {

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String flightNumber;
    private String status;

    public FlightStatusMessage() {
    }

    public FlightStatusMessage(String flightNumber, String status) {
        this.flightNumber = flightNumber;
        this.status = status;
    }



}