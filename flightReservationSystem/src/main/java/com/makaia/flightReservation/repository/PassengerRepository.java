package com.makaia.flightReservation.repository;

import com.makaia.flightReservation.model.Passenger;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends CrudRepository<Passenger, Integer> {
}
