package com.makaia.flightReservation.service;

import com.makaia.flightReservation.model.Airline;
import com.makaia.flightReservation.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AirlineService {
    private final AirlineRepository airlineRepository;
    @Autowired
    public AirlineService(AirlineRepository airlineRepository) {
        this.airlineRepository = airlineRepository;
    }

    public Airline saveAirline(Airline airline){
        return airlineRepository.save(airline);
    }

    public Optional<Airline> getAirline(Integer airlineId){
        return airlineRepository.findById(airlineId);
    }

    public List<Airline> getAirlines(){
        return (List<Airline>) airlineRepository.findAll();
    }

    public Airline updateAirline(Airline airline, Integer airlineId){
        Airline airlineToUpdate = this.getAirline(airlineId).orElse(null);
        if (airlineToUpdate != null){
            airlineToUpdate.setAirlineName(airline.getAirlineName());
            return airlineRepository.save(airlineToUpdate);
        }
        throw new RuntimeException();
    }

    public String deleteAirline(Integer airlineId){
        Airline airlineToDelete = this.getAirline(airlineId).orElse(null);
        if (airlineToDelete != null){
            airlineRepository.deleteById(airlineId);
            return "Airline successfully eliminated";
        }
        throw new RuntimeException();
    }
}
