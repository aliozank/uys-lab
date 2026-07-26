package com.alikaracor.learning.flightarchivepostgresqllab.repository;

import com.alikaracor.learning.flightarchivepostgresqllab.model.ArchivedFlight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArchivedFlightRepository
        extends JpaRepository<ArchivedFlight, Long> {

    boolean existsByEventId(String eventId);

    List<ArchivedFlight> findAllByOrderByArchivedAtDesc();
}
