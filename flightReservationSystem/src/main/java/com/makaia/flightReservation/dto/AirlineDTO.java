package com.makaia.flightReservation.dto;


public class AirlineDTO {
    private Integer airlineId;
    private String airlineName;
    private Integer flightSequence;

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
