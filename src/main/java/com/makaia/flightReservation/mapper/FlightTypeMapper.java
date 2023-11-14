package com.makaia.flightReservation.mapper;

import com.makaia.flightReservation.dto.FlightTypeDTO;
import com.makaia.flightReservation.model.FlightType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FlightTypeMapper {
    FlightTypeDTO toDto(FlightType flightType);

    FlightType toFlightType(FlightTypeDTO flightTypeDTO);
}
