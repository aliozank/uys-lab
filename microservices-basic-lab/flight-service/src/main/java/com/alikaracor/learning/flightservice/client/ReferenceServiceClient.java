package com.alikaracor.learning.flightservice.client;

import com.alikaracor.learning.flightservice.dto.AirportResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ReferenceServiceClient {

    private final RestClient referenceServiceRestClient;

    public ReferenceServiceClient(RestClient referenceServiceRestClient) {
        this.referenceServiceRestClient = referenceServiceRestClient;
    }

    public AirportResponse getAirportByIcaoCode(String icaoCode) {

        try {
            return referenceServiceRestClient
                    .get()
                    .uri("/api/airports/{icaoCode}", icaoCode)
                    .retrieve()
                    .body(AirportResponse.class);

        } catch (RestClientResponseException exception) {

            if (exception.getStatusCode().value() == 404) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        icaoCode + " koduyla havalimanı bulunamadı"
                );
            }

            throw new ResponseStatusException(
                    HttpStatus.BAD_GATEWAY,
                    "Reference Service isteği başarısız oldu"
            );

        } catch (ResourceAccessException exception) {

            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE,
                    "Reference Service'e ulaşılamıyor"
            );
        }
    }
}