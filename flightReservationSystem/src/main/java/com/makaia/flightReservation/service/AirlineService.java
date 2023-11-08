package com.makaia.flightReservation.service;

import com.makaia.flightReservation.dto.AirlineDTO;
import com.makaia.flightReservation.mapper.AirlineMapper;
import com.makaia.flightReservation.model.Airline;
import com.makaia.flightReservation.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        airlineDTO.setFlightSequence(0);
        Airline airline = airlineMapper.toAirline(airlineDTO);
        airlineRepository.save(airline);
        return airlineMapper.toDto(airline);

    }

    public AirlineDTO getAirline(Integer airlineId) {
        Optional<Airline> airline = airlineRepository.findById(airlineId);
        if (airline.isPresent()) {
            return airlineMapper.toDto(airline.get());
        }
        throw new RuntimeException();
    }

    public List<AirlineDTO> getAirlines() {
        return airlineRepository.findAll().stream()
                .map(airlineMapper::toDto)
                .collect(Collectors.toList());
    }

    public AirlineDTO updateAirline(AirlineDTO airlineDTO, Integer airlineId) {
        AirlineDTO airlineToUpdate = this.getAirline(airlineId);

        Airline airline = airlineMapper.toAirline(airlineToUpdate);
        airline.setAirlineName(airlineDTO.getAirlineName());
        return airlineMapper.toDto(airlineRepository.save(airline));

    }

    public void updateFlightSequence(Airline airline) {
        airlineRepository.save(airline);
    }

    public String deleteAirline(Integer airlineId) {
        AirlineDTO airlineToDelete = this.getAirline(airlineId);
        if (airlineToDelete != null) {
            airlineRepository.deleteById(airlineId);
            return "Airline successfully eliminated";
        }
        throw new RuntimeException();
    }
}
