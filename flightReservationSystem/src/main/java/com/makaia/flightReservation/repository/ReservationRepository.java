package com.makaia.flightReservation.repository;

import com.makaia.flightReservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, String> {
    List<Reservation> findByPassengerId(Integer passengerId);
}
