package com.makaia.flightReservation.service;

import com.makaia.flightReservation.dto.AirlineDTO;
import com.makaia.flightReservation.mapper.AirlineMapper;
import com.makaia.flightReservation.model.Airline;
import com.makaia.flightReservation.model.Flight;
import com.makaia.flightReservation.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {
    private final FlightRepository flightRepository;
    private final AirlineService airlineService;
    private final AirlineMapper airlineMapper;
    @Autowired
    public FlightService(FlightRepository flightRepository, AirlineService airlineService, AirlineMapper airlineMapper) {
        this.flightRepository = flightRepository;
        this.airlineService = airlineService;
        this.airlineMapper = airlineMapper;
    }

    public Flight saveFlight(Flight flight){
        flight.setFlightCode(generateFlightCode(flight.getAirlineId()));
        return flightRepository.save(flight);
    }

    public Optional<Flight> getFlight(String flightCode){
        return flightRepository.findById(flightCode);
    }

    public List<Flight> getFlights(){
        return flightRepository.findAll();
    }

    private String generateFlightCode(Integer airlineId){
        Optional<AirlineDTO> optionalAirline = airlineService.getAirline(airlineId);

        if (optionalAirline.isEmpty()){
            throw new RuntimeException();
        }
        Airline airline = airlineMapper.toAirline(optionalAirline.get());
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
