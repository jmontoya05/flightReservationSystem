package com.makaia.flightReservation.repository;

import com.makaia.flightReservation.model.FlightType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightTypeRepository extends CrudRepository<FlightType, Integer> {
}
