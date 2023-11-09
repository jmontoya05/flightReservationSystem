package com.makaia.flightReservation.mapper;

import com.makaia.flightReservation.dto.CityDTO;
import com.makaia.flightReservation.model.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CityMapper {
    CityDTO toDto(City city);
    @Mapping(target = "country", ignore = true)
    City toCity(CityDTO cityDTO);
}
