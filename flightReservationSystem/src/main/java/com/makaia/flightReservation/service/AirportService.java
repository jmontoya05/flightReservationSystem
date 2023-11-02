package com.makaia.flightReservation.service;

import com.makaia.flightReservation.model.Airport;
import com.makaia.flightReservation.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirportService {
    private final AirportRepository airportRepository;
    @Autowired
    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public Airport saveAirport(Airport airport){
        return airportRepository.save(airport);
    }

    public Optional<Airport> getAirport(Integer airportId){
        return airportRepository.findById(airportId);
    }

    public List<Airport> getAirports(){
        return (List<Airport>) airportRepository.findAll();
    }

    public Airport updateAirport(Airport airport, Integer airportId){
        Airport airportToUpdate = this.getAirport(airportId).orElse(null);
        if (airportToUpdate != null){
            airportToUpdate.setAirportName(airport.getAirportName());
            return airportRepository.save(airportToUpdate);
        }
        throw new RuntimeException();
    }

    public String deleteAirport(Integer airportId){
        Airport AirportToDelete = this.getAirport(airportId).orElse(null);
        if (AirportToDelete != null){
            airportRepository.deleteById(airportId);
            return "Airport successfully eliminated";
        }
        throw new RuntimeException();
    }
}
