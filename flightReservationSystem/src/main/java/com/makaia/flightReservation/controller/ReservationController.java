package com.makaia.flightReservation.controller;

import com.makaia.flightReservation.dto.ReservationDTO;
import com.makaia.flightReservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ReservationDTO saveReservation(@RequestBody ReservationDTO reservationDTO) {
        return reservationService.saveReservation(reservationDTO);
    }
}
