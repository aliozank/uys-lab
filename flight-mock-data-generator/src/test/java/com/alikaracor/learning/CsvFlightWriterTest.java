package com.alikaracor.learning;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CsvFlightWriterTest {

    @TempDir
    Path tempDirectory;

    @Test
    void shouldWriteFlightsToCsvFile() throws IOException {

        FlightDataGenerator generator =
                new FlightDataGenerator();

        CsvFlightWriter csvWriter =
                new CsvFlightWriter();

        List<FlightData> flights =
                generator.generateFlights(10);

        Path csvFile =
                tempDirectory.resolve("flights.csv");

        csvWriter.writeFlights(
                flights,
                csvFile.toString()
        );

        List<String> lines =
                Files.readAllLines(csvFile);

        assertTrue(Files.exists(csvFile));
        assertEquals(11, lines.size());

        assertTrue(
                lines.get(0).startsWith("flightNumber")
        );
    }

    @Test
    void shouldRejectEmptyFlightList() {

        CsvFlightWriter csvWriter =
                new CsvFlightWriter();

        Path csvFile =
                tempDirectory.resolve("flights.csv");

        assertThrows(
                IllegalArgumentException.class,
                () -> csvWriter.writeFlights(
                        List.of(),
                        csvFile.toString()
                )
        );
    }
}
