package com.makaia.flightReservation.mapper;

import com.makaia.flightReservation.dto.PassengerDTO;
import com.makaia.flightReservation.model.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PassengerMapper {
    PassengerDTO toDto(Passenger passenger);

    @Mapping(target = "reservations", ignore = true)
    Passenger toPassenger(PassengerDTO passengerDTO);
}
