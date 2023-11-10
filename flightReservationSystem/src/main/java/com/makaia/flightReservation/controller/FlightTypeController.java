package com.makaia.flightReservation.controller;

import com.makaia.flightReservation.dto.FlightTypeDTO;
import com.makaia.flightReservation.service.FlightTypeService;
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
@RequestMapping("/flight-types")
public class FlightTypeController {
    private final FlightTypeService flightTypeService;

    @Autowired
    public FlightTypeController(FlightTypeService flightTypeService) {
        this.flightTypeService = flightTypeService;
    }

    @PostMapping
    public ResponseEntity<FlightTypeDTO> saveFlightType(@RequestBody FlightTypeDTO flightTypeDTO) {
        return new ResponseEntity<>(flightTypeService.saveFlightType(flightTypeDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{flightTypeId}")
    public ResponseEntity<FlightTypeDTO> getFlightType(@PathVariable Integer flightTypeId) {
        return new ResponseEntity<>(flightTypeService.getFlightType(flightTypeId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<FlightTypeDTO>> getFlightTypes() {
        return new ResponseEntity<>(flightTypeService.getFlightTypes(), HttpStatus.OK);
    }

    @PutMapping("/{flightTypeId}")
    public ResponseEntity<FlightTypeDTO> updateFlightType(@RequestBody FlightTypeDTO flightTypeDTO, @PathVariable Integer flightTypeId) {
        return new ResponseEntity<>(flightTypeService.updateFlightType(flightTypeDTO, flightTypeId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{flightTypeId}")
    public ResponseEntity<String> deleteFlightType(@PathVariable Integer flightTypeId) {
        return new ResponseEntity<>(flightTypeService.deleteFlightType(flightTypeId), HttpStatus.NO_CONTENT);
    }
}
