package com.makaia.flightReservation.service;

import com.makaia.flightReservation.dto.ReservationDTO;
import com.makaia.flightReservation.mapper.ReservationMapper;
import com.makaia.flightReservation.model.Reservation;
import com.makaia.flightReservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ReservationMapper reservationMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
    }

    public ReservationDTO saveReservation(ReservationDTO reservationDTO){
        Reservation reservation = reservationMapper.toReservation(reservationDTO);
        reservationRepository.save(reservation);
        return reservationMapper.toDto(reservation);
    }
}
