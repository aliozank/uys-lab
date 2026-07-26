package com.alikaracor.learning.flightarchivepostgresqllab.controller;

import com.alikaracor.learning.flightarchivepostgresqllab.dto.ArchivedFlightResponse;
import com.alikaracor.learning.flightarchivepostgresqllab.service.FlightArchiveService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ArchiveController.class)
class ArchiveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FlightArchiveService flightArchiveService;

    @Test
    void shouldReturnArchivedFlightById() throws Exception {

        ArchivedFlightResponse response =
                new ArchivedFlightResponse(
                        1L,
                        "event-001",
                        "TK101",
                        "LTFM",
                        "EDDF",
                        LocalDateTime.of(2026, 7, 26, 10, 30),
                        LocalDateTime.of(2026, 7, 26, 13, 20),
                        LocalDateTime.of(2026, 7, 26, 14, 0)
                );

        given(
                flightArchiveService.getArchivedFlightById(1L)
        ).willReturn(response);

        mockMvc.perform(
                        get("/api/archived-flights/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.flightNumber").value("TK101"))
                .andExpect(jsonPath("$.originIcaoCode").value("LTFM"))
                .andExpect(jsonPath("$.destinationIcaoCode").value("EDDF"));
    }
}