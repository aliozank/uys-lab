package com.alikaracor.learning;

import java.time.LocalDate;
import java.time.LocalTime;

public class FlightData {
    public FlightData(String flightNumber, String airlineCode, String aircraftType, String originIcao, String destinationIcao, LocalDate flightDate, LocalTime scheduledDepartureTime, LocalTime scheduledArrivalTime, FlightType flightType) {
        this.flightNumber = flightNumber;
        this.airlineCode = airlineCode;
        this.aircraftType = aircraftType;
        this.originIcao = originIcao;
        this.destinationIcao = destinationIcao;
        this.flightDate = flightDate;
        this.scheduledDepartureTime = scheduledDepartureTime;
        this.scheduledArrivalTime = scheduledArrivalTime;
        this.flightType = flightType;
    }

    private final String flightNumber;
    private final String airlineCode;
    private final String aircraftType;

    public String getAirlineCode() {
        return airlineCode;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public String getDestinationIcao() {
        return destinationIcao;
    }

    public String getOriginIcao() {
        return originIcao;
    }

    public LocalDate getFlightDate() {
        return flightDate;
    }

    public LocalTime getScheduledArrivalTime() {
        return scheduledArrivalTime;
    }

    public LocalTime getScheduledDepartureTime() {
        return scheduledDepartureTime;
    }

    public FlightType getFlightType() {
        return flightType;
    }

    @Override
    public String toString() {
        return "FlightData{" +
                "flightNumber='" + flightNumber + '\'' +
                ", airlineCode='" + airlineCode + '\'' +
                ", aircraftType='" + aircraftType + '\'' +
                ", originIcao='" + originIcao + '\'' +
                ", destinationIcao='" + destinationIcao + '\'' +
                ", flightDate=" + flightDate +
                ", scheduledDepartureTime=" + scheduledDepartureTime +
                ", scheduledArrivalTime=" + scheduledArrivalTime +
                ", flightType=" + flightType +
                '}';
    }

    private final String originIcao;
    private final String destinationIcao;
    private final LocalDate flightDate;
    private final LocalTime scheduledDepartureTime;
    private final LocalTime scheduledArrivalTime;
    private final FlightType flightType;

}
