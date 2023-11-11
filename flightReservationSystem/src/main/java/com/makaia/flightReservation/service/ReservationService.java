package com.makaia.flightReservation.service;

import com.makaia.flightReservation.dto.ReservationDTO;
import com.makaia.flightReservation.exception.BadRequestException;
import com.makaia.flightReservation.exception.InternalServerErrorException;
import com.makaia.flightReservation.exception.NotFoundException;
import com.makaia.flightReservation.mapper.ReservationMapper;
import com.makaia.flightReservation.model.Flight;
import com.makaia.flightReservation.model.Reservation;
import com.makaia.flightReservation.repository.FlightRepository;
import com.makaia.flightReservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final FlightRepository flightRepository;
    private final FlightService flightService;
    private final PassengerService passengerService;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ReservationMapper reservationMapper, FlightRepository flightRepository, FlightService flightService, PassengerService passengerService) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.flightRepository = flightRepository;
        this.flightService = flightService;
        this.passengerService = passengerService;
    }

    public ReservationDTO saveReservation(ReservationDTO reservationDTO) {
        Flight flight = flightService.getFlightByCode(reservationDTO.getFlightCode());
        passengerService.getPassenger(reservationDTO.getPassengerId());

        if (flight.getAvailableSeats() <= 0) {
            throw new BadRequestException("No seats available for the flight");
        }

        LocalDateTime now = LocalDateTime.now();
        if (flight.getDepartureDate().minusHours(3).isBefore(now)) {
            throw new BadRequestException("It's too late to make a reservation for this flight");
        }

        Reservation reservation = reservationMapper.toReservation(reservationDTO);
        reservation.setReservationCode(generateReservationCode(flight));
        reservation.setReservationDate(now);
        try {
            reservationRepository.save(reservation);
            flight.setAvailableSeats(flight.getAvailableSeats() - 1);
            flightRepository.save(flight);
            return reservationMapper.toDto(reservation);
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while saving reservation: " + e.getMessage());
        }
    }

    public ReservationDTO getReservation(String reservationCode) {
        return reservationRepository.findById(reservationCode)
                .map(reservationMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Reservation not found with ID: " + reservationCode));
    }

    public List<ReservationDTO> getAllReservations() {
        try {
            return reservationRepository.findAll().stream()
                    .map(reservationMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while searching for flights: " + e.getMessage());
        }
    }

    public List<ReservationDTO> getReservationsByPassengerId(Integer passengerId) {

        List<Reservation> reservations = reservationRepository.findByPassengerId(passengerId);

        if (reservations.isEmpty()) {
            throw new NotFoundException("No reservations found for Passenger ID: " + passengerId);
        }

        return reservations.stream()
                .map(reservationMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteReservation(String reservationCode) {
        Reservation reservation = reservationRepository.findById(reservationCode)
                .orElseThrow(() -> new NotFoundException("Reservation not found with ID: " + reservationCode));

        Flight flight = flightRepository.findById(reservation.getFlightCode())
                .orElseThrow(() -> new NotFoundException("Flight not found with ID: " + reservation.getFlightCode()));
        flight.setAvailableSeats(flight.getAvailableSeats() + 1);
        flightRepository.save(flight);
        try {
            reservationRepository.deleteById(reservationCode);
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while deleting reservation: " + e.getMessage());
        }

    }

    private String generateReservationCode(Flight flight) {

        int reservationSequence = flight.getReservationsCount() + 1;
        String formattedSequence = String.format("%04d", reservationSequence);
        String reservationCode = "RV" + formattedSequence + "-" + flight.getFlightCode();

        flight.setReservationsCount(reservationSequence);
        flightService.updateReservationCount(flight);

        return reservationCode;
    }
}
