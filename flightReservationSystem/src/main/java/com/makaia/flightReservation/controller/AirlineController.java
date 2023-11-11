package com.makaia.flightReservation.controller;

import com.makaia.flightReservation.dto.AirlineDTO;
import com.makaia.flightReservation.service.AirlineService;
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
@RequestMapping("/airlines")
public class AirlineController {
    private final AirlineService airlineService;

    @Autowired
    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @PostMapping
    public ResponseEntity<AirlineDTO> saveAirline(@RequestBody AirlineDTO airlineDTO) {
        return new ResponseEntity<>(airlineService.saveAirline(airlineDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{airlineId}")
    public ResponseEntity<AirlineDTO> getAirline(@PathVariable Integer airlineId) {
        return new ResponseEntity<>(airlineService.getAirline(airlineId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AirlineDTO>> getAirlines() {
        return new ResponseEntity<>(airlineService.getAllAirlines(), HttpStatus.OK);
    }

    @PutMapping("/{airlineId}")
    public ResponseEntity<AirlineDTO> updateAirline(@RequestBody AirlineDTO airlineDTO, @PathVariable Integer airlineId) {
        return new ResponseEntity<>(airlineService.updateAirline(airlineDTO, airlineId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{airlineId}")
    public ResponseEntity<String> deleteAirline(@PathVariable Integer airlineId) {
        return new ResponseEntity<>(airlineService.deleteAirline(airlineId), HttpStatus.NO_CONTENT);
    }
}
