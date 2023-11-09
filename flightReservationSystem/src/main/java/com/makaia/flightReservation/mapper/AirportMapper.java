package com.makaia.flightReservation.mapper;

import com.makaia.flightReservation.dto.AirportDTO;
import com.makaia.flightReservation.model.Airport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AirportMapper {
    AirportDTO toDto(Airport airport);
    @Mapping(target = "city", ignore = true)
    Airport toAirport(AirportDTO airportDTO);
}
