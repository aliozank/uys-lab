package com.alikaracor.learning.csvflightuploadlab.service;

import com.alikaracor.learning.csvflightuploadlab.dto.CsvRowError;
import com.alikaracor.learning.csvflightuploadlab.dto.CsvUploadResponse;
import com.alikaracor.learning.csvflightuploadlab.dto.FlightCsvRecord;
import com.alikaracor.learning.csvflightuploadlab.model.FlightType;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class FlightCsvService {

    private static final List<String> REQUIRED_HEADERS = List.of(
            "flightNumber",
            "airlineIataCode",
            "aircraftTypeId",
            "originIcaoCode",
            "destinationIcaoCode",
            "flightDate",
            "std",
            "sta",
            "flightType"
    );

    private final Validator validator;

    public FlightCsvService(Validator validator) {
        this.validator = validator;
    }

    public CsvUploadResponse uploadFlights(MultipartFile file) {

        validateFile(file);

        List<FlightCsvRecord> successfulRecords = new ArrayList<>();
        List<CsvRowError> errors = new ArrayList<>();

        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader()
                .setSkipHeaderRecord(true)
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .get();

        try (
                Reader reader = new InputStreamReader(
                        file.getInputStream(),
                        StandardCharsets.UTF_8
                );
                CSVParser csvParser = csvFormat.parse(reader)
        ) {

            validateHeaders(csvParser);

            for (CSVRecord csvRecord : csvParser) {
                processRecord(csvRecord, successfulRecords, errors);
            }

        } catch (IOException exception) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "CSV dosyası okunamadı",
                    exception
            );
        }

        return new CsvUploadResponse(
                successfulRecords.size() + errors.size(),
                successfulRecords.size(),
                errors.size(),
                successfulRecords,
                errors
        );
    }

    private void validateFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "CSV dosyası boş olamaz"
            );
        }

        String fileName = file.getOriginalFilename();

        if (fileName == null ||
                !fileName.toLowerCase(Locale.ROOT).endsWith(".csv")) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Yüklenen dosya .csv uzantılı olmalıdır"
            );
        }
    }

    private void validateHeaders(CSVParser csvParser) {

        Set<String> uploadedHeaders = csvParser.getHeaderMap().keySet();

        List<String> missingHeaders = REQUIRED_HEADERS.stream()
                .filter(requiredHeader ->
                        uploadedHeaders.stream().noneMatch(uploadedHeader ->
                                uploadedHeader.equalsIgnoreCase(requiredHeader)
                        )
                )
                .toList();

        if (!missingHeaders.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Eksik CSV sütunları: " + missingHeaders
            );
        }
    }

    private void processRecord(
            CSVRecord csvRecord,
            List<FlightCsvRecord> successfulRecords,
            List<CsvRowError> errors
    ) {

        List<String> errorMessages = new ArrayList<>();
        String rawData = convertRecordToText(csvRecord);

        try {
            String flightNumber = csvRecord.get("flightNumber");
            String airlineIataCode = csvRecord.get("airlineIataCode")
                    .toUpperCase(Locale.ROOT);

            String originIcaoCode = csvRecord.get("originIcaoCode")
                    .toUpperCase(Locale.ROOT);

            String destinationIcaoCode = csvRecord.get("destinationIcaoCode")
                    .toUpperCase(Locale.ROOT);

            Long aircraftTypeId = parseAircraftTypeId(
                    csvRecord.get("aircraftTypeId"),
                    errorMessages
            );

            LocalDate flightDate = parseFlightDate(
                    csvRecord.get("flightDate"),
                    errorMessages
            );

            LocalTime std = parseTime(
                    csvRecord.get("std"),
                    "STD",
                    errorMessages
            );

            LocalTime sta = parseTime(
                    csvRecord.get("sta"),
                    "STA",
                    errorMessages
            );

            FlightType flightType = parseFlightType(
                    csvRecord.get("flightType"),
                    errorMessages
            );

            FlightCsvRecord flightCsvRecord = new FlightCsvRecord(
                    flightNumber,
                    airlineIataCode,
                    aircraftTypeId,
                    originIcaoCode,
                    destinationIcaoCode,
                    flightDate,
                    std,
                    sta,
                    flightType
            );

            addValidationErrors(flightCsvRecord, errorMessages);
            validateDifferentAirports(flightCsvRecord, errorMessages);

            if (errorMessages.isEmpty()) {
                successfulRecords.add(flightCsvRecord);
            } else {
                errors.add(new CsvRowError(
                        csvRecord.getRecordNumber(),
                        rawData,
                        errorMessages
                ));
            }

        } catch (RuntimeException exception) {
            errors.add(new CsvRowError(
                    csvRecord.getRecordNumber(),
                    rawData,
                    List.of("Satır formatı okunamadı: " + exception.getMessage())
            ));
        }
    }

    private Long parseAircraftTypeId(
            String value,
            List<String> errorMessages
    ) {

        try {
            return Long.valueOf(value);
        } catch (NumberFormatException exception) {
            errorMessages.add("Aircraft type id sayı olmalıdır");
            return null;
        }
    }

    private LocalDate parseFlightDate(
            String value,
            List<String> errorMessages
    ) {

        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException exception) {
            errorMessages.add("Uçuş tarihi yyyy-MM-dd formatında olmalıdır");
            return null;
        }
    }

    private LocalTime parseTime(
            String value,
            String fieldName,
            List<String> errorMessages
    ) {

        try {
            return LocalTime.parse(value);
        } catch (DateTimeParseException exception) {
            errorMessages.add(fieldName + " HH:mm formatında olmalıdır");
            return null;
        }
    }

    private FlightType parseFlightType(
            String value,
            List<String> errorMessages
    ) {

        try {
            return FlightType.valueOf(value.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException exception) {
            errorMessages.add(
                    "Flight type DOMESTIC veya INTERNATIONAL olmalıdır"
            );
            return null;
        }
    }

    private void addValidationErrors(
            FlightCsvRecord flightCsvRecord,
            List<String> errorMessages
    ) {

        Set<ConstraintViolation<FlightCsvRecord>> violations =
                validator.validate(flightCsvRecord);

        violations.stream()
                .map(ConstraintViolation::getMessage)
                .forEach(errorMessages::add);
    }

    private void validateDifferentAirports(
            FlightCsvRecord flightCsvRecord,
            List<String> errorMessages
    ) {

        if (flightCsvRecord.getOriginIcaoCode() != null &&
                flightCsvRecord.getOriginIcaoCode().equalsIgnoreCase(
                        flightCsvRecord.getDestinationIcaoCode()
                )) {

            errorMessages.add(
                    "Kalkış ve varış havalimanı aynı olamaz"
            );
        }
    }

    private String convertRecordToText(CSVRecord csvRecord) {

        return IntStream.range(0, csvRecord.size())
                .mapToObj(csvRecord::get)
                .collect(Collectors.joining(","));
    }
}