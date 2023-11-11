package com.makaia.flightReservation.controller;

import com.makaia.flightReservation.dto.CityDTO;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {
    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping
    public ResponseEntity<CityDTO> saveCity(@RequestBody CityDTO cityDTO) {
        return new ResponseEntity<>(cityService.saveCity(cityDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{cityId}")
    public ResponseEntity<CityDTO> getCity(@PathVariable Integer cityId) {
        return new ResponseEntity<>(cityService.getCity(cityId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CityDTO>> getAllCities() {
        return new ResponseEntity<>(cityService.getAllCities(), HttpStatus.OK);
    }

    @PutMapping("/{cityId}")
    public ResponseEntity<CityDTO> updateCity(@RequestBody CityDTO cityDTO, @PathVariable Integer cityId) {
        return new ResponseEntity<>(cityService.updateCity(cityDTO, cityId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{cityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCity(@PathVariable Integer cityId) {
        cityService.deleteCity(cityId);
    }
}
