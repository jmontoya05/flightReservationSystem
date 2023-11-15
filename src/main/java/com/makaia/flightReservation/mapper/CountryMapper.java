package com.makaia.flightReservation.mapper;

import com.makaia.flightReservation.dto.CountryDTO;
import com.makaia.flightReservation.model.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    CountryDTO toDto(Country country);
    @Mapping(target = "cities", ignore = true)
    Country toCountry(CountryDTO countryDTO);
}
