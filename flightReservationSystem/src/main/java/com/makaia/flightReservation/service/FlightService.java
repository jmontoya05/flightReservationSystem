package com.makaia.flightReservation.service;

import com.makaia.flightReservation.dto.AirlineDTO;
import com.makaia.flightReservation.dto.FlightCustomPage;
import com.makaia.flightReservation.dto.FlightRequestDTO;
import com.makaia.flightReservation.dto.FlightResponseDTO;
import com.makaia.flightReservation.exception.BadRequestException;
import com.makaia.flightReservation.exception.InternalServerErrorException;
import com.makaia.flightReservation.exception.NotFoundException;
import com.makaia.flightReservation.mapper.AirlineMapper;
import com.makaia.flightReservation.mapper.FlightMapper;
import com.makaia.flightReservation.model.Airline;
import com.makaia.flightReservation.model.Airport;
import com.makaia.flightReservation.model.City;
import com.makaia.flightReservation.model.Flight;
import com.makaia.flightReservation.repository.AirportRepository;
import com.makaia.flightReservation.repository.CityRepository;
import com.makaia.flightReservation.repository.FlightRepository;
import com.makaia.flightReservation.repository.FlightTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final CityRepository cityRepository;
    private final AirportRepository airportRepository;
    private final FlightTypeRepository flightTypeRepository;
    private final AirlineService airlineService;
    private final FlightMapper flightMapper;
    private final AirlineMapper airlineMapper;

    @Autowired
    public FlightService(FlightRepository flightRepository, CityRepository cityRepository, AirportRepository airportRepository, FlightTypeRepository flightTypeRepository, AirlineService airlineService, FlightMapper flightMapper, AirlineMapper airlineMapper) {
        this.flightRepository = flightRepository;
        this.cityRepository = cityRepository;
        this.airportRepository = airportRepository;
        this.flightTypeRepository = flightTypeRepository;
        this.airlineService = airlineService;
        this.flightMapper = flightMapper;
        this.airlineMapper = airlineMapper;
    }

    public FlightRequestDTO saveFlight(FlightRequestDTO flightRequestDTO) {

        validateIdsExistence(flightRequestDTO.getFlightTypeId(), flightRequestDTO.getAirportOriginId(), flightRequestDTO.getAirportDestinationId());
        Flight flight = flightMapper.toFlight(flightRequestDTO);

        validateDates(flight.getDepartureDate(), flight.getArrivalDate());

        flight.setFlightCode(generateFlightCode(flight.getAirlineId()));
        flight.setReservationsCount(0);
        try {
            return flightMapper.toRequestDto(flightRepository.save(flight));
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while saving flight: " + e.getMessage());
        }
    }

    public FlightResponseDTO getFlight(String flightCode) {
        return flightRepository.findById(flightCode)
                .map(flightMapper::toResponseDto)
                .orElseThrow(() -> new NotFoundException("Flight not found with ID: " + flightCode));
    }

    public Flight getFlightByCode(String flightCode) {
        return flightRepository.findById(flightCode)
                .orElseThrow(() -> new NotFoundException("Flight not found with ID: " + flightCode));
    }

    public FlightCustomPage getFlights(String cityOrigin, String cityDestination, LocalDate departureDate, int page, int pageSize) {
        try {

            List<Integer> airportsOrigin = Optional.ofNullable(cityOrigin).map(this::getAirports).orElse(null);
            List<Integer> airportsDestination = Optional.ofNullable(cityDestination).map(this::getAirports).orElse(null);

            PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
            Page<Flight> flights = queryFlights(airportsOrigin, airportsDestination, departureDate, pageRequest);

            List<FlightResponseDTO> flightsDto = flights
                    .map(flightMapper::toResponseDto)
                    .getContent();

            return new FlightCustomPage(flights.getNumber() + 1, flights.getSize(), flights.getNumberOfElements(), flights.getTotalElements(), flightsDto);
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while searching for flights: " + e.getMessage());
        }
    }

    public FlightRequestDTO updateFlight(FlightRequestDTO flightRequestDTO, String flightCode) {

        validateIdsExistence(flightRequestDTO.getFlightTypeId(), flightRequestDTO.getAirportOriginId(), flightRequestDTO.getAirportDestinationId());
        Flight flight = flightRepository.findById(flightCode)
                .orElseThrow(() -> new NotFoundException("Flight not found with ID: " + flightCode));

        updateFlightProperties(flight, flightRequestDTO);
        validateDates(flight.getDepartureDate(), flight.getArrivalDate());
        try {
            return flightMapper.toRequestDto(flightRepository.save(flight));
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while updating flight: " + e.getMessage());
        }
    }

    public void updateReservationCount(Flight flight) {
        try {
            flightRepository.save(flight);
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while updating flight reservation count: " + e.getMessage());
        }
    }

    public void deleteFlight(String flightCode) {
        if (!flightRepository.existsById(flightCode)) {
            throw new NotFoundException("Flight not found with ID: " + flightCode);
        }
        try {
            flightRepository.deleteById(flightCode);
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while deleting flight: " + e.getMessage());
        }
    }

    private void validateIdsExistence(Integer flightTypeId, Integer airportOriginId, Integer airportDestinationId) {
        if (!flightTypeRepository.existsById(flightTypeId)) {
            throw new NotFoundException("Flight Type not found with ID: " + flightTypeId);
        }

        if (!airportRepository.existsById(airportOriginId)) {
            throw new NotFoundException("Airport Origin not found with ID: " + airportOriginId);
        }

        if (!airportRepository.existsById(airportDestinationId)) {
            throw new NotFoundException("Airport Destination not found with ID: " + airportDestinationId);
        }
    }

    private void validateDates(LocalDateTime departureDate, LocalDateTime arrivalDate) {
        if (arrivalDate.isBefore(departureDate)) {
            throw new BadRequestException("Arrival date must be after departure date.");
        }
    }

    private String generateFlightCode(Integer airlineId) {
        AirlineDTO airlineDTO = airlineService.getAirline(airlineId);
        Airline airline = airlineMapper.toAirline(airlineDTO);
        String airlineName = airline.getAirlineName();
        Integer flightSequence = airline.getFlightSequence();
        flightSequence++;

        String airlineCode = airlineName.substring(0, 2).toUpperCase();
        String formattedSequence = String.format("%04d", flightSequence);
        String flightCode = airlineCode + formattedSequence;

        airline.setFlightSequence(flightSequence);
        airlineService.updateFlightSequence(airline);

        return flightCode;
    }

    public Page<Flight> queryFlights(List<Integer> airportsOrigin, List<Integer> airportsDestination, LocalDate departureDate, PageRequest pageRequest) {
        LocalDateTime startDay = departureDate != null ? departureDate.atStartOfDay() : null;
        LocalDateTime endDay = departureDate != null ? departureDate.atTime(LocalTime.of(23, 59, 59)) : null;

        if (airportsOrigin != null && airportsDestination != null && departureDate != null) {
            return flightRepository.findByAirportOriginIdInAndAirportDestinationIdInAndDepartureDateBetween(airportsOrigin, airportsDestination, startDay, endDay, pageRequest);
        } else if (airportsOrigin != null && airportsDestination != null) {
            return flightRepository.findByAirportOriginIdInAndAirportDestinationIdIn(airportsOrigin, airportsDestination, pageRequest);
        } else if (airportsOrigin != null && departureDate != null) {
            return flightRepository.findByAirportOriginIdInAndDepartureDateBetween(airportsOrigin, startDay, endDay, pageRequest);
        } else if (airportsDestination != null && departureDate != null) {
            return flightRepository.findByAirportDestinationIdInAndDepartureDateBetween(airportsDestination, startDay, endDay, pageRequest);
        } else if (airportsOrigin != null) {
            return flightRepository.findByAirportOriginIdIn(airportsOrigin, pageRequest);
        } else if (airportsDestination != null) {
            return flightRepository.findByAirportDestinationIdIn(airportsDestination, pageRequest);
        } else if (departureDate != null) {
            return flightRepository.findByDepartureDateBetween(startDay, endDay, pageRequest);
        } else {
            return flightRepository.findAll(pageRequest);
        }
    }

    private City getCity(String cityName) {
        try {
            return cityRepository.findByCity(cityName);
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while searching city name: " + e.getMessage());
        }
    }

    public List<Integer> getAirports(String cityName) {
        City city = getCity(cityName.toLowerCase());
        if (city == null) {
            return Collections.emptyList();
        }

        Integer cityId = city.getCityId();
        List<Airport> airports = airportRepository.findByCityId(cityId);

        return airports.stream()
                .map(Airport::getAirportId)
                .collect(Collectors.toList());
    }

    private void updateFlightProperties(Flight flight, FlightRequestDTO flightRequestDTO) {
        flight.setAirlineId(flightRequestDTO.getAirlineId());
        flight.setFlightTypeId(flightRequestDTO.getFlightTypeId());
        flight.setAirportOriginId(flightRequestDTO.getAirportOriginId());
        flight.setAirportDestinationId(flightRequestDTO.getAirportDestinationId());
        flight.setDepartureDate(flightRequestDTO.getDepartureDate());
        flight.setArrivalDate(flightRequestDTO.getArrivalDate());
        flight.setPrice(flightRequestDTO.getPrice());
        flight.setAvailableSeats(flightRequestDTO.getAvailableSeats());
    }

}
