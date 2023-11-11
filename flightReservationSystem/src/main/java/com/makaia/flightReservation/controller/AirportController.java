package com.makaia.flightReservation.controller;

import com.makaia.flightReservation.dto.AirportDTO;
import com.makaia.flightReservation.service.AirportService;
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
@RequestMapping("/airports")
public class AirportController {
    private final AirportService airportService;

    @Autowired
    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @PostMapping
    public ResponseEntity<AirportDTO> saveAirport(@RequestBody AirportDTO airportDTO) {
        return new ResponseEntity<>(airportService.saveAirport(airportDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{airportId}")
    public ResponseEntity<AirportDTO> getAirport(@PathVariable Integer airportId) {
        return new ResponseEntity<>(airportService.getAirport(airportId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AirportDTO>> getAirports() {
        return new ResponseEntity<>(airportService.getAirports(), HttpStatus.OK);
    }

    @PutMapping("/{airportId}")
    public ResponseEntity<AirportDTO> updateAirport(@RequestBody AirportDTO airportDTO, @PathVariable Integer airportId) {
        return new ResponseEntity<>(airportService.updateAirport(airportDTO, airportId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{airportId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAirport(@PathVariable Integer airportId) {
        airportService.deleteAirport(airportId);
    }
}
