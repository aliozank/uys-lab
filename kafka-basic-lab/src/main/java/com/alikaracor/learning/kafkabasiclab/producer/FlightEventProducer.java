package com.alikaracor.learning.kafkabasiclab.producer;

import com.alikaracor.learning.kafkabasiclab.model.FlightEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FlightEventProducer {

    private final KafkaTemplate<String, FlightEvent> kafkaTemplate;

    public FlightEventProducer(
            KafkaTemplate<String, FlightEvent> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendFlightEvent(FlightEvent flightEvent) {

        kafkaTemplate.send(
                "flight.events",
                flightEvent.getFlightNumber(),
                flightEvent
        );
    }
}