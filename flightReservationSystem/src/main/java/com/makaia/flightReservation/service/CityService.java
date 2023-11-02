package com.makaia.flightReservation.service;

import com.makaia.flightReservation.model.City;
import com.makaia.flightReservation.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {
    private final CityRepository cityRepository;
    @Autowired
    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public City saveCity(City city){
        return cityRepository.save(city);
    }

    public Optional<City> getCity(Integer cityId){
        return cityRepository.findById(cityId);
    }

    public List<City> getCities(){
        return (List<City>) cityRepository.findAll();
    }

    public City updateCity(City city, Integer cityId){
        City cityToUpdate = this.getCity(cityId).orElse(null);
        if (cityToUpdate != null){
            cityToUpdate.setCity(city.getCity());
            return cityRepository.save(cityToUpdate);
        }
        throw new RuntimeException();
    }

    public String deleteCity(Integer cityId){
        City cityToDelete = this.getCity(cityId).orElse(null);
        if (cityToDelete != null){
            cityRepository.deleteById(cityId);
            return "City successfully eliminated";
        }
        throw new RuntimeException();
    }
}
