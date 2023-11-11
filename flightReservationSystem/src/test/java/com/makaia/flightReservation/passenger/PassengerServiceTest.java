package com.makaia.flightReservation.passenger;

import com.makaia.flightReservation.dto.PassengerDTO;
import com.makaia.flightReservation.exception.NotFoundException;
import com.makaia.flightReservation.mapper.PassengerMapper;
import com.makaia.flightReservation.model.Passenger;
import com.makaia.flightReservation.repository.PassengerRepository;
import com.makaia.flightReservation.service.PassengerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PassengerServiceTest {
    private PassengerService passengerService;
    private PassengerRepository passengerRepository;
    private PassengerMapper passengerMapper;

    @BeforeEach
    void setUp() {
        passengerRepository = mock(PassengerRepository.class);
        passengerMapper = mock(PassengerMapper.class);
        passengerService = new PassengerService(passengerRepository, passengerMapper);
    }

    @Test
    void savePassenger() {
        // Arrange
        PassengerDTO validPassengerDTO = new PassengerDTO();
        validPassengerDTO.setFirstName("Juan");
        validPassengerDTO.setLastName("Valdez");
        validPassengerDTO.setPassport("AB123456");
        validPassengerDTO.setNationality("US");
        validPassengerDTO.setEmail("juan.valdez@example.com");
        validPassengerDTO.setPhoneNumber("123456789");
        validPassengerDTO.setEmergencyContact("Emergency Contact");
        validPassengerDTO.setContactPhoneNumber("987654321");

        Passenger validPassenger = new Passenger();
        validPassenger.setPassengerId(1);
        validPassenger.setFirstName("Juan");
        validPassenger.setLastName("Valdez");
        validPassenger.setPassport("AB123456");
        validPassenger.setNationality("US");
        validPassenger.setEmail("juan.valdez@example.com");
        validPassenger.setPhoneNumber("123456789");
        validPassenger.setEmergencyContact("Emergency Contact");
        validPassenger.setContactPhoneNumber("987654321");

        PassengerDTO expectedPassengerDTO = new PassengerDTO();
        expectedPassengerDTO.setPassengerId(1);
        expectedPassengerDTO.setFirstName("Juan");
        expectedPassengerDTO.setLastName("Valdez");
        expectedPassengerDTO.setPassport("AB123456");
        expectedPassengerDTO.setNationality("US");
        expectedPassengerDTO.setEmail("juan.valdez@example.com");
        expectedPassengerDTO.setPhoneNumber("123456789");
        expectedPassengerDTO.setEmergencyContact("Emergency Contact");
        expectedPassengerDTO.setContactPhoneNumber("987654321");

        when(passengerMapper.toPassenger(validPassengerDTO)).thenReturn(validPassenger);
        when(passengerRepository.save(validPassenger)).thenReturn(validPassenger);
        when(passengerMapper.toDto(validPassenger)).thenReturn(expectedPassengerDTO);

        // Act
        PassengerDTO result = passengerService.savePassenger(validPassengerDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getPassengerId());
        assertEquals("Juan", result.getFirstName());
        assertEquals("Valdez", result.getLastName());
        assertEquals("AB123456", result.getPassport());
        assertEquals("US", result.getNationality());
        assertEquals("juan.valdez@example.com", result.getEmail());
        assertEquals("123456789", result.getPhoneNumber());
        assertEquals("Emergency Contact", result.getEmergencyContact());
        assertEquals("987654321", result.getContactPhoneNumber());
    }

    @Test
    void getPassenger() {
        // Arrange
        int existingPassengerId = 1;

        Passenger existingPassenger = new Passenger();
        existingPassenger.setPassengerId(existingPassengerId);
        existingPassenger.setFirstName("Juan");
        existingPassenger.setLastName("Valdez");

        PassengerDTO expectedPassengerDTO = new PassengerDTO();
        expectedPassengerDTO.setPassengerId(existingPassengerId);
        expectedPassengerDTO.setFirstName("Juan");
        expectedPassengerDTO.setLastName("Valdez");

        when(passengerRepository.findById(existingPassengerId)).thenReturn(Optional.of(existingPassenger));
        when(passengerMapper.toDto(existingPassenger)).thenReturn(expectedPassengerDTO);

        // Act
        PassengerDTO result = passengerService.getPassenger(existingPassengerId);

        // Assert
        assertNotNull(result);
        assertEquals(existingPassengerId, result.getPassengerId());
        assertEquals("Juan", result.getFirstName());
        assertEquals("Valdez", result.getLastName());
    }

    @Test
    void getPassengerWhenNonExistingPassengerId() {
        // Arrange
        int nonExistingPassengerId = 999;

        when(passengerRepository.findById(nonExistingPassengerId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> passengerService.getPassenger(nonExistingPassengerId));
    }

    @Test
    void getAllPassengers() {
        // Arrange
        Passenger passenger1 = new Passenger();
        passenger1.setPassengerId(1);
        passenger1.setFirstName("Juan");
        passenger1.setLastName("Valdez");

        Passenger passenger2 = new Passenger();
        passenger2.setPassengerId(2);
        passenger2.setFirstName("Jane");
        passenger2.setLastName("Valdez");

        List<Passenger> passengers = Arrays.asList(passenger1, passenger2);

        PassengerDTO passengerDTO1 = new PassengerDTO();
        passengerDTO1.setPassengerId(1);
        passengerDTO1.setFirstName("Juan");
        passengerDTO1.setLastName("Valdez");

        PassengerDTO passengerDTO2 = new PassengerDTO();
        passengerDTO2.setPassengerId(2);
        passengerDTO2.setFirstName("Jane");
        passengerDTO2.setLastName("Valdez");

        List<PassengerDTO> expectedPassengerDTOs = Arrays.asList(passengerDTO1, passengerDTO2);

        when(passengerRepository.findAll()).thenReturn(passengers);
        when(passengerMapper.toDto(passenger1)).thenReturn(passengerDTO1);
        when(passengerMapper.toDto(passenger2)).thenReturn(passengerDTO2);

        // Act
        List<PassengerDTO> result = passengerService.getAllPassengers();

        // Assert
        assertNotNull(result);
        assertEquals(expectedPassengerDTOs.size(), result.size());
        assertEquals(expectedPassengerDTOs, result);
    }

    @Test
    void updatePassenger() {
        // Arrange
        int existingPassengerId = 1;

        Passenger existingPassenger = new Passenger();
        existingPassenger.setPassengerId(existingPassengerId);
        existingPassenger.setFirstName("Juan");
        existingPassenger.setLastName("Valdez");

        PassengerDTO updatedPassengerDTO = new PassengerDTO();
        updatedPassengerDTO.setFirstName("UpdatedFirstName");
        updatedPassengerDTO.setLastName("UpdatedLastName");

        Passenger updatedPassenger = new Passenger();
        updatedPassenger.setPassengerId(existingPassengerId);
        updatedPassenger.setFirstName("UpdatedFirstName");
        updatedPassenger.setLastName("UpdatedLastName");

        PassengerDTO expectedPassengerDTO = new PassengerDTO();
        expectedPassengerDTO.setPassengerId(existingPassengerId);
        expectedPassengerDTO.setFirstName("UpdatedFirstName");
        expectedPassengerDTO.setLastName("UpdatedLastName");

        when(passengerRepository.findById(existingPassengerId)).thenReturn(Optional.of(existingPassenger));
        when(passengerRepository.save(any(Passenger.class))).thenReturn(updatedPassenger);
        when(passengerMapper.toDto(updatedPassenger)).thenReturn(expectedPassengerDTO);

        // Act
        PassengerDTO result = passengerService.updatePassenger(updatedPassengerDTO, existingPassengerId);

        // Assert
        assertNotNull(result);
        assertEquals(existingPassengerId, result.getPassengerId());
        assertEquals(expectedPassengerDTO.getFirstName(), result.getFirstName());
        assertEquals(expectedPassengerDTO.getLastName(), result.getLastName());
    }

    @Test
    void updatePassengerWhenNonExistingPassengerId() {
        // Arrange
        int nonExistingPassengerId = 999;

        when(passengerRepository.findById(nonExistingPassengerId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> passengerService.updatePassenger(new PassengerDTO(), nonExistingPassengerId));
    }

    @Test
    void deletePassenger() {
        // Arrange
        int existingPassengerId = 1;

        when(passengerRepository.existsById(existingPassengerId)).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> passengerService.deletePassenger(existingPassengerId));

        verify(passengerRepository, times(1)).deleteById(existingPassengerId);
    }

    @Test
    void deletePassengerWhenNonExistingPassengerId() {
        // Arrange
        int nonExistingPassengerId = 999;

        when(passengerRepository.existsById(nonExistingPassengerId)).thenReturn(false);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> passengerService.deletePassenger(nonExistingPassengerId));
        verify(passengerRepository, never()).deleteById(nonExistingPassengerId);
    }
}
