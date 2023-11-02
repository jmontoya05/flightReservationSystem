package com.makaia.flightReservation.service;

import com.makaia.flightReservation.model.Country;
import com.makaia.flightReservation.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {
    private final CountryRepository countryRepository;
    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public Country saveCountry(Country country){
        return countryRepository.save(country);
    }

    public Optional<Country> getCountry(Integer countryId){
        return countryRepository.findById(countryId);
    }

    public List<Country> getCountries(){
        return (List<Country>) countryRepository.findAll();
    }

    public Country updateCountry(Country country, Integer countryId){
        Country countryToUpdate = this.getCountry(countryId).orElse(null);
        if (countryToUpdate != null){
            countryToUpdate.setCountry(country.getCountry());
            return countryRepository.save(countryToUpdate);
        }
        throw new RuntimeException();
    }

    public String deleteCountry(Integer countryId){
        Country countryToDelete = this.getCountry(countryId).orElse(null);
        if (countryToDelete != null){
            countryRepository.deleteById(countryId);
            return "Country successfully eliminated";
        }
        throw new RuntimeException();
    }

}
