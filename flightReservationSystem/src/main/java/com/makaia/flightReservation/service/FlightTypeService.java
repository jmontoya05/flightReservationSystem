package com.makaia.flightReservation.service;

import com.makaia.flightReservation.model.FlightType;
import com.makaia.flightReservation.repository.FlightTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightTypeService {
    private final FlightTypeRepository flightTypeRepository;
    @Autowired
    public FlightTypeService(FlightTypeRepository flightTypeRepository) {
        this.flightTypeRepository = flightTypeRepository;
    }

    public FlightType saveFlightType(FlightType flightType){
        return flightTypeRepository.save(flightType);
    }

    public Optional<FlightType> getFlightType(Integer flightTypeId){
        return flightTypeRepository.findById(flightTypeId);
    }

    public List<FlightType> getFlightTypes(){
        return (List<FlightType>) flightTypeRepository.findAll();
    }

    public FlightType updateFlightType(FlightType flightType, Integer flightTypeId){
        FlightType flightTypeToUpdate = this.getFlightType(flightTypeId).orElse(null);
        if (flightTypeToUpdate != null){
            flightTypeToUpdate.setFlightType(flightType.getFlightType());
            return flightTypeRepository.save(flightTypeToUpdate);
        }
        throw new RuntimeException();
    }

    public String deleteFlightType(Integer flightTypeId){
        FlightType flightTypeToDelete = this.getFlightType(flightTypeId).orElse(null);
        if (flightTypeToDelete != null){
            flightTypeRepository.deleteById(flightTypeId);
            return "FlightType successfully eliminated";
        }
        throw new RuntimeException();
    }
}
