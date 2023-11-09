package com.makaia.flightReservation.mapper;

import com.makaia.flightReservation.dto.CountryDTO;
import com.makaia.flightReservation.model.Country;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper {
    CountryDTO toDto(Country country);
    Country toCountry(CountryDTO countryDTO);
}
