package com.makaia.flightReservation.service;

import com.makaia.flightReservation.dto.CityDTO;
import com.makaia.flightReservation.mapper.CityMapper;
import com.makaia.flightReservation.model.City;
import com.makaia.flightReservation.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public CityDTO saveCity(CityDTO cityDTO){
        City city = cityMapper.toCity(cityDTO);
        cityRepository.save(city);
        return cityMapper.toDto(city);
    }

    public CityDTO getCity(Integer cityId){
        Optional<City> city = cityRepository.findById(cityId);
        if (city.isPresent()){
            return cityMapper.toDto(city.get());
        }
        throw new RuntimeException();
    }

    public List<CityDTO> getCities(){
        return cityRepository.findAll().stream()
                .map(cityMapper::toDto)
                .collect(Collectors.toList());
    }

    public CityDTO updateCity(CityDTO cityDTO, Integer cityId){
        CityDTO cityToUpdate = this.getCity(cityId);
        City city = cityMapper.toCity(cityToUpdate);
        city.setCity(cityDTO.getCity());
        return cityMapper.toDto(cityRepository.save(city));
    }

    public String deleteCity(Integer cityId){
        CityDTO cityToDelete = this.getCity(cityId);
        if (cityToDelete != null){
            cityRepository.deleteById(cityId);
            return "City successfully eliminated";
        }
        throw new RuntimeException();
    }
}
