package com.alikaracor.learning.jparelationshipslab.service;

import com.alikaracor.learning.jparelationshipslab.dto.AirlineRequest;
import com.alikaracor.learning.jparelationshipslab.dto.AirlineResponse;
import com.alikaracor.learning.jparelationshipslab.model.Airline;
import com.alikaracor.learning.jparelationshipslab.repository.AircraftRepository;
import com.alikaracor.learning.jparelationshipslab.repository.AirlineRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;

@Service
@Transactional(readOnly = true)
public class AirlineService {

    private final AirlineRepository airlineRepository;
    private final AircraftRepository aircraftRepository;

    public AirlineService(
            AirlineRepository airlineRepository,
            AircraftRepository aircraftRepository
    ) {
        this.airlineRepository = airlineRepository;
        this.aircraftRepository = aircraftRepository;
    }

    @Transactional
    public AirlineResponse createAirline(AirlineRequest request) {
        validateUniqueCodes(request, null);

        Airline airline = new Airline();
        updateFields(airline, request);

        return toResponse(airlineRepository.save(airline));
    }

    public List<AirlineResponse> getAllAirlines() {
        return airlineRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public AirlineResponse getAirlineById(Long airlineId) {
        return toResponse(findAirlineById(airlineId));
    }

    @Transactional
    public AirlineResponse updateAirline(
            Long airlineId,
            AirlineRequest request
    ) {
        Airline airline = findAirlineById(airlineId);
        validateUniqueCodes(request, airlineId);
        updateFields(airline, request);

        return toResponse(airline);
    }

    @Transactional
    public void deleteAirline(Long airlineId) {
        Airline airline = findAirlineById(airlineId);

        if (aircraftRepository.existsByOperatorAirline_Id(airlineId)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Bu havayoluna bağlı uçaklar bulunduğu için silinemez"
            );
        }

        airlineRepository.delete(airline);
    }

    private Airline findAirlineById(Long airlineId) {
        return airlineRepository.findById(airlineId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Bu id ile eşleşen havayolu bulunamadı"
                ));
    }

    private void validateUniqueCodes(
            AirlineRequest request,
            Long currentAirlineId
    ) {
        boolean iataExists;
        boolean icaoExists;

        if (currentAirlineId == null) {
            iataExists = airlineRepository.existsByIataCodeIgnoreCase(
                    request.getIataCode()
            );
            icaoExists = airlineRepository.existsByIcaoCodeIgnoreCase(
                    request.getIcaoCode()
            );
        } else {
            iataExists = airlineRepository.existsByIataCodeIgnoreCaseAndIdNot(
                    request.getIataCode(),
                    currentAirlineId
            );
            icaoExists = airlineRepository.existsByIcaoCodeIgnoreCaseAndIdNot(
                    request.getIcaoCode(),
                    currentAirlineId
            );
        }

        if (iataExists || icaoExists) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "IATA veya ICAO kodu başka bir havayolu tarafından kullanılıyor"
            );
        }
    }

    private void updateFields(Airline airline, AirlineRequest request) {
        airline.setName(request.getName().trim());
        airline.setIataCode(request.getIataCode().toUpperCase(Locale.ROOT));
        airline.setIcaoCode(request.getIcaoCode().toUpperCase(Locale.ROOT));
    }

    private AirlineResponse toResponse(Airline airline) {
        AirlineResponse response = new AirlineResponse();

        response.setId(airline.getId());
        response.setName(airline.getName());
        response.setIataCode(airline.getIataCode());
        response.setIcaoCode(airline.getIcaoCode());

        return response;
    }
}
