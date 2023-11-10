package com.makaia.flightReservation.repository;

import com.makaia.flightReservation.model.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {
    Page<Flight> findByAirportOriginIdInAndAirportDestinationIdInAndDepartureDateBetween(
            List<Integer> airportOriginId,
            List<Integer> airportDestinationId,
            LocalDateTime startDay,
            LocalDateTime endDay,
            Pageable pageable
    );

    Page<Flight> findByAirportOriginIdInAndDepartureDateBetween(
            List<Integer> airportOriginId,
            LocalDateTime startDay,
            LocalDateTime endDay,
            Pageable pageable
    );

    Page<Flight> findByAirportDestinationIdInAndDepartureDateBetween(
            List<Integer> airportDestinationId,
            LocalDateTime startDay,
            LocalDateTime endDay,
            Pageable pageable
    );

    Page<Flight> findByDepartureDateBetween(
            LocalDateTime startDay,
            LocalDateTime endDay,
            Pageable pageable
    );

    Page<Flight> findByAirportOriginIdInAndAirportDestinationIdIn(
            List<Integer> airportOriginId,
            List<Integer> airportDestinationId,
            Pageable pageable
    );

    Page<Flight> findByAirportOriginIdIn(
            List<Integer> airportOriginId,
            Pageable pageable
    );

    Page<Flight> findByAirportDestinationIdIn(
            List<Integer> airportDestinationId,
            Pageable pageable
    );
}
