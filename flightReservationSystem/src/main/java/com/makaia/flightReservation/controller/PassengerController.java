package com.makaia.flightReservation.controller;

import com.makaia.flightReservation.model.Passenger;
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
import java.util.Optional;

@RestController
@RequestMapping("/passengers")
public class PassengerController {
    private final PassengerService passengerService;
    @Autowired
    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @PostMapping
    public ResponseEntity<Passenger> savePassenger(@RequestBody Passenger passenger){
        return new ResponseEntity<>(passengerService.savePassenger(passenger), HttpStatus.CREATED);
    }
    @GetMapping("/{passengerId}")
    public ResponseEntity<Optional<Passenger>> getPassenger(@PathVariable Integer passengerId){
        return new ResponseEntity<>(passengerService.getPassenger(passengerId), HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<Passenger>> getPassengers(){
        return new ResponseEntity<>(passengerService.getPassengers(), HttpStatus.OK);
    }

    @PutMapping("/{passengerId}")
    public ResponseEntity<Passenger> updatePassenger(@RequestBody Passenger passenger, @PathVariable Integer passengerId){
        return new ResponseEntity<>(passengerService.updatePassenger(passenger, passengerId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{passengerId}")
    public ResponseEntity<String> deletePassenger(@PathVariable Integer passengerId){
        return new ResponseEntity<>(passengerService.deletePassenger(passengerId), HttpStatus.NO_CONTENT);
    }
}
