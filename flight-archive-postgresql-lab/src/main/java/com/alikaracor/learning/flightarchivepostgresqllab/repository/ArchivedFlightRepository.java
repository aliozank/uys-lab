package com.alikaracor.learning.flightarchivepostgresqllab.repository;

import com.alikaracor.learning.flightarchivepostgresqllab.model.ArchivedFlight;
import com.alikaracor.learning.flightarchivepostgresqllab.repository.projection.DailyFlightReportProjection;
import com.alikaracor.learning.flightarchivepostgresqllab.repository.projection.RouteReportProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArchivedFlightRepository
        extends JpaRepository<ArchivedFlight, Long> {

    boolean existsByEventId(String eventId);

    List<ArchivedFlight> findAllByOrderByArchivedAtDesc();

    @Query(value = """
            SELECT
                origin_icao_code AS "originIcaoCode",
                destination_icao_code AS "destinationIcaoCode",
                COUNT(*) AS "flightCount"
            FROM archived_flights
            GROUP BY origin_icao_code, destination_icao_code
            ORDER BY COUNT(*) DESC
            """, nativeQuery = true)
    List<RouteReportProjection> findRouteReport();

    @Query(value = """
        SELECT
            DATE(actual_departure) AS "flightDate",
            COUNT(*) AS "flightCount",
            ROUND(
                AVG(
                    EXTRACT(EPOCH FROM (actual_arrival - actual_departure)) / 60
                )::numeric,
                2
            ) AS "averageDurationMinutes"
        FROM archived_flights
        GROUP BY DATE(actual_departure)
        ORDER BY DATE(actual_departure)
        """, nativeQuery = true)
    List<DailyFlightReportProjection> findDailyFlightReport();

}
