package com.alikaracor.learning.flightarchivepostgresqllab.repository;

import com.alikaracor.learning.flightarchivepostgresqllab.model.ArchivedFlight;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE
)
class ArchivedFlightRepositoryTest {

    @Container
    @ServiceConnection
    static final PostgreSQLContainer POSTGRESQL =
            new PostgreSQLContainer("postgres:17-alpine");

    @Autowired
    private ArchivedFlightRepository archivedFlightRepository;

    @Test
    void shouldSaveAndFindArchivedFlight() {

        ArchivedFlight archivedFlight = new ArchivedFlight();

        archivedFlight.setEventId("event-test-001");
        archivedFlight.setFlightNumber("TK101");
        archivedFlight.setOriginIcaoCode("LTFM");
        archivedFlight.setDestinationIcaoCode("EDDF");
        archivedFlight.setActualDeparture(
                LocalDateTime.of(2026, 7, 26, 10, 30)
        );
        archivedFlight.setActualArrival(
                LocalDateTime.of(2026, 7, 26, 13, 20)
        );
        archivedFlight.setArchivedAt(LocalDateTime.now());

        ArchivedFlight savedFlight =
                archivedFlightRepository.save(archivedFlight);

        Optional<ArchivedFlight> foundFlight =
                archivedFlightRepository.findById(savedFlight.getId());

        assertThat(foundFlight).isPresent();
        assertThat(foundFlight.get().getFlightNumber()).isEqualTo("TK101");
        assertThat(foundFlight.get().getOriginIcaoCode()).isEqualTo("LTFM");
        assertThat(foundFlight.get().getDestinationIcaoCode()).isEqualTo("EDDF");
    }
}