package com.makaia.flightReservation.service;

import com.makaia.flightReservation.dto.FlightResponseDTO;
import com.makaia.flightReservation.dto.ReservationDTO;
import com.makaia.flightReservation.exception.BadRequestsException;
import com.makaia.flightReservation.mapper.FlightMapper;
import com.makaia.flightReservation.mapper.ReservationMapper;
import com.makaia.flightReservation.model.Flight;
import com.makaia.flightReservation.model.Reservation;
import com.makaia.flightReservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final FlightService flightService;
    private final FlightMapper flightMapper;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ReservationMapper reservationMapper, FlightService flightService, FlightMapper flightMapper) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.flightService = flightService;
        this.flightMapper = flightMapper;
    }

    public ReservationDTO saveReservation(ReservationDTO reservationDTO) {
        FlightResponseDTO flight = flightService.getFlight(reservationDTO.getFlightCode());
        if (flight.getAvailableSeats() < 1){
            throw new RuntimeException("No hay asientos disponibles");
        }

        if (flight.getDepartureDate().isBefore(LocalDateTime.now())){
            throw new RuntimeException("La fecha del vuelo ya pasó");
        }

        Reservation reservation = reservationMapper.toReservation(reservationDTO);
        reservation.setReservationCode(generateReservationCode(reservation.getFlightCode()));
        reservation.setReservationDate(LocalDateTime.now());
        reservationRepository.save(reservation);
        return reservationMapper.toDto(reservation);
    }

    public ReservationDTO getReservation(String reservationCode){
        Optional<Reservation> reservation = reservationRepository.findById(reservationCode);
        if (reservation.isPresent()){
            return reservationMapper.toDto(reservation.get());
        }
        throw new RuntimeException();
    }

    public List<ReservationDTO> getReservations() {
        return reservationRepository.findAll().stream()
                .map(reservationMapper::toDto)
                .collect(Collectors.toList());
    }

    public ReservationDTO updateReservation(ReservationDTO reservationDTO, String reservationCode){
        ReservationDTO reservationToUpdate = getReservation(reservationCode);
        Reservation reservation = reservationMapper.toReservation(reservationToUpdate);
        reservation.setReservationDate(reservationDTO.getReservationDate());

        return reservationMapper.toDto(reservationRepository.save(reservation));
    }
    public boolean deleteReservation(String reservationCode){
        Reservation reservationToDelete = reservationMapper.
                toReservation(getReservation(reservationCode));
        if (reservationToDelete != null){
            reservationRepository.deleteById(reservationCode);
            return true;
        }
        return false;
    }

    private String generateReservationCode(String flightCode) {
        Optional<Flight> optionalFlight = flightService.getFlightByCode(flightCode);

        if (optionalFlight.isEmpty()) {
            throw new BadRequestsException("Flight not found for code: " + flightCode);
        }

        Flight flight = optionalFlight.get();

        int reservationSequence = flight.getReservationsCount() + 1;
        String formattedSequence = String.format("%04d", reservationSequence);
        String reservationCode = "RV" + formattedSequence + "-" + flightCode;

        flight.setReservationsCount(reservationSequence);
        flightService.updateReservationCount(flight);

        return reservationCode;
    }

}
