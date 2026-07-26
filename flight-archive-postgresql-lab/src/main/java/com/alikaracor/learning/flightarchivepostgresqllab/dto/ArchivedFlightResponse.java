package com.alikaracor.learning.flightarchivepostgresqllab.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ArchivedFlightResponse {

    private Long id;
    private String eventId;
    private String flightNumber;
    private String originIcaoCode;
    private String destinationIcaoCode;
    private LocalDateTime actualDeparture;
    private LocalDateTime actualArrival;
    private LocalDateTime archivedAt;
}
