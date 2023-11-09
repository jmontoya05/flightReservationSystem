package com.makaia.flightReservation.repository;

import com.makaia.flightReservation.model.Airport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Integer> {
}
