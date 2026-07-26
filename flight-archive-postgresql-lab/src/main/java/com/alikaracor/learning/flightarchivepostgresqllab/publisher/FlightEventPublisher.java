package com.alikaracor.learning.flightarchivepostgresqllab.publisher;

import com.alikaracor.learning.flightarchivepostgresqllab.dto.FlightCompletedRequest;
import com.alikaracor.learning.flightarchivepostgresqllab.event.FlightCompletedEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;
import java.util.UUID;

@Component
public class FlightEventPublisher {

    private final KafkaTemplate<String, FlightCompletedEvent> kafkaTemplate;
    private final String topicName;

    public FlightEventPublisher(
            KafkaTemplate<String, FlightCompletedEvent> kafkaTemplate,
            @Value("${app.kafka.flight-events-topic}") String topicName
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    public FlightCompletedEvent publishCompletedFlight(
            FlightCompletedRequest request
    ) {

        validateRequest(request);

        FlightCompletedEvent event = new FlightCompletedEvent(
                UUID.randomUUID().toString(),
                request.getFlightNumber(),
                request.getOriginIcaoCode().toUpperCase(Locale.ROOT),
                request.getDestinationIcaoCode().toUpperCase(Locale.ROOT),
                request.getActualDeparture(),
                request.getActualArrival()
        );

        kafkaTemplate.send(
                topicName,
                event.getEventId(),
                event
        );

        return event;
    }

    private void validateRequest(FlightCompletedRequest request) {

        if (request.getOriginIcaoCode().equalsIgnoreCase(
                request.getDestinationIcaoCode()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Kalkış ve varış havalimanı aynı olamaz"
            );
        }

        if (!request.getActualArrival().isAfter(
                request.getActualDeparture()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Varış zamanı kalkış zamanından sonra olmalıdır"
            );
        }
    }
}
