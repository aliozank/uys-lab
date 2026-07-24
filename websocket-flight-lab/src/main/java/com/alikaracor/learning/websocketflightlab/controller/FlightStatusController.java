package com.alikaracor.learning.websocketflightlab.controller;

import com.alikaracor.learning.websocketflightlab.model.FlightStatusMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class FlightStatusController {

    @MessageMapping("/flight-status")
    @SendTo("/topic/flight-status")
    public FlightStatusMessage updateFlightStatus(
            FlightStatusMessage flightStatusMessage
    ) {
        return flightStatusMessage;
    }
}