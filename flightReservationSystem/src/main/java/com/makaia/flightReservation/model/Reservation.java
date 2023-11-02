package com.makaia.flightReservation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Integer reservationId;

    @Column(name = "flight_code", nullable = false, length = 10)
    private String flightCode;
    @Column(name = "passenger_id", nullable = false)
    private Integer passengerId;
    @Column(name = "reservation_date", nullable = false)
    private LocalDateTime reservationDate;
    @ManyToOne
    @JoinColumn(name = "flight_code", insertable = false, updatable = false)
    private Flight flight;
    @ManyToOne
    @JoinColumn(name = "passenger_id", insertable = false, updatable = false)
    private Passenger passenger;

    public Reservation() {
    }

    public Reservation(Integer reservationId, String flightCode, Integer passengerId, LocalDateTime reservationDate, Flight flight, Passenger passenger) {
        this.reservationId = reservationId;
        this.flightCode = flightCode;
        this.passengerId = passengerId;
        this.reservationDate = reservationDate;
        this.flight = flight;
        this.passenger = passenger;
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public Integer getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Integer passengerId) {
        this.passengerId = passengerId;
    }

    public LocalDateTime getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }
}
