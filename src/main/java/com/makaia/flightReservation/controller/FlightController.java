package com.makaia.flightReservation.controller;

import com.makaia.flightReservation.dto.FlightCustomPage;
import com.makaia.flightReservation.dto.FlightRequestDTO;
import com.makaia.flightReservation.dto.FlightResponseDTO;
import com.makaia.flightReservation.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @PostMapping
    public ResponseEntity<FlightRequestDTO> saveFlight(@RequestBody FlightRequestDTO flightRequestDTO) {
        return new ResponseEntity<>(flightService.saveFlight(flightRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{flightCode}")
    public ResponseEntity<FlightResponseDTO> getFlight(@PathVariable String flightCode) {
        return new ResponseEntity<>(flightService.getFlight(flightCode), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<FlightCustomPage> getFlights(
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination,
            @RequestParam(name = "departure-date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate departureDate,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return new ResponseEntity<>(flightService.getFlights(origin, destination, departureDate, page, pageSize), HttpStatus.OK);
    }

    @PutMapping("/{flightCode}")
    public ResponseEntity<FlightRequestDTO> updateFlight(@RequestBody FlightRequestDTO flightRequestDTO, @PathVariable String flightCode) {
        return new ResponseEntity<>(flightService.updateFlight(flightRequestDTO, flightCode), HttpStatus.CREATED);
    }

    @DeleteMapping("/{flightCode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFlight(@PathVariable String flightCode) {
        flightService.deleteFlight(flightCode);
    }
}
