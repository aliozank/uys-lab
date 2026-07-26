package com.alikaracor.learning.flightarchivepostgresqllab.service;

import com.alikaracor.learning.flightarchivepostgresqllab.event.FlightCompletedEvent;
import com.alikaracor.learning.flightarchivepostgresqllab.model.ArchivedFlight;
import com.alikaracor.learning.flightarchivepostgresqllab.repository.ArchivedFlightRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FlightArchiveServiceTest {

    @Mock
    private ArchivedFlightRepository archivedFlightRepository;

    @InjectMocks
    private FlightArchiveService flightArchiveService;

    @Test
    void shouldArchiveFlightWhenEventIsNew() {

        FlightCompletedEvent event = new FlightCompletedEvent(
                "event-001",
                "TK101",
                "LTFM",
                "EDDF",
                LocalDateTime.of(2026, 7, 26, 10, 30),
                LocalDateTime.of(2026, 7, 26, 13, 20)
        );

        given(
                archivedFlightRepository.existsByEventId("event-001")
        ).willReturn(false);

        flightArchiveService.archiveFlight(event);

        ArgumentCaptor<ArchivedFlight> flightCaptor =
                ArgumentCaptor.forClass(ArchivedFlight.class);

        verify(archivedFlightRepository).save(flightCaptor.capture());

        ArchivedFlight savedFlight = flightCaptor.getValue();

        assertThat(savedFlight.getEventId()).isEqualTo("event-001");
        assertThat(savedFlight.getFlightNumber()).isEqualTo("TK101");
        assertThat(savedFlight.getOriginIcaoCode()).isEqualTo("LTFM");
        assertThat(savedFlight.getDestinationIcaoCode()).isEqualTo("EDDF");
        assertThat(savedFlight.getArchivedAt()).isNotNull();
    }

    @Test
    void shouldNotArchiveFlightWhenEventAlreadyExists() {

        FlightCompletedEvent event = new FlightCompletedEvent(
                "event-001",
                "TK101",
                "LTFM",
                "EDDF",
                LocalDateTime.of(2026, 7, 26, 10, 30),
                LocalDateTime.of(2026, 7, 26, 13, 20)
        );

        given(
                archivedFlightRepository.existsByEventId("event-001")
        ).willReturn(true);

        flightArchiveService.archiveFlight(event);

        verify(archivedFlightRepository, never())
                .save(any(ArchivedFlight.class));
    }
}