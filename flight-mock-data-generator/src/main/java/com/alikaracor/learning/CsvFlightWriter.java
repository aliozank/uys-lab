package com.alikaracor.learning;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class CsvFlightWriter {

    public void writeFlights(
            List<FlightData> flights,
            String filePath
    ) throws IOException {

        if (flights == null || flights.isEmpty()) {            // veri mevcut mu ? kotnrolü
            throw new IllegalArgumentException(
                    "CSV dosyasına yazılacak uçuş bulunamadı"
            );
        }

        Path outputPath = Path.of(filePath);                 // dosya yolu ve klasörü belirleme

        Path parentDirectory = outputPath.getParent();

        if (parentDirectory != null) {                  // dosya bulunamazsa önlem
            Files.createDirectories(parentDirectory);
        }

        try (BufferedWriter writer =
                     Files.newBufferedWriter(       //newBufferedWriter = Dosyaya yazıyor . utf_8 türkçe karakter için uygun format
                             outputPath,
                             StandardCharsets.UTF_8
                     )) {

            writer.write(
                    "flightNumber,airlineCode,aircraftType," +
                            "originIcao,destinationIcao,flightDate," +
                            "scheduledDepartureTime,scheduledArrivalTime," +
                            "flightType"
            );

            writer.newLine();

            for (FlightData flight : flights) {

                String csvLine = String.join(                  // Virgül koyarak birleştireceğini gösteriyor uçuşları her uçuş bşaına virgül koyarak formatı bozmuyor
                        ",",
                        flight.getFlightNumber(),
                        flight.getAirlineCode(),
                        flight.getAircraftType(),
                        flight.getOriginIcao(),
                        flight.getDestinationIcao(),
                        flight.getFlightDate().toString(),
                        flight.getScheduledDepartureTime().toString(),
                        flight.getScheduledArrivalTime().toString(),
                        flight.getFlightType().name()
                );

                writer.write(csvLine);
                writer.newLine();
            }
        }
    }
}