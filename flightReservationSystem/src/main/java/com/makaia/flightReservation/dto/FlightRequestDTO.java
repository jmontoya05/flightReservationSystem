package com.makaia.flightReservation.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class FlightRequestDTO {
    private String flightCode;
    private Integer airlineId;
    private Integer flightTypeId;
    private Integer airportOriginId;
    private Integer airportDestinationId;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private BigDecimal price;
    private Integer availableSeats;

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public Integer getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(Integer airlineId) {
        this.airlineId = airlineId;
    }

    public Integer getFlightTypeId() {
        return flightTypeId;
    }

    public void setFlightTypeId(Integer flightTypeId) {
        this.flightTypeId = flightTypeId;
    }

    public Integer getAirportOriginId() {
        return airportOriginId;
    }

    public void setAirportOriginId(Integer airportOriginId) {
        this.airportOriginId = airportOriginId;
    }

    public Integer getAirportDestinationId() {
        return airportDestinationId;
    }

    public void setAirportDestinationId(Integer airportDestinationId) {
        this.airportDestinationId = airportDestinationId;
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
}
