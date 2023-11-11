package com.makaia.flightReservation.service;

import com.makaia.flightReservation.dto.PassengerDTO;
import com.makaia.flightReservation.mapper.PassengerMapper;
import com.makaia.flightReservation.model.Passenger;
import com.makaia.flightReservation.repository.PassengerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.makaia.flightReservation.util.UpdateObjectProperties.getNullPropertyNames;

@Service
public class PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    @Autowired
    public PassengerService(PassengerRepository passengerRepository, PassengerMapper passengerMapper) {
        this.passengerRepository = passengerRepository;
        this.passengerMapper = passengerMapper;
    }

    public PassengerDTO savePassenger(PassengerDTO passengerDTO) {
        Passenger passenger = passengerMapper.toPassenger(passengerDTO);
        passengerRepository.save(passenger);
        return passengerMapper.toDto(passenger);
    }

    public PassengerDTO getPassenger(Integer passengerId) {
        Optional<Passenger> passenger = passengerRepository.findById(passengerId);
        if (passenger.isPresent()) {
            return passengerMapper.toDto(passenger.get());
        }
        throw new RuntimeException();
    }

    public List<PassengerDTO> getPassengers() {
        return passengerRepository.findAll().stream()
                .map(passengerMapper::toDto)
                .collect(Collectors.toList());
    }

    public PassengerDTO updatePassenger(PassengerDTO passengerDTO, Integer passengerId) {
        PassengerDTO passengerToUpdate = this.getPassenger(passengerId);
        Passenger passenger = passengerMapper.toPassenger(passengerToUpdate);

        BeanUtils.copyProperties(passengerDTO, passenger, getNullPropertyNames(passengerDTO));

        return passengerMapper.toDto(passengerRepository.save(passenger));
    }

    public String deletePassenger(Integer passengerId) {
        PassengerDTO PassengerToDelete = this.getPassenger(passengerId);
        if (PassengerToDelete != null) {
            passengerRepository.deleteById(passengerId);
            return "Passenger successfully eliminated";
        }
        throw new RuntimeException();
    }


}
