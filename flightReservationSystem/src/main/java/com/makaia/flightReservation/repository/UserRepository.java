package com.makaia.flightReservation.repository;

import com.makaia.flightReservation.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
