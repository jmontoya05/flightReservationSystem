package com.makaia.flightReservation.service;

import com.makaia.flightReservation.repository.FlightTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightTypeService {
    private final FlightTypeRepository flightTypeRepository;
    @Autowired
    public FlightTypeService(FlightTypeRepository flightTypeRepository) {
        this.flightTypeRepository = flightTypeRepository;
    }


}
