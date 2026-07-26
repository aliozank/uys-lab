package com.alikaracor.learning.flightarchivepostgresqllab.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "archived_flights")
@Getter
@Setter
@NoArgsConstructor
public class ArchivedFlight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false, unique = true, length = 100)
    private String eventId;

    @Column(name = "flight_number", nullable = false, length = 20)
    private String flightNumber;

    @Column(name = "origin_icao_code", nullable = false, length = 4)
    private String originIcaoCode;

    @Column(name = "destination_icao_code", nullable = false, length = 4)
    private String destinationIcaoCode;

    @Column(name = "actual_departure", nullable = false)
    private LocalDateTime actualDeparture;

    @Column(name = "actual_arrival", nullable = false)
    private LocalDateTime actualArrival;

    @Column(name = "archived_at", nullable = false)
    private LocalDateTime archivedAt;
}
