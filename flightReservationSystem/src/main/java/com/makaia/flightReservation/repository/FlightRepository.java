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
    Page<Flight> findByAirportOriginIdAndAirportDestinationIdAndDepartureDateBetween(
            Integer airportOriginId,
            Integer airportDestinationId,
            LocalDateTime startDay,
            LocalDateTime endDay,
            Pageable pageable
    );

    Page<Flight> findByAirportOriginIdAndDepartureDateBetween(
            Integer airportOriginId,
            LocalDateTime startDay,
            LocalDateTime endDay,
            Pageable pageable
    );

    Page<Flight> findByAirportDestinationIdAndDepartureDateBetween(
            Integer airportDestinationId,
            LocalDateTime startDay,
            LocalDateTime endDay,
            Pageable pageable
    );

    Page<Flight> findByDepartureDateBetween(
            LocalDateTime startDay,
            LocalDateTime endDay,
            Pageable pageable
    );

    Page<Flight> findByAirportOriginIdAndAirportDestinationId(
            Integer airportOriginId,
            Integer airportDestinationId,
            Pageable pageable
    );

    Page<Flight> findByAirportOriginId(
            Integer airportOriginId,
            Pageable pageable
    );

    Page<Flight> findByAirportDestinationId(
            Integer airportDestinationId,
            Pageable pageable
    );
}
