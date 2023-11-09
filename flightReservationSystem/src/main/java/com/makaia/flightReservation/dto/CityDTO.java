package com.makaia.flightReservation.dto;

import java.util.List;

public class CityDTO {
    private Integer cityId;
    private String city;
    private Integer countryId;
    private List<AirportDTO> airports;

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public List<AirportDTO> getAirports() {
        return airports;
    }

    public void setAirports(List<AirportDTO> airports) {
        this.airports = airports;
    }
}
