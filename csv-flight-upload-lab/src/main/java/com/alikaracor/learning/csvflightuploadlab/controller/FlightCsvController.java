package com.alikaracor.learning.csvflightuploadlab.controller;

import com.alikaracor.learning.csvflightuploadlab.dto.CsvUploadResponse;
import com.alikaracor.learning.csvflightuploadlab.service.FlightCsvService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/flights/csv")
public class FlightCsvController {

    private final FlightCsvService flightCsvService;

    public FlightCsvController(FlightCsvService flightCsvService) {
        this.flightCsvService = flightCsvService;
    }

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public CsvUploadResponse uploadFlights(
            @RequestParam("file") MultipartFile file
    ) {

        return flightCsvService.uploadFlights(file);
    }
}