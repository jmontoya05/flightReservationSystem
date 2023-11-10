package com.makaia.flightReservation.controller;

import com.makaia.flightReservation.dto.CountryDTO;
import com.makaia.flightReservation.service.CountryService;
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

@RestController
@RequestMapping("/countries")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping
    public ResponseEntity<CountryDTO> saveCountry(@RequestBody CountryDTO countryDTO) {
        return new ResponseEntity<>(countryService.saveCountry(countryDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{countryId}")
    public ResponseEntity<CountryDTO> getCountry(@PathVariable Integer countryId) {
        return new ResponseEntity<>(countryService.getCountry(countryId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CountryDTO>> getCountries() {
        return new ResponseEntity<>(countryService.getCountries(), HttpStatus.OK);
    }

    @PutMapping("/{countryId}")
    public ResponseEntity<CountryDTO> updateCountry(@RequestBody CountryDTO countryDTO, @PathVariable Integer countryId) {
        return new ResponseEntity<>(countryService.updateCountry(countryDTO, countryId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{countryId}")
    public ResponseEntity<String> deleteCountry(@PathVariable Integer countryId) {
        return new ResponseEntity<>(countryService.deleteCountry(countryId), HttpStatus.NO_CONTENT);
    }

}
