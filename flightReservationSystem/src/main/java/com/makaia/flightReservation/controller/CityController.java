package com.makaia.flightReservation.controller;

import com.makaia.flightReservation.model.City;
import com.makaia.flightReservation.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cities")
public class CityController {
    private final CityService cityService;
    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping
    public ResponseEntity<City> saveCity(@RequestBody City city){
        return new ResponseEntity<>(cityService.saveCity(city), HttpStatus.CREATED);
    }
    @GetMapping("/{cityId}")
    public ResponseEntity<Optional<City>> getCity(@PathVariable Integer cityId){
        return new ResponseEntity<>(cityService.getCity(cityId), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<City>> getCities(){
        return new ResponseEntity<>(cityService.getCities(), HttpStatus.OK);
    }

    @PutMapping("/{cityId}")
    public ResponseEntity<City> updateCity(@RequestBody City city, @PathVariable Integer cityId){
        return new ResponseEntity<>(cityService.updateCity(city, cityId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{cityId}")
    public ResponseEntity<String> deleteCity(@PathVariable Integer cityId){
        return new ResponseEntity<>(cityService.deleteCity(cityId), HttpStatus.NO_CONTENT);
    }
}
