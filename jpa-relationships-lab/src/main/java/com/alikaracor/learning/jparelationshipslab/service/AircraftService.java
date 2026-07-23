package com.alikaracor.learning.jparelationshipslab.service;

import com.alikaracor.learning.jparelationshipslab.dto.AircraftRequest;
import com.alikaracor.learning.jparelationshipslab.dto.AircraftResponse;
import com.alikaracor.learning.jparelationshipslab.model.Aircraft;
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
public class AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AirlineRepository airlineRepository;

    public AircraftService(
            AircraftRepository aircraftRepository,
            AirlineRepository airlineRepository
    ) {
        this.aircraftRepository = aircraftRepository;
        this.airlineRepository = airlineRepository;
    }

    @Transactional
    public AircraftResponse createAircraft(AircraftRequest request) {
        if (aircraftRepository.existsByRegistrationNumberIgnoreCase(
                request.getRegistrationNumber()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Bu tescil numarasıyla bir uçak zaten kayıtlı"
            );
        }

        Airline operatorAirline = findAirlineById(
                request.getOperatorAirlineId()
        );

        Aircraft aircraft = new Aircraft();
        updateFields(aircraft, request, operatorAirline);

        return toResponse(aircraftRepository.save(aircraft));
    }

    public List<AircraftResponse> getAllAircraft() {
        return aircraftRepository.findAllWithOperatorAirline()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public AircraftResponse getAircraftById(Long aircraftId) {
        return toResponse(findAircraftById(aircraftId));
    }

    public List<AircraftResponse> getAircraftByAirline(Long airlineId) {
        findAirlineById(airlineId);

        return aircraftRepository.findByOperatorAirline_Id(airlineId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public AircraftResponse updateAircraft(
            Long aircraftId,
            AircraftRequest request
    ) {
        Aircraft aircraft = findAircraftById(aircraftId);

        if (aircraftRepository.existsByRegistrationNumberIgnoreCaseAndIdNot(
                request.getRegistrationNumber(),
                aircraftId
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Bu tescil numarası başka bir uçak tarafından kullanılıyor"
            );
        }

        Airline operatorAirline = findAirlineById(
                request.getOperatorAirlineId()
        );

        updateFields(aircraft, request, operatorAirline);

        return toResponse(aircraft);
    }

    @Transactional
    public void deleteAircraft(Long aircraftId) {
        aircraftRepository.delete(findAircraftById(aircraftId));
    }

    private Aircraft findAircraftById(Long aircraftId) {
        return aircraftRepository.findById(aircraftId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Bu id ile eşleşen uçak bulunamadı"
                ));
    }

    private Airline findAirlineById(Long airlineId) {
        return airlineRepository.findById(airlineId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Bu id ile eşleşen havayolu bulunamadı"
                ));
    }

    private void updateFields(
            Aircraft aircraft,
            AircraftRequest request,
            Airline operatorAirline
    ) {
        aircraft.setRegistrationNumber(
                request.getRegistrationNumber().toUpperCase(Locale.ROOT)
        );
        aircraft.setModel(request.getModel().trim());
        aircraft.setSeatCapacity(request.getSeatCapacity());
        aircraft.setOperatorAirline(operatorAirline);
    }

    private AircraftResponse toResponse(Aircraft aircraft) {
        AircraftResponse response = new AircraftResponse();
        Airline operatorAirline = aircraft.getOperatorAirline();

        response.setId(aircraft.getId());
        response.setRegistrationNumber(aircraft.getRegistrationNumber());
        response.setModel(aircraft.getModel());
        response.setSeatCapacity(aircraft.getSeatCapacity());
        response.setOperatorAirlineId(operatorAirline.getId());
        response.setOperatorAirlineName(operatorAirline.getName());

        return response;
    }
}
