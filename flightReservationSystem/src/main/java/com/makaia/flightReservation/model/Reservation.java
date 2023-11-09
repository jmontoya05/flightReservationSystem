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
    @Column(name = "reservation_code", length = 10)
    private String reservation_code;

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

    public Reservation(String reservation_code, String flightCode, Integer passengerId, LocalDateTime reservationDate, Flight flight, Passenger passenger) {
        this.reservation_code = reservation_code;
        this.flightCode = flightCode;
        this.passengerId = passengerId;
        this.reservationDate = reservationDate;
        this.flight = flight;
        this.passenger = passenger;
    }

    public String getReservation_code() {
        return reservation_code;
    }

    public void setReservation_code(String reservation_code) {
        this.reservation_code = reservation_code;
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
