package com.makaia.flightReservation.flight;

import com.makaia.flightReservation.dto.AirlineDTO;
import com.makaia.flightReservation.dto.AirportDTO;
import com.makaia.flightReservation.dto.FlightRequestDTO;
import com.makaia.flightReservation.dto.FlightResponseDTO;
import com.makaia.flightReservation.dto.FlightTypeDTO;
import com.makaia.flightReservation.exception.BadRequestException;
import com.makaia.flightReservation.exception.NotFoundException;
import com.makaia.flightReservation.mapper.AirlineMapper;
import com.makaia.flightReservation.mapper.FlightMapper;
import com.makaia.flightReservation.model.Airline;
import com.makaia.flightReservation.model.Flight;
import com.makaia.flightReservation.repository.AirportRepository;
import com.makaia.flightReservation.repository.CityRepository;
import com.makaia.flightReservation.repository.FlightRepository;
import com.makaia.flightReservation.repository.FlightTypeRepository;
import com.makaia.flightReservation.service.AirlineService;
import com.makaia.flightReservation.service.FlightService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FlightServiceTest {
    private FlightService flightService;
    private FlightRepository flightRepository;
    private AirportRepository airportRepository;
    private FlightTypeRepository flightTypeRepository;
    private AirlineService airlineService;
    private FlightMapper flightMapper;
    private AirlineMapper airlineMapper;

    @BeforeEach
    void setUp() {
        flightRepository = mock(FlightRepository.class);
        CityRepository cityRepository = mock(CityRepository.class);
        airportRepository = mock(AirportRepository.class);
        flightTypeRepository = mock(FlightTypeRepository.class);
        airlineService = mock(AirlineService.class);
        flightMapper = mock(FlightMapper.class);
        airlineMapper = mock(AirlineMapper.class);
        flightService = new FlightService(flightRepository, cityRepository, airportRepository, flightTypeRepository, airlineService, flightMapper, airlineMapper);
    }

    @Test
    void saveFlight() {
        // Arrange
        FlightRequestDTO inputFlightRequestDTO = new FlightRequestDTO();
        inputFlightRequestDTO.setFlightCode("ABC123");
        inputFlightRequestDTO.setAirlineId(1);
        inputFlightRequestDTO.setFlightTypeId(2);
        inputFlightRequestDTO.setAirportOriginId(3);
        inputFlightRequestDTO.setAirportDestinationId(4);
        inputFlightRequestDTO.setDepartureDate(LocalDateTime.now());
        inputFlightRequestDTO.setArrivalDate(LocalDateTime.now().plusHours(3));
        inputFlightRequestDTO.setPrice(new BigDecimal("150.50"));
        inputFlightRequestDTO.setAvailableSeats(100);

        Flight inputFlight = new Flight();
        inputFlight.setFlightCode("ABC123");
        inputFlight.setAirlineId(1);
        inputFlight.setFlightTypeId(2);
        inputFlight.setAirportOriginId(3);
        inputFlight.setAirportDestinationId(4);
        inputFlight.setDepartureDate(LocalDateTime.now());
        inputFlight.setArrivalDate(LocalDateTime.now().plusHours(3));
        inputFlight.setPrice(new BigDecimal("150.50"));
        inputFlight.setAvailableSeats(100);

        when(flightMapper.toFlight(inputFlightRequestDTO)).thenReturn(inputFlight);
        when(flightTypeRepository.existsById(inputFlightRequestDTO.getFlightTypeId())).thenReturn(true);
        when(airportRepository.existsById(inputFlightRequestDTO.getAirportOriginId())).thenReturn(true);
        when(airportRepository.existsById(inputFlightRequestDTO.getAirportDestinationId())).thenReturn(true);
        when(airlineService.getAirline(anyInt())).thenReturn(new AirlineDTO());
        when(airlineMapper.toAirline(any())).thenReturn(new Airline(1, "Airline", 0));
        when(flightRepository.save(any())).thenReturn(inputFlight);
        when(flightMapper.toRequestDto(inputFlight)).thenReturn(inputFlightRequestDTO);

        // Act
        FlightRequestDTO result = flightService.saveFlight(inputFlightRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals(inputFlightRequestDTO.getFlightCode(), result.getFlightCode());
        assertEquals(inputFlightRequestDTO.getAirlineId(), result.getAirlineId());
        assertEquals(inputFlightRequestDTO.getFlightTypeId(), result.getFlightTypeId());
        assertEquals(inputFlightRequestDTO.getAirportOriginId(), result.getAirportOriginId());
        assertEquals(inputFlightRequestDTO.getAirportDestinationId(), result.getAirportDestinationId());
        assertEquals(inputFlightRequestDTO.getDepartureDate(), result.getDepartureDate());
        assertEquals(inputFlightRequestDTO.getArrivalDate(), result.getArrivalDate());
        assertEquals(inputFlightRequestDTO.getPrice(), result.getPrice());
        assertEquals(inputFlightRequestDTO.getAvailableSeats(), result.getAvailableSeats());
    }

    @Test
    void saveFlightWhenFlightTypeIdIsInvalid() {
        // Arrange
        FlightRequestDTO inputFlightRequestDTO = new FlightRequestDTO();
        inputFlightRequestDTO.setFlightCode("ABC123");
        inputFlightRequestDTO.setAirlineId(1);
        inputFlightRequestDTO.setFlightTypeId(null);
        inputFlightRequestDTO.setAirportOriginId(3);
        inputFlightRequestDTO.setAirportDestinationId(4);
        inputFlightRequestDTO.setDepartureDate(LocalDateTime.now());
        inputFlightRequestDTO.setArrivalDate(LocalDateTime.now().plusHours(3));
        inputFlightRequestDTO.setPrice(new BigDecimal("150.50"));
        inputFlightRequestDTO.setAvailableSeats(100);

        when(flightTypeRepository.existsById(any())).thenReturn(false);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> flightService.saveFlight(inputFlightRequestDTO));
    }

    @Test
    void saveFlightWhenAirportOriginIdIsInvalid() {
        // Arrange
        FlightRequestDTO inputFlightRequestDTO = new FlightRequestDTO();
        inputFlightRequestDTO.setFlightCode("ABC123");
        inputFlightRequestDTO.setAirlineId(1);
        inputFlightRequestDTO.setFlightTypeId(2);
        inputFlightRequestDTO.setAirportOriginId(null);
        inputFlightRequestDTO.setAirportDestinationId(4);
        inputFlightRequestDTO.setDepartureDate(LocalDateTime.now());
        inputFlightRequestDTO.setArrivalDate(LocalDateTime.now().plusHours(3));
        inputFlightRequestDTO.setPrice(new BigDecimal("150.50"));
        inputFlightRequestDTO.setAvailableSeats(100);

        when(flightTypeRepository.existsById(any())).thenReturn(false);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> flightService.saveFlight(inputFlightRequestDTO));
    }

    @Test
    void saveFlightWhenAirportDestinationIdIsInvalid() {
        // Arrange
        FlightRequestDTO inputFlightRequestDTO = new FlightRequestDTO();
        inputFlightRequestDTO.setFlightCode("ABC123");
        inputFlightRequestDTO.setAirlineId(1);
        inputFlightRequestDTO.setFlightTypeId(2);
        inputFlightRequestDTO.setAirportOriginId(3);
        inputFlightRequestDTO.setAirportDestinationId(null);
        inputFlightRequestDTO.setDepartureDate(LocalDateTime.now());
        inputFlightRequestDTO.setArrivalDate(LocalDateTime.now().plusHours(3));
        inputFlightRequestDTO.setPrice(new BigDecimal("150.50"));
        inputFlightRequestDTO.setAvailableSeats(100);

        when(flightTypeRepository.existsById(any())).thenReturn(false);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> flightService.saveFlight(inputFlightRequestDTO));
    }

    @Test
    void saveFlightWhenAirlineIdIsInvalid() {
        // Arrange
        FlightRequestDTO inputFlightRequestDTO = new FlightRequestDTO();
        inputFlightRequestDTO.setFlightCode("ABC123");
        inputFlightRequestDTO.setAirlineId(null);
        inputFlightRequestDTO.setFlightTypeId(2);
        inputFlightRequestDTO.setAirportOriginId(3);
        inputFlightRequestDTO.setAirportDestinationId(4);
        inputFlightRequestDTO.setDepartureDate(LocalDateTime.now());
        inputFlightRequestDTO.setArrivalDate(LocalDateTime.now().plusHours(3));
        inputFlightRequestDTO.setPrice(new BigDecimal("150.50"));
        inputFlightRequestDTO.setAvailableSeats(100);

        when(flightTypeRepository.existsById(any())).thenReturn(true);
        when(airlineService.getAirline(anyInt())).thenThrow(new NotFoundException("Airline not found"));

        // Act and Assert
        assertThrows(NotFoundException.class, () -> flightService.saveFlight(inputFlightRequestDTO));
    }

    @Test
    void saveFlightWhenArrivalDateIsBeforeThanDepartureDate() {
        // Arrange
        FlightRequestDTO inputFlightRequestDTO = new FlightRequestDTO();
        inputFlightRequestDTO.setFlightCode("ABC123");
        inputFlightRequestDTO.setAirlineId(1);
        inputFlightRequestDTO.setFlightTypeId(2);
        inputFlightRequestDTO.setAirportOriginId(3);
        inputFlightRequestDTO.setAirportDestinationId(4);
        inputFlightRequestDTO.setDepartureDate(LocalDateTime.now());
        inputFlightRequestDTO.setArrivalDate(LocalDateTime.now().minusHours(1));
        inputFlightRequestDTO.setPrice(new BigDecimal("150.50"));
        inputFlightRequestDTO.setAvailableSeats(100);

        Flight inputFlight = new Flight();
        inputFlight.setFlightCode("ABC123");
        inputFlight.setAirlineId(1);
        inputFlight.setFlightTypeId(2);
        inputFlight.setAirportOriginId(3);
        inputFlight.setAirportDestinationId(4);
        inputFlight.setDepartureDate(LocalDateTime.now());
        inputFlight.setArrivalDate(LocalDateTime.now().minusHours(1));
        inputFlight.setPrice(new BigDecimal("150.50"));
        inputFlight.setAvailableSeats(100);

        when(flightTypeRepository.existsById(any())).thenReturn(true);
        when(airportRepository.existsById(any())).thenReturn(true);
        when(flightMapper.toFlight(inputFlightRequestDTO)).thenReturn(inputFlight);
        when(airlineService.getAirline(anyInt())).thenReturn(new AirlineDTO());

        // Act and Assert
        assertThrows(BadRequestException.class, () -> flightService.saveFlight(inputFlightRequestDTO));
    }

    @Test
    void getFlight() {
        // Arrange
        String validFlightCode = "ABC123";

        FlightResponseDTO expectedFlightResponseDTO = new FlightResponseDTO();
        expectedFlightResponseDTO.setFlightCode("ABC123");
        expectedFlightResponseDTO.setAirline(new AirlineDTO());
        expectedFlightResponseDTO.setFlightType(new FlightTypeDTO());
        expectedFlightResponseDTO.setAirportOrigin(new AirportDTO());
        expectedFlightResponseDTO.setAirportDestination(new AirportDTO());
        expectedFlightResponseDTO.setDepartureDate(LocalDateTime.now());
        expectedFlightResponseDTO.setArrivalDate(LocalDateTime.now().plusHours(3));
        expectedFlightResponseDTO.setPrice(new BigDecimal("150.50"));
        expectedFlightResponseDTO.setAvailableSeats(100);
        expectedFlightResponseDTO.setReservationsCount(0);

        Flight expectedFlight = new Flight();
        expectedFlight.setFlightCode("ABC123");

        when(flightRepository.findById(validFlightCode)).thenReturn(Optional.of(expectedFlight));
        when(flightMapper.toResponseDto(expectedFlight)).thenReturn(expectedFlightResponseDTO);

        // Act
        FlightResponseDTO result = flightService.getFlight(validFlightCode);

        // Assert
        assertNotNull(result);
        assertEquals(expectedFlightResponseDTO.getFlightCode(), result.getFlightCode());
        assertEquals(expectedFlightResponseDTO.getAirline(), result.getAirline());
        assertEquals(expectedFlightResponseDTO.getFlightType(), result.getFlightType());
        assertEquals(expectedFlightResponseDTO.getAirportOrigin(), result.getAirportOrigin());
        assertEquals(expectedFlightResponseDTO.getAirportDestination(), result.getAirportDestination());
        assertEquals(expectedFlightResponseDTO.getDepartureDate(), result.getDepartureDate());
        assertEquals(expectedFlightResponseDTO.getArrivalDate(), result.getArrivalDate());
        assertEquals(expectedFlightResponseDTO.getPrice(), result.getPrice());
        assertEquals(expectedFlightResponseDTO.getAvailableSeats(), result.getAvailableSeats());
        assertEquals(expectedFlightResponseDTO.getReservationsCount(), result.getReservationsCount());
    }

    @Test
    void getFlightWhenFlightCodeIsInvalid() {
        // Arrange
        String invalidFlightCode = "INVALID";

        when(flightRepository.findById(invalidFlightCode)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> flightService.getFlight(invalidFlightCode));
    }
}


