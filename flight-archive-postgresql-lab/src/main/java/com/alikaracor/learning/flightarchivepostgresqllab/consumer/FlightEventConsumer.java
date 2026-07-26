package com.alikaracor.learning.flightarchivepostgresqllab.consumer;

import com.alikaracor.learning.flightarchivepostgresqllab.event.FlightCompletedEvent;
import com.alikaracor.learning.flightarchivepostgresqllab.service.FlightArchiveService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class FlightEventConsumer {

    private final FlightArchiveService flightArchiveService;

    public FlightEventConsumer(
            FlightArchiveService flightArchiveService
    ) {
        this.flightArchiveService = flightArchiveService;
    }

    @KafkaListener(
            topics = "${app.kafka.flight-events-topic}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consumeCompletedFlight(FlightCompletedEvent event) {
        flightArchiveService.archiveFlight(event);
    }
}
