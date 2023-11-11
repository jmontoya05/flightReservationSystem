package com.makaia.flightReservation.controller;

import com.makaia.flightReservation.dto.ReservationDTO;
import com.makaia.flightReservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> saveReservation(@RequestBody ReservationDTO reservationDTO) {
        return new ResponseEntity<>(reservationService.saveReservation(reservationDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{reservationCode}")
    public ResponseEntity<ReservationDTO> getReservation(@PathVariable String reservationCode) {
        return new ResponseEntity<>(reservationService.getReservation(reservationCode), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ReservationDTO>> getAllReservations() {
        return new ResponseEntity<>(reservationService.getAllReservations(), HttpStatus.OK);
    }

    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByPassengerId(@PathVariable Integer passengerId) {
        return new ResponseEntity<>(reservationService.getReservationsByPassengerId(passengerId), HttpStatus.OK);
    }

    @DeleteMapping("/{reservationCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable String reservationCode) {
        reservationService.deleteReservation(reservationCode);
    }

}
