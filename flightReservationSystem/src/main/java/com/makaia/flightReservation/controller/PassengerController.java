package com.makaia.flightReservation.controller;

import com.makaia.flightReservation.dto.PassengerDTO;
import com.makaia.flightReservation.service.PassengerService;
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
@RequestMapping("/passengers")
public class PassengerController {
    private final PassengerService passengerService;

    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping
    public ResponseEntity<PassengerDTO> savePassenger(@RequestBody PassengerDTO passengerDTO) {
        return new ResponseEntity<>(passengerService.savePassenger(passengerDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{passengerId}")
    public ResponseEntity<PassengerDTO> getPassenger(@PathVariable Integer passengerId) {
        return new ResponseEntity<>(passengerService.getPassenger(passengerId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PassengerDTO>> getPassengers() {
        return new ResponseEntity<>(passengerService.getPassengers(), HttpStatus.OK);
    }

    @PutMapping("/{passengerId}")
    public ResponseEntity<PassengerDTO> updatePassenger(@RequestBody PassengerDTO passengerDTO, @PathVariable Integer passengerId) {
        return new ResponseEntity<>(passengerService.updatePassenger(passengerDTO, passengerId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{passengerId}")
    public ResponseEntity<String> deletePassenger(@PathVariable Integer passengerId) {
        return new ResponseEntity<>(passengerService.deletePassenger(passengerId), HttpStatus.NO_CONTENT);
    }
}
