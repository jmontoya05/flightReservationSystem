package com.makaia.flightReservation.service;

import com.makaia.flightReservation.dto.CityDTO;
import com.makaia.flightReservation.exception.InternalServerErrorException;
import com.makaia.flightReservation.exception.NotFoundException;
import com.makaia.flightReservation.mapper.CityMapper;
import com.makaia.flightReservation.model.City;
import com.makaia.flightReservation.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Autowired
    public CityService(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    public CityDTO saveCity(CityDTO cityDTO) {
        try {
            City city = cityMapper.toCity(cityDTO);
            cityRepository.save(city);
            return cityMapper.toDto(city);
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while saving city: " + e.getMessage());
        }
    }

    public CityDTO getCity(Integer cityId) {
        return cityRepository.findById(cityId)
                .map(cityMapper::toDto)
                .orElseThrow(() -> new NotFoundException("City not found with ID: " + cityId));
    }

    public List<CityDTO> getAllCities() {
        try {
            return cityRepository.findAll().stream()
                    .map(cityMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while searching for all cities: " + e.getMessage());
        }
    }

    public CityDTO updateCity(CityDTO cityDTO, Integer cityId) {
        CityDTO cityToUpdate = this.getCity(cityId);
        City city = cityMapper.toCity(cityToUpdate);
        try {
            city.setCity(cityDTO.getCity());
            return cityMapper.toDto(cityRepository.save(city));
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while updating city: " + e.getMessage());
        }
    }

    public void deleteCity(Integer cityId) {
        if (!cityRepository.existsById(cityId)) {
            throw new NotFoundException("City not found with ID: " + cityId);
        }
        try {
            cityRepository.deleteById(cityId);
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while deleting city: " + e.getMessage());
        }
    }
}
