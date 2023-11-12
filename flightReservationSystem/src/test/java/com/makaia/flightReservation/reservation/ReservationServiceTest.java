package com.makaia.flightReservation.reservation;

import com.makaia.flightReservation.dto.PassengerDTO;
import com.makaia.flightReservation.dto.ReservationDTO;
import com.makaia.flightReservation.mapper.ReservationMapper;
import com.makaia.flightReservation.model.Flight;
import com.makaia.flightReservation.model.Reservation;
import com.makaia.flightReservation.repository.FlightRepository;
import com.makaia.flightReservation.repository.ReservationRepository;
import com.makaia.flightReservation.service.FlightService;
import com.makaia.flightReservation.service.PassengerService;
import com.makaia.flightReservation.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReservationServiceTest {
    private ReservationService reservationService;
    private ReservationRepository reservationRepository;
    private ReservationMapper reservationMapper;
    private FlightRepository flightRepository;
    private FlightService flightService;
    private PassengerService passengerService;

    @BeforeEach
    void setUp() {
        reservationRepository = mock(ReservationRepository.class);
        reservationMapper = mock(ReservationMapper.class);
        flightRepository = mock(FlightRepository.class);
        flightService = mock(FlightService.class);
        passengerService = mock(PassengerService.class);
        reservationService = new ReservationService(reservationRepository, reservationMapper, flightRepository, flightService, passengerService);
    }

    @Test
    void saveReservation() {
        // Arrange
        ReservationDTO inputReservationDTO = new ReservationDTO();
        inputReservationDTO.setReservationCode("RES123");
        inputReservationDTO.setFlightCode("ABC123");
        inputReservationDTO.setPassengerId(1);
        inputReservationDTO.setReservationDate(LocalDateTime.now());

        Flight mockFlight = new Flight();
        mockFlight.setFlightCode("ABC123");
        mockFlight.setAvailableSeats(10);
        mockFlight.setDepartureDate(LocalDateTime.now().plusHours(5));
        mockFlight.setReservationsCount(1);

        PassengerDTO mockPassengerDTO = new PassengerDTO();
        mockPassengerDTO.setPassengerId(1);

        Reservation mockReservation = new Reservation();
        mockReservation.setReservationCode("RES123");
        mockReservation.setFlightCode("ABC123");
        mockReservation.setPassengerId(1);
        mockReservation.setReservationDate(LocalDateTime.now());

        when(flightService.getFlightByCode(inputReservationDTO.getFlightCode())).thenReturn(mockFlight);
        when(passengerService.getPassenger(inputReservationDTO.getPassengerId())).thenReturn(mockPassengerDTO);
        when(reservationMapper.toReservation(inputReservationDTO)).thenReturn(mockReservation);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(mockReservation);
        when(reservationMapper.toDto(mockReservation)).thenReturn(inputReservationDTO);

        // Act
        ReservationDTO result = reservationService.saveReservation(inputReservationDTO);

        // Assert
        assertNotNull(result);
        assertEquals(inputReservationDTO.getFlightCode(), result.getFlightCode());
        assertEquals(inputReservationDTO.getPassengerId(), result.getPassengerId());
        assertNotNull(result.getReservationCode());
        assertNotNull(result.getReservationDate());
    }
}
