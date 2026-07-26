package com.alikaracor.learning.kafkabasiclab;

import com.alikaracor.learning.kafkabasiclab.consumer.FlightEventConsumer;
import com.alikaracor.learning.kafkabasiclab.model.FlightEvent;
import com.alikaracor.learning.kafkabasiclab.producer.FlightEventProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.util.Locale;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@SpringBootTest
@EmbeddedKafka(
        partitions = 1,
        topics = "flight.events",
        bootstrapServersProperty = "spring.kafka.bootstrap-servers"
)
class FlightEventKafkaIntegrationTest {

    static {
        Locale.setDefault(Locale.ENGLISH);
    }

    @Autowired
    private FlightEventProducer flightEventProducer;

    @MockitoSpyBean
    private FlightEventConsumer flightEventConsumer;

    @Test
    void shouldPublishAndConsumeFlightEvent() {

        FlightEvent flightEvent = new FlightEvent();

        flightEvent.setFlightNumber("TK101");
        flightEvent.setOriginAirportCode("LTFM");
        flightEvent.setDestinationAirportCode("EDDF");
        flightEvent.setStatus("COMPLETED");

        flightEventProducer.sendFlightEvent(flightEvent);

        verify(
                flightEventConsumer,
                timeout(5000)
        ).consumeFlightEvent(
                argThat(receivedEvent ->
                        receivedEvent != null
                                && "TK101".equals(
                                receivedEvent.getFlightNumber()
                        )
                                && "LTFM".equals(
                                receivedEvent.getOriginAirportCode()
                        )
                                && "EDDF".equals(
                                receivedEvent.getDestinationAirportCode()
                        )
                                && "COMPLETED".equals(
                                receivedEvent.getStatus()
                        )
                )
        );
    }
}