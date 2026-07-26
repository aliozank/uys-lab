package com.alikaracor.learning.flightarchivepostgresqllab.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlightCompletedEvent {

    private String eventId;
    private String flightNumber;
    private String originIcaoCode;
    private String destinationIcaoCode;
    private LocalDateTime actualDeparture;
    private LocalDateTime actualArrival;
}
