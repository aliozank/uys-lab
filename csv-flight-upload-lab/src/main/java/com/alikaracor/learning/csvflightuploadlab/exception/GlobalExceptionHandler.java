package com.alikaracor.learning.csvflightuploadlab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ProblemDetail handleResponseStatusException(
            ResponseStatusException exception
    ) {

        String detail = exception.getReason() != null
                ? exception.getReason()
                : "İstek işlenemedi";

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                exception.getStatusCode(),
                detail
        );

        problemDetail.setTitle("CSV yükleme hatası");

        return problemDetail;
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ProblemDetail handleMaxUploadSizeExceededException(
            MaxUploadSizeExceededException exception
    ) {

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.PAYLOAD_TOO_LARGE,
                "Yüklenen CSV dosyası en fazla 5 MB olabilir"
        );

        problemDetail.setTitle("Dosya boyutu aşıldı");

        return problemDetail;
    }
}