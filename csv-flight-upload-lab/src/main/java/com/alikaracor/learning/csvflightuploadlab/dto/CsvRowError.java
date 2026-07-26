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
public class CsvRowError {

    private long rowNumber;
    private String rawData;
    private List<String> errorMessages;
}