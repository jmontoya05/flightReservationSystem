package com.makaia.flightReservation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @Column(name = "flight_code", length = 10)
    private String flightCode;
    @Column(name = "airline_id", nullable = false)
    private Integer airlineId;
    @Column(name = "flight_type_id", nullable = false)
    private Integer flightTypeId;
    @Column(name = "airport_origin_id", nullable = false)
    private Integer airportOriginId;
    @Column(name = "airport_destination_id", nullable = false)
    private Integer airportDestinationId;
    @Column(name = "departure_date", nullable = false)
    private LocalDateTime departureDate;
    @Column(name = "arrival_date", nullable = false)
    private LocalDateTime arrivalDate;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;
    @ManyToOne
    @JoinColumn(name = "airline_id", insertable = false, updatable = false)
    private Airline airline;
    @ManyToOne
    @JoinColumn(name = "flight_type_id", insertable = false, updatable = false)
    private FlightType flightType;
    @ManyToOne
    @JoinColumn(name = "airport_origin_id", insertable = false, updatable = false)
    private Airport airportOrigin;
    @ManyToOne
    @JoinColumn(name = "airport_destination_id", insertable = false, updatable = false)
    private Airport airportDestination;

    public Flight() {
    }

    public Flight(String flightCode, Integer airlineId, Integer flightTypeId, Integer airportOriginId, Integer airportDestinationId, LocalDateTime departureDate, LocalDateTime arrivalDate, BigDecimal price, Integer availableSeats, Airline airline, FlightType flightType, Airport airportOrigin, Airport airportDestination) {
        this.flightCode = flightCode;
        this.airlineId = airlineId;
        this.flightTypeId = flightTypeId;
        this.airportOriginId = airportOriginId;
        this.airportDestinationId = airportDestinationId;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.price = price;
        this.availableSeats = availableSeats;
        this.airline = airline;
        this.flightType = flightType;
        this.airportOrigin = airportOrigin;
        this.airportDestination = airportDestination;
    }

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

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public FlightType getFlightType() {
        return flightType;
    }

    public void setFlightType(FlightType flightType) {
        this.flightType = flightType;
    }

    public Airport getAirportOrigin() {
        return airportOrigin;
    }

    public void setAirportOrigin(Airport airportOrigin) {
        this.airportOrigin = airportOrigin;
    }

    public Airport getAirportDestination() {
        return airportDestination;
    }

    public void setAirportDestination(Airport airportDestination) {
        this.airportDestination = airportDestination;
    }
}

