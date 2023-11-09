package com.makaia.flightReservation.dto;

import com.makaia.flightReservation.model.City;

import java.util.List;

public class CountryDTO {
    private Integer countryId;
    private String country;
    private List<CityDTO> cities;

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<CityDTO> getCities() {
        return cities;
    }

    public void setCities(List<CityDTO> cities) {
        this.cities = cities;
    }
}
