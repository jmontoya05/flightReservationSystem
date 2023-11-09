package com.makaia.flightReservation.service;

import com.makaia.flightReservation.dto.CountryDTO;
import com.makaia.flightReservation.mapper.CountryMapper;
import com.makaia.flightReservation.model.Country;
import com.makaia.flightReservation.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Autowired
    public CountryService(CountryRepository countryRepository, CountryMapper countryMapper) {
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
    }

    public CountryDTO saveCountry(CountryDTO countryDTO) {
        Country country = countryMapper.toCountry(countryDTO);
        countryRepository.save(country);
        return countryMapper.toDto(country);
    }

    public CountryDTO getCountry(Integer countryId) {
        Optional<Country> country = countryRepository.findById(countryId);
        if (country.isPresent()) {
            return countryMapper.toDto(country.get());
        }
        throw new RuntimeException();
    }

    public List<CountryDTO> getCountries() {
        return countryRepository.findAll().stream()
                .map(countryMapper::toDto)
                .collect(Collectors.toList());
    }

    public CountryDTO updateCountry(CountryDTO countryDTO, Integer countryId) {
        CountryDTO countryToUpdate = this.getCountry(countryId);
        Country country = countryMapper.toCountry(countryToUpdate);
        country.setCountry(countryDTO.getCountry());
        return countryMapper.toDto(countryRepository.save(country));

    }

    public String deleteCountry(Integer countryId) {
        CountryDTO countryToDelete = this.getCountry(countryId);
        if (countryToDelete != null) {
            countryRepository.deleteById(countryId);
            return "Country successfully eliminated";
        }
        throw new RuntimeException();
    }

}
