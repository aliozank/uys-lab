package com.alikaracor.learning.flightarchivepostgresqllab.service;

import com.alikaracor.learning.flightarchivepostgresqllab.dto.ArchivedFlightResponse;
import com.alikaracor.learning.flightarchivepostgresqllab.event.FlightCompletedEvent;
import com.alikaracor.learning.flightarchivepostgresqllab.model.ArchivedFlight;
import com.alikaracor.learning.flightarchivepostgresqllab.repository.ArchivedFlightRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FlightArchiveService {

    private final ArchivedFlightRepository archivedFlightRepository;

    public FlightArchiveService(
            ArchivedFlightRepository archivedFlightRepository
    ) {
        this.archivedFlightRepository = archivedFlightRepository;
    }

    @Transactional
    public void archiveFlight(FlightCompletedEvent event) {

        if (archivedFlightRepository.existsByEventId(event.getEventId())) {
            return;
        }

        ArchivedFlight archivedFlight = new ArchivedFlight();

        archivedFlight.setEventId(event.getEventId());
        archivedFlight.setFlightNumber(event.getFlightNumber());
        archivedFlight.setOriginIcaoCode(event.getOriginIcaoCode());
        archivedFlight.setDestinationIcaoCode(
                event.getDestinationIcaoCode()
        );
        archivedFlight.setActualDeparture(event.getActualDeparture());
        archivedFlight.setActualArrival(event.getActualArrival());
        archivedFlight.setArchivedAt(LocalDateTime.now());

        archivedFlightRepository.save(archivedFlight);
    }

    @Transactional(readOnly = true)
    public List<ArchivedFlightResponse> getAllArchivedFlights() {

        return archivedFlightRepository
                .findAllByOrderByArchivedAtDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ArchivedFlightResponse getArchivedFlightById(Long id) {

        ArchivedFlight archivedFlight = archivedFlightRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Bu id ile arşivlenmiş uçuş bulunamadı"
                ));

        return toResponse(archivedFlight);
    }

    private ArchivedFlightResponse toResponse(
            ArchivedFlight archivedFlight
    ) {

        return new ArchivedFlightResponse(
                archivedFlight.getId(),
                archivedFlight.getEventId(),
                archivedFlight.getFlightNumber(),
                archivedFlight.getOriginIcaoCode(),
                archivedFlight.getDestinationIcaoCode(),
                archivedFlight.getActualDeparture(),
                archivedFlight.getActualArrival(),
                archivedFlight.getArchivedAt()
        );
    }
}
