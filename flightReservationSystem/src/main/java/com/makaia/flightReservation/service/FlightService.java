package com.makaia.flightReservation.service;

import com.makaia.flightReservation.dto.AirlineDTO;
import com.makaia.flightReservation.dto.FlightRequestDTO;
import com.makaia.flightReservation.dto.FlightResponseDTO;
import com.makaia.flightReservation.mapper.AirlineMapper;
import com.makaia.flightReservation.mapper.FlightMapper;
import com.makaia.flightReservation.model.Airline;
import com.makaia.flightReservation.model.Flight;
import com.makaia.flightReservation.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<FlightResponseDTO> getFlights(){
        return flightRepository.findAll().stream()
                .map(flightMapper::toResponseDto)
                .collect(Collectors.toList());
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
