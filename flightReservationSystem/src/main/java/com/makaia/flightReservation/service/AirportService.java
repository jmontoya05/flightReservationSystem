package com.makaia.flightReservation.service;

import com.makaia.flightReservation.dto.AirportDTO;
import com.makaia.flightReservation.mapper.AirportMapper;
import com.makaia.flightReservation.model.Airport;
import com.makaia.flightReservation.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AirportService {
    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper;

    @Autowired
    public AirportService(AirportRepository airportRepository, AirportMapper airportMapper) {
        this.airportRepository = airportRepository;
        this.airportMapper = airportMapper;
    }

    public AirportDTO saveAirport(AirportDTO airportDTO) {
        Airport airport = airportMapper.toAirport(airportDTO);
        airportRepository.save(airport);
        return airportMapper.toDto(airport);
    }

    public AirportDTO getAirport(Integer airportId) {
        Optional<Airport> airport = airportRepository.findById(airportId);
        if (airport.isPresent()) {
            return airportMapper.toDto(airport.get());
        }
        throw new RuntimeException();
    }

    public List<AirportDTO> getAirports() {
        return airportRepository.findAll().stream()
                .map(airportMapper::toDto)
                .collect(Collectors.toList());
    }

    public AirportDTO updateAirport(AirportDTO airportDTO, Integer airportId) {
        AirportDTO airportToUpdate = this.getAirport(airportId);
        Airport airport = airportMapper.toAirport(airportToUpdate);
        airport.setAirportName(airportDTO.getAirportName());
        return airportMapper.toDto(airportRepository.save(airport));
    }

    public String deleteAirport(Integer airportId) {
        AirportDTO AirportToDelete = this.getAirport(airportId);
        if (AirportToDelete != null) {
            airportRepository.deleteById(airportId);
            return "Airport successfully eliminated";
        }
        throw new RuntimeException();
    }
}
