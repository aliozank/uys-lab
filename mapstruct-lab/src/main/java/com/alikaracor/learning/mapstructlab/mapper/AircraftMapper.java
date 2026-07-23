package com.alikaracor.learning.mapstructlab.mapper;

import com.alikaracor.learning.mapstructlab.dto.AircraftRequest;
import com.alikaracor.learning.mapstructlab.dto.AircraftResponse;
import com.alikaracor.learning.mapstructlab.model.Aircraft;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface AircraftMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modelName", source = "model")
    @Mapping(target = "seatCapacity", source = "capacity")
    @Mapping(target = "operatorAirline", ignore = true)
    Aircraft toEntity(AircraftRequest request);

    @Mapping(target = "aircraftId", source = "id")
    @Mapping(target = "model", source = "modelName")
    @Mapping(target = "capacity", source = "seatCapacity")
    @Mapping(target = "operatorAirlineId", source = "operatorAirline.id")
    @Mapping(target = "operatorAirlineName", source = "operatorAirline.name")
    AircraftResponse toResponse(Aircraft aircraft);

    List<AircraftResponse> toResponseList(List<Aircraft> aircraft);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modelName", source = "model")
    @Mapping(target = "seatCapacity", source = "capacity")
    @Mapping(target = "operatorAirline", ignore = true)
    void updateEntity(
            AircraftRequest request,
            @MappingTarget Aircraft aircraft
    );
}
