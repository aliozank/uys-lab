package com.alikaracor.learning.flightarchivepostgresqllab.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class DailyFlightReportResponse {

    private LocalDate flightDate;
    private Long flightCount;
    private BigDecimal averageDurationMinutes;
}