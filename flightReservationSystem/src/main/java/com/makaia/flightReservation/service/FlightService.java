package com.makaia.flightReservation.service;

import com.makaia.flightReservation.dto.AirlineDTO;
import com.makaia.flightReservation.dto.FlightCustomPage;
import com.makaia.flightReservation.dto.FlightRequestDTO;
import com.makaia.flightReservation.dto.FlightResponseDTO;
import com.makaia.flightReservation.mapper.AirlineMapper;
import com.makaia.flightReservation.mapper.FlightMapper;
import com.makaia.flightReservation.model.Airline;
import com.makaia.flightReservation.model.Flight;
import com.makaia.flightReservation.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final AirlineService airlineService;
    private final FlightMapper flightMapper;
    private final AirlineMapper airlineMapper;
    @Autowired
    public FlightService(FlightRepository flightRepository, AirlineService airlineService, FlightMapper flightMapper, AirlineMapper airlineMapper) {
        this.flightRepository = flightRepository;
        this.airlineService = airlineService;
        this.flightMapper = flightMapper;
        this.airlineMapper = airlineMapper;
    }

    public FlightRequestDTO saveFlight(FlightRequestDTO flightRequestDTO){
        Flight flight = flightMapper.toFlight(flightRequestDTO);
        flight.setFlightCode(generateFlightCode(flight.getAirlineId()));
        flightRepository.save(flight);
        return flightMapper.toRequestDto(flight);
    }

    public FlightResponseDTO getFlight(String flightCode){
        Optional<Flight> flight = flightRepository.findById(flightCode);
        if (flight.isPresent()){
            return flightMapper.toResponseDto(flight.get());
        }
        throw new RuntimeException();
    }

    public FlightCustomPage getFlights(Integer airportOriginId, Integer airportDestinationId, LocalDate departureDate, int page, int pageSize){

        boolean airportOrigin = airportOriginId != null;
        boolean airportDestination = airportDestinationId != null;
        boolean date = departureDate != null;

        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        Page<Flight> flights;

        if (date){
            LocalDateTime startDay = departureDate.atStartOfDay();
            LocalDateTime endDay = departureDate.atTime(LocalTime.MAX);
            if (airportOrigin && airportDestination){
                flights = flightRepository.findByAirportOriginIdAndAirportDestinationIdAndDepartureDateBetween(airportOriginId, airportDestinationId, startDay, endDay, pageRequest);
            } else if (airportOrigin){
                flights = flightRepository.findByAirportOriginIdAndDepartureDateBetween(airportOriginId, startDay, endDay, pageRequest);
            } else if (airportDestination){
                flights = flightRepository.findByAirportDestinationIdAndDepartureDateBetween(airportDestinationId, startDay, endDay, pageRequest);
            } else {
                flights = flightRepository.findByDepartureDateBetween(startDay, endDay, pageRequest);
            }
        } else if (airportOrigin){
            if (airportDestination){
                flights = flightRepository.findByAirportOriginIdAndAirportDestinationId(airportOriginId, airportDestinationId, pageRequest);
            } else {
                flights = flightRepository.findByAirportOriginId(airportOriginId, pageRequest);
            }
        } else if (airportDestination) {
            flights = flightRepository.findByAirportDestinationId(airportDestinationId, pageRequest);
        } else {
            flights = flightRepository.findAll(pageRequest);
        }

        List<FlightResponseDTO> flightsDto = flights.stream()
                .map(flightMapper::toResponseDto)
                .collect(Collectors.toList());

        return new FlightCustomPage(flights.getNumber() + 1, flights.getSize(), flights.getNumberOfElements(), flights.getTotalElements(), flightsDto);
    }

    private String generateFlightCode(Integer airlineId){
        AirlineDTO airlineDTO = airlineService.getAirline(airlineId);
        Airline airline = airlineMapper.toAirline(airlineDTO);
        String airlineName = airline.getAirlineName();
        Integer flightSequence = airline.getFlightSequence();

        flightSequence++;
        String airlineCode = airlineName.substring(0,2).toUpperCase();
        String formattedSequence = String.format("%04d", flightSequence);
        String flightCode = airlineCode + formattedSequence;

        airline.setFlightSequence(flightSequence);
        airlineService.updateFlightSequence(airline);

        return flightCode;
    }

}
