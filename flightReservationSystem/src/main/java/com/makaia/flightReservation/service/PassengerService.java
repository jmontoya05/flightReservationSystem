package com.makaia.flightReservation.service;

import com.makaia.flightReservation.dto.PassengerDTO;
import com.makaia.flightReservation.exception.InternalServerErrorException;
import com.makaia.flightReservation.exception.NotFoundException;
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
import java.util.Set;
import java.util.stream.Collectors;

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
        try {
            Passenger passenger = passengerMapper.toPassenger(passengerDTO);
            passengerRepository.save(passenger);
            return passengerMapper.toDto(passenger);
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while saving passenger: " + e.getMessage());
        }
    }

    public PassengerDTO getPassenger(Integer passengerId) {
        return passengerRepository.findById(passengerId)
                .map(passengerMapper::toDto)
                .orElseThrow(() -> new NotFoundException("Passenger not found with ID: " + passengerId));
    }

    public List<PassengerDTO> getAllPassengers() {
        try {
            return passengerRepository.findAll().stream()
                    .map(passengerMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while searching for all passengers: " + e.getMessage());
        }
    }

    public PassengerDTO updatePassenger(PassengerDTO passengerDTO, Integer passengerId) {
        try {
            PassengerDTO passengerToUpdate = this.getPassenger(passengerId);
            Passenger passenger = passengerMapper.toPassenger(passengerToUpdate);
            BeanUtils.copyProperties(passengerDTO, passenger, getNullPropertyNames(passengerDTO));
            return passengerMapper.toDto(passengerRepository.save(passenger));
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while updating the passenger: " + e.getMessage());
        }
    }

    public void deletePassenger(Integer passengerId) {
        if (!passengerRepository.existsById(passengerId)) {
            throw new NotFoundException("Passenger not found with ID: " + passengerId);
        }
        try {
            passengerRepository.deleteById(passengerId);
        } catch (Exception e) {
            throw new InternalServerErrorException("Internal Server Error occurred while deleting passenger: " + e.getMessage());
        }
    }

    private String[] getNullPropertyNames(Object object) {
        BeanWrapper src = new BeanWrapperImpl(object);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
