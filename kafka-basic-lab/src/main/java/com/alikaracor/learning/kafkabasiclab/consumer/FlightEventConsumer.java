package com.alikaracor.learning.kafkabasiclab.consumer;

import com.alikaracor.learning.kafkabasiclab.model.FlightEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class FlightEventConsumer {

    @KafkaListener(
            topics = "flight.events",
            groupId = "flight-archive-group"
    )
    public void consumeFlightEvent(FlightEvent flightEvent) {

        System.out.println(
                "Kafka mesajı alındı: " + flightEvent
        );
    }
}