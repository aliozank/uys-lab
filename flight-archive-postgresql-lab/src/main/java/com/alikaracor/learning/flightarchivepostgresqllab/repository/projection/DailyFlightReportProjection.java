package com.alikaracor.learning.flightarchivepostgresqllab.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface DailyFlightReportProjection {

    LocalDate getFlightDate();

    Long getFlightCount();

    BigDecimal getAverageDurationMinutes();
}