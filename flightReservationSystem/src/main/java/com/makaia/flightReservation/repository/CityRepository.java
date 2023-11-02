package com.makaia.flightReservation.repository;

import com.makaia.flightReservation.model.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface CityRepository extends CrudRepository<City,Integer> {
}
