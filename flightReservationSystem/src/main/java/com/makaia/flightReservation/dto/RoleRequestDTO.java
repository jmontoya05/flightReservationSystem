package com.makaia.flightReservation.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class RoleRequestDTO {
    private String role;

    public RoleRequestDTO() {
    }

    @JsonCreator
    public RoleRequestDTO(@JsonProperty("role") String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
