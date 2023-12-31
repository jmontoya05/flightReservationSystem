package com.makaia.flightReservation.service;

import com.makaia.flightReservation.dto.AirportDTO;
import com.makaia.flightReservation.exception.InternalServerErrorException;
import com.makaia.flightReservation.exception.NotFoundException;
import com.makaia.flightReservation.mapper.AirportMapper;
import com.makaia.flightReservation.model.Airport;
import com.makaia.flightReservation.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        try {
            Airport airport = airportMapper.toAirport(airportDTO);
            return airportMapper.toDto(airportRepository.save(airport));
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while saving airport: " + e.getMessage());
        }
    }

    public AirportDTO getAirport(Integer airportId) {
        return airportRepository.findById(airportId)
                .map(airportMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Airport not found with ID: " + airportId));
    }

    public List<AirportDTO> getAllAirports() {
        try {
            return airportRepository.findAll().stream()
                    .map(airportMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while searching for all airports: " + e.getMessage());
        }
    }

    public AirportDTO updateAirport(AirportDTO airportDTO, Integer airportId) {
        Airport airport = airportRepository.findById(airportId)
                .orElseThrow(() -> new NotFoundException("Airport not found with ID: " + airportId));
        try {
            airport.setAirportName(airportDTO.getAirportName());
            return airportMapper.toDto(airportRepository.save(airport));
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while updating airport: " + e.getMessage());
        }
    }

    public void deleteAirport(Integer airportId) {
        if (!airportRepository.existsById(airportId)) {
            throw new NotFoundException("Airport not found with ID: " + airportId);
        }
        try {
            airportRepository.deleteById(airportId);
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while deleting airport: " + e.getMessage());
        }
    }
}
