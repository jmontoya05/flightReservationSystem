package com.makaia.flightReservation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "flight_types")
public class FlightType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flight_type_id")
    private Integer flightTypeId;
    @Column(name = "flight_type", nullable = false, length = 20)
    private String flightType;

    public FlightType() {
    }

    public FlightType(Integer flightTypeId, String flightType) {
        this.flightTypeId = flightTypeId;
        this.flightType = flightType;
    }

    public Integer getFlightTypeId() {
        return flightTypeId;
    }

    public void setFlightTypeId(Integer flightTypeId) {
        this.flightTypeId = flightTypeId;
    }

    public String getFlightType() {
        return flightType;
    }

    public void setFlightType(String flightType) {
        this.flightType = flightType;
    }
}
