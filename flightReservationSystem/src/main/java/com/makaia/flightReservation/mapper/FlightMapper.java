package com.makaia.flightReservation.mapper;

import com.makaia.flightReservation.dto.FlightRequestDTO;
import com.makaia.flightReservation.dto.FlightResponseDTO;
import com.makaia.flightReservation.model.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    FlightRequestDTO toRequestDto(Flight flight);

    @Mapping(target = "flightType", ignore = true)
    @Mapping(target = "airportOrigin", ignore = true)
    @Mapping(target = "airportDestination", ignore = true)
    @Mapping(target = "airline", ignore = true)
    Flight toFlight(FlightRequestDTO flightRequestDTO);

    FlightResponseDTO toResponseDto(Flight flight);



}
