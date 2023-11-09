package com.makaia.flightReservation.dto;

import com.makaia.flightReservation.model.Reservation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class FlightResponseDTO {
    private String flightCode;
    private AirlineDTO airline;
    private FlightTypeDTO flightType;
    private AirportDTO airportOrigin;
    private AirportDTO airportDestination;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private BigDecimal price;
    private Integer availableSeats;
    private List<Reservation> reservations;

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public AirlineDTO getAirline() {
        return airline;
    }

    public void setAirline(AirlineDTO airline) {
        this.airline = airline;
    }

    public FlightTypeDTO getFlightType() {
        return flightType;
    }

    public void setFlightType(FlightTypeDTO flightType) {
        this.flightType = flightType;
    }

    public AirportDTO getAirportOrigin() {
        return airportOrigin;
    }

    public void setAirportOrigin(AirportDTO airportOrigin) {
        this.airportOrigin = airportOrigin;
    }

    public AirportDTO getAirportDestination() {
        return airportDestination;
    }

    public void setAirportDestination(AirportDTO airportDestination) {
        this.airportDestination = airportDestination;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
