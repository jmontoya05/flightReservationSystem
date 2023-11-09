package com.makaia.flightReservation.mapper;

import com.makaia.flightReservation.dto.ReservationDTO;
import com.makaia.flightReservation.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    ReservationDTO toDto(Reservation reservation);

    @Mapping(target = "passenger", ignore = true)
    @Mapping(target = "flight", ignore = true)
    Reservation toReservation(ReservationDTO reservationDTO);
}
