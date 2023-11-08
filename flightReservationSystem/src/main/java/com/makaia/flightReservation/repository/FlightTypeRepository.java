package com.makaia.flightReservation.repository;

import com.makaia.flightReservation.model.FlightType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightTypeRepository extends JpaRepository<FlightType, Integer> {
}
