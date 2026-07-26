package com.alikaracor.learning.csvflightuploadlab.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CsvUploadResponse {

    private int totalRowCount;
    private int successfulRowCount;
    private int failedRowCount;

    private List<FlightCsvRecord> successfulRecords;
    private List<CsvRowError> errors;
}