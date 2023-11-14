package com.makaia.flightReservation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "airlines")
public class Airline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "airline_id")
    private Integer airlineId;
    @Column(name = "airline_name", nullable = false, length = 30)
    private String airlineName;
    @Column(name = "flight_sequence", nullable = false)
    private Integer flightSequence;

    public Airline() {
    }

    public Airline(Integer airlineId, String airlineName, Integer flightSequence) {
        this.airlineId = airlineId;
        this.airlineName = airlineName;
        this.flightSequence = flightSequence;
    }

    public Integer getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(Integer airlineId) {
        this.airlineId = airlineId;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public Integer getFlightSequence() {
        return flightSequence;
    }

    public void setFlightSequence(Integer flightSequence) {
        this.flightSequence = flightSequence;
    }
}
