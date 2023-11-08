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
    private final AirlineMapper mapper;

    @Autowired
    public AirlineService(AirlineRepository airlineRepository, AirlineMapper mapper) {
        this.airlineRepository = airlineRepository;
        this.mapper = mapper;
    }

    public AirlineDTO saveAirline(AirlineDTO airlineDTO) {
        airlineDTO.setFlightSequence(0);
        Airline airline = mapper.toAirline(airlineDTO);
        airlineRepository.save(airline);
        return mapper.toDto(airline);

    }

    public Optional<AirlineDTO> getAirline(Integer airlineId) {
        Optional<Airline> airline = airlineRepository.findById(airlineId);
        if (airline.isPresent()) {
            return Optional.of(mapper.toDto(airline.get()));
        }
        throw new RuntimeException();
    }

    public List<AirlineDTO> getAirlines() {
        return airlineRepository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    public AirlineDTO updateAirline(AirlineDTO airlineDTO, Integer airlineId) {
        AirlineDTO airlineToUpdate = this.getAirline(airlineId).orElse(null);
        if (airlineToUpdate != null) {
            Airline airline = mapper.toAirline(airlineToUpdate);
            airline.setAirlineName(airlineDTO.getAirlineName());
            return mapper.toDto(airlineRepository.save(airline));
        }
        throw new RuntimeException();
    }

    public void updateFlightSequence(Airline airline) {
        airlineRepository.save(airline);
    }

    public String deleteAirline(Integer airlineId) {
        AirlineDTO airlineToDelete = this.getAirline(airlineId).orElse(null);
        if (airlineToDelete != null) {
            airlineRepository.deleteById(airlineId);
            return "Airline successfully eliminated";
        }
        throw new RuntimeException();
    }
}
