package com.makaia.flightReservation.service;

import com.makaia.flightReservation.dto.FlightTypeDTO;
import com.makaia.flightReservation.mapper.FlightTypeMapper;
import com.makaia.flightReservation.model.FlightType;
import com.makaia.flightReservation.repository.FlightTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        FlightType flightType = flightTypeMapper.toFlightType(flightTypeDTO);
        flightTypeRepository.save(flightType);
        return flightTypeMapper.toDto(flightType);
    }

    public FlightTypeDTO getFlightType(Integer flightTypeId) {
        Optional<FlightType> flightType = flightTypeRepository.findById(flightTypeId);
        if (flightType.isPresent()) {
            return flightTypeMapper.toDto(flightType.get());
        }
        throw new RuntimeException();
    }

    public List<FlightTypeDTO> getFlightTypes() {
        return flightTypeRepository.findAll().stream()
                .map(flightTypeMapper::toDto)
                .collect(Collectors.toList());
    }

    public FlightTypeDTO updateFlightType(FlightTypeDTO flightTypeDTO, Integer flightTypeId) {
        FlightTypeDTO flightTypeToUpdate = this.getFlightType(flightTypeId);

        FlightType flightType = flightTypeMapper.toFlightType(flightTypeToUpdate);
        flightType.setFlightType(flightTypeDTO.getFlightType());
        return flightTypeMapper.toDto(flightTypeRepository.save(flightType));

    }

    public String deleteFlightType(Integer flightTypeId) {
        FlightTypeDTO flightTypeToDelete = this.getFlightType(flightTypeId);
        if (flightTypeToDelete != null) {
            flightTypeRepository.deleteById(flightTypeId);
            return "FlightType successfully eliminated";
        }
        throw new RuntimeException();
    }
}
