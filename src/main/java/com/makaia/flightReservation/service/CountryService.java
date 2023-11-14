package com.makaia.flightReservation.service;

import com.makaia.flightReservation.dto.CountryDTO;
import com.makaia.flightReservation.exception.InternalServerErrorException;
import com.makaia.flightReservation.exception.NotFoundException;
import com.makaia.flightReservation.mapper.CountryMapper;
import com.makaia.flightReservation.model.Country;
import com.makaia.flightReservation.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        try {
            Country country = countryMapper.toCountry(countryDTO);
            return countryMapper.toDto(countryRepository.save(country));
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while saving country: " + e.getMessage());
        }
    }

    public CountryDTO getCountry(Integer countryId) {
        return countryRepository.findById(countryId)
                .map(countryMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Country not found with ID: " + countryId));
    }

    public List<CountryDTO> getAllCountries() {
        try {
            return countryRepository.findAll().stream()
                    .map(countryMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while searching for all countries: " + e.getMessage());
        }
    }

    public CountryDTO updateCountry(CountryDTO countryDTO, Integer countryId) {
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new NotFoundException("Country not found with ID: " + countryId));
        try {
            country.setCountry(countryDTO.getCountry());
            return countryMapper.toDto(countryRepository.save(country));
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while saving country: " + e.getMessage());
        }
    }

    public void deleteCountry(Integer countryId) {
        if (!countryRepository.existsById(countryId)) {
            throw new NotFoundException("Country not found with ID: " + countryId);
        }
        try {
            countryRepository.deleteById(countryId);
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while deleting country: " + e.getMessage());
        }
    }
}
