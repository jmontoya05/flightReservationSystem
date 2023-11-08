package com.makaia.flightReservation.mapper;

import com.makaia.flightReservation.dto.AirlineDTO;
import com.makaia.flightReservation.model.Airline;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AirlineMapper {
    AirlineDTO toDto(Airline airline);
    Airline toAirline(AirlineDTO airlineDTO);
}
