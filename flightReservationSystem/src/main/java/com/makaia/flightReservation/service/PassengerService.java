package com.makaia.flightReservation.service;

import com.makaia.flightReservation.model.Passenger;
import com.makaia.flightReservation.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PassengerService {
    private final PassengerRepository passengerRepository;
    @Autowired
    public PassengerService(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }

    public Passenger savePassenger(Passenger passenger){
        return passengerRepository.save(passenger);
    }

    public Optional<Passenger> getPassenger(Integer passengerId){
        return passengerRepository.findById(passengerId);
    }

    public List<Passenger> getPassengers(){
        return (List<Passenger>) passengerRepository.findAll();
    }

    public Passenger updatePassenger(Passenger passenger, Integer passengerId){
        Passenger passengerToUpdate = this.getPassenger(passengerId).orElse(null);
        if (passengerToUpdate != null){
            passengerToUpdate.setFirstName(passenger.getFirstName());
            passengerToUpdate.setLastName(passenger.getLastName());
            passengerToUpdate.setPassport(passenger.getPassport());
            passengerToUpdate.setNationality(passenger.getNationality());
            passengerToUpdate.setEmail(passenger.getEmail());
            passengerToUpdate.setPhoneNumber(passenger.getPhoneNumber());
            passengerToUpdate.setEmergencyContact(passenger.getEmergencyContact());
            passengerToUpdate.setContactPhoneNumber(passenger.getContactPhoneNumber());
            return passengerRepository.save(passengerToUpdate);
        }
        throw new RuntimeException();
    }

    public String deletePassenger(Integer passengerId){
        Passenger PassengerToDelete = this.getPassenger(passengerId).orElse(null);
        if (PassengerToDelete != null){
            passengerRepository.deleteById(passengerId);
            return "Passenger successfully eliminated";
        }
        throw new RuntimeException();
    }
}
