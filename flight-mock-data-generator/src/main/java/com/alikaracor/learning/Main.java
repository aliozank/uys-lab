package com.alikaracor.learning;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        FlightDataGenerator flightGenerator = new FlightDataGenerator();

        List<FlightData> flightsData = flightGenerator.generateFlights(30);

        for (FlightData flight : flightsData) {
            System.out.println(flight);
        }

        System.out.println(
                "Toplam üretilen uçuş: " + flightsData.size()
        );

        long passengerFlightCount = flightsData.stream()
                .filter(flight -> flight.getFlightType() == FlightType.PASSENGER)
                .count();

        System.out.println(
                "Passenger uçuş sayısı: " + passengerFlightCount
        );

        System.out.println("LTFM kalkışlı uçuşlar:");

        flightsData.stream()
                .filter(flight -> "LTFM".equals(flight.getOriginIcao()))
                .forEach(System.out::println);

        CsvFlightWriter csvFlightWriter = new CsvFlightWriter();

        csvFlightWriter.writeFlights(
                flightsData,
                "output/flights.csv"
        );

        System.out.println(
                "CSV dosyası başarıyla oluşturuldu: output/flights.csv"
        );


    }
}
