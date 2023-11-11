package com.makaia.flightReservation.service;

import com.makaia.flightReservation.dto.AirlineDTO;
import com.makaia.flightReservation.exception.BadRequestsException;
import com.makaia.flightReservation.exception.InternalServerErrorException;
import com.makaia.flightReservation.exception.NotFoundException;
import com.makaia.flightReservation.mapper.AirlineMapper;
import com.makaia.flightReservation.model.Airline;
import com.makaia.flightReservation.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirlineService {
    private final AirlineRepository airlineRepository;
    private final AirlineMapper airlineMapper;

    @Autowired
    public AirlineService(AirlineRepository airlineRepository, AirlineMapper airlineMapper) {
        this.airlineRepository = airlineRepository;
        this.airlineMapper = airlineMapper;
    }

    public AirlineDTO saveAirline(AirlineDTO airlineDTO) {
        try {
            airlineDTO.setFlightSequence(0);
            Airline airline = airlineMapper.toAirline(airlineDTO);
            airlineRepository.save(airline);
            return airlineMapper.toDto(airline);
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while saving airline: " + e.getMessage());
        }
    }

    public AirlineDTO getAirline(Integer airlineId) {
        return airlineRepository.findById(airlineId)
                .map(airlineMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Airline not found with ID: " + airlineId));
    }

    public List<AirlineDTO> getAirlines() {
        try {
            return airlineRepository.findAll().stream()
                    .map(airlineMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while searching for all airlines: " + e.getMessage());
        }
    }

    public AirlineDTO updateAirline(AirlineDTO airlineDTO, Integer airlineId) {
        Airline airline = airlineRepository.findById(airlineId)
                .orElseThrow(() -> new NotFoundException("Airline not found with ID: " + airlineId));

        airline.setAirlineName(airlineDTO.getAirlineName());

        return airlineMapper.toDto(airlineRepository.save(airline));
    }

    public void updateFlightSequence(Airline airline) {
        airlineRepository.save(airline);
    }

    public void deleteAirline(Integer airlineId) {
        if (!airlineRepository.existsById(airlineId)) {
            throw new NotFoundException("Airline not found with ID: " + airlineId);
        }
        airlineRepository.deleteById(airlineId);
    }
}
