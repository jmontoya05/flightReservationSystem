package com.makaia.flightReservation.service;

import com.makaia.flightReservation.dto.FlightTypeDTO;
import com.makaia.flightReservation.exception.InternalServerErrorException;
import com.makaia.flightReservation.exception.NotFoundException;
import com.makaia.flightReservation.mapper.FlightTypeMapper;
import com.makaia.flightReservation.model.FlightType;
import com.makaia.flightReservation.repository.FlightTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightTypeService {
    private final FlightTypeRepository flightTypeRepository;
    private final FlightTypeMapper flightTypeMapper;

    @Autowired
    public FlightTypeService(FlightTypeRepository flightTypeRepository, FlightTypeMapper flightTypeMapper) {
        this.flightTypeRepository = flightTypeRepository;
        this.flightTypeMapper = flightTypeMapper;
    }

    public FlightTypeDTO saveFlightType(FlightTypeDTO flightTypeDTO) {
        try {
            FlightType flightType = flightTypeMapper.toFlightType(flightTypeDTO);
            return flightTypeMapper.toDto(flightTypeRepository.save(flightType));
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while saving flight type: " + e.getMessage());
        }
    }

    public FlightTypeDTO getFlightType(Integer flightTypeId) {
        return flightTypeRepository.findById(flightTypeId)
                .map(flightTypeMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Flight type not found with ID: " + flightTypeId));
    }

    public List<FlightTypeDTO> getAllFlightTypes() {
        try {
            return flightTypeRepository.findAll().stream()
                    .map(flightTypeMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while searching for all flight types: " + e.getMessage());
        }
    }

    public FlightTypeDTO updateFlightType(FlightTypeDTO flightTypeDTO, Integer flightTypeId) {
        FlightType flightType = flightTypeRepository.findById(flightTypeId)
                .orElseThrow(() -> new NotFoundException("Flight type not found with ID: " + flightTypeId));
        try {
            flightType.setFlightType(flightTypeDTO.getFlightType());
            return flightTypeMapper.toDto(flightTypeRepository.save(flightType));
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while updating flight type: " + e.getMessage());
        }
    }

    public void deleteFlightType(Integer flightTypeId) {
        if (!flightTypeRepository.existsById(flightTypeId)) {
            throw new NotFoundException("Flight type not found with ID: " + flightTypeId);
        }
        try {
            flightTypeRepository.deleteById(flightTypeId);
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while deleting flight type: " + e.getMessage());
        }
    }
}
