package com.makaia.flightReservation.airline;

import com.makaia.flightReservation.dto.AirlineDTO;
import com.makaia.flightReservation.exception.NotFoundException;
import com.makaia.flightReservation.mapper.AirlineMapper;
import com.makaia.flightReservation.model.Airline;
import com.makaia.flightReservation.repository.AirlineRepository;
import com.makaia.flightReservation.service.AirlineService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AirlineServiceTest {

    private AirlineService airlineService;
    private AirlineRepository airlineRepository;
    private AirlineMapper airlineMapper;

    @BeforeEach
    void setUp() {
        airlineRepository = mock(AirlineRepository.class);
        airlineMapper = mock(AirlineMapper.class);
        airlineService = new AirlineService(airlineRepository, airlineMapper);
    }

    @Test
    void saveAirline() {
        // Arrange
        AirlineDTO inputAirlineDTO = new AirlineDTO();
        inputAirlineDTO.setAirlineId(1);
        inputAirlineDTO.setAirlineName("Test Airline");

        Airline inputAirline = new Airline();
        inputAirline.setAirlineId(1);
        inputAirline.setAirlineName("Test Airline");

        Airline savedAirline = new Airline();
        savedAirline.setAirlineId(1);
        savedAirline.setAirlineName("Test Airline");
        savedAirline.setFlightSequence(0);

        when(airlineMapper.toAirline(inputAirlineDTO)).thenReturn(inputAirline);
        when(airlineRepository.save(inputAirline)).thenReturn(savedAirline);
        when(airlineMapper.toDto(savedAirline)).thenReturn(inputAirlineDTO);

        // Act
        AirlineDTO result = airlineService.saveAirline(inputAirlineDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getAirlineId());
        assertEquals("Test Airline", result.getAirlineName());
        assertEquals(0, result.getFlightSequence());
    }


    @Test
    void getAirline() {
        // Arrange
        int existingAirlineId = 1;
        Airline existingAirline = new Airline();
        existingAirline.setAirlineId(existingAirlineId);
        existingAirline.setAirlineName("Test Airline");

        AirlineDTO expectedAirlineDTO = new AirlineDTO();
        expectedAirlineDTO.setAirlineId(existingAirlineId);
        expectedAirlineDTO.setAirlineName("Test Airline");

        when(airlineRepository.findById(existingAirlineId)).thenReturn(Optional.of(existingAirline));
        when(airlineMapper.toDto(existingAirline)).thenReturn(expectedAirlineDTO);

        // Act
        AirlineDTO result = airlineService.getAirline(existingAirlineId);

        // Assert
        assertNotNull(result);
        assertEquals(existingAirlineId, result.getAirlineId());
        assertEquals("Test Airline", result.getAirlineName());
    }

    @Test
    void getAirlineWhenNonExistingAirlineId() {
        // Arrange
        int nonExistingAirlineId = 999;

        when(airlineRepository.findById(nonExistingAirlineId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> airlineService.getAirline(nonExistingAirlineId));
    }

    @Test
    void getAllAirlines() {
        // Arrange
        Airline airline1 = new Airline();
        airline1.setAirlineId(1);
        airline1.setAirlineName("Airline 1");

        Airline airline2 = new Airline();
        airline2.setAirlineId(2);
        airline2.setAirlineName("Airline 2");

        List<Airline> airlines = Arrays.asList(airline1, airline2);

        AirlineDTO airlineDTO1 = new AirlineDTO();
        airlineDTO1.setAirlineId(1);
        airlineDTO1.setAirlineName("Airline 1");

        AirlineDTO airlineDTO2 = new AirlineDTO();
        airlineDTO2.setAirlineId(2);
        airlineDTO2.setAirlineName("Airline 2");

        List<AirlineDTO> expectedAirlineDTOs = Arrays.asList(airlineDTO1, airlineDTO2);

        when(airlineRepository.findAll()).thenReturn(airlines);
        when(airlineMapper.toDto(airline1)).thenReturn(airlineDTO1);
        when(airlineMapper.toDto(airline2)).thenReturn(airlineDTO2);

        // Act
        List<AirlineDTO> result = airlineService.getAllAirlines();

        // Assert
        assertNotNull(result);
        assertEquals(expectedAirlineDTOs.size(), result.size());
        assertEquals(expectedAirlineDTOs, result);
    }

    @Test
    void updateAirline() {
        // Arrange
        int existingAirlineId = 1;
        AirlineDTO updatedAirlineDTO = new AirlineDTO();
        updatedAirlineDTO.setAirlineId(existingAirlineId);
        updatedAirlineDTO.setAirlineName("Updated Airline");

        Airline existingAirline = new Airline();
        existingAirline.setAirlineId(existingAirlineId);
        existingAirline.setAirlineName("Old Airline");

        Airline updatedAirline = new Airline();
        updatedAirline.setAirlineId(existingAirlineId);
        updatedAirline.setAirlineName("Updated Airline");

        when(airlineRepository.findById(existingAirlineId)).thenReturn(Optional.of(existingAirline));
        when(airlineRepository.save(any(Airline.class))).thenReturn(updatedAirline);
        when(airlineMapper.toDto(updatedAirline)).thenReturn(updatedAirlineDTO);

        // Act
        AirlineDTO result = airlineService.updateAirline(updatedAirlineDTO, existingAirlineId);

        // Assert
        assertNotNull(result);
        assertEquals(existingAirlineId, result.getAirlineId());
        assertEquals("Updated Airline", result.getAirlineName());
    }

    @Test
    void updateAirlineWhenNonExistingAirlineId() {
        // Arrange
        int nonExistingAirlineId = 999;
        AirlineDTO updatedAirlineDTO = new AirlineDTO();
        updatedAirlineDTO.setAirlineName("Updated Airline");

        when(airlineRepository.findById(nonExistingAirlineId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> airlineService.updateAirline(updatedAirlineDTO, nonExistingAirlineId));
    }

    @Test
    void deleteAirline() {
        // Arrange
        int existingAirlineId = 1;
        Airline existingAirline = new Airline();
        existingAirline.setAirlineId(existingAirlineId);
        existingAirline.setAirlineName("Test Airline");

        when(airlineRepository.existsById(existingAirlineId)).thenReturn(true);

        // Act
        airlineService.deleteAirline(existingAirlineId);

        // Assert
        verify(airlineRepository, times(1)).deleteById(existingAirlineId);
    }

    @Test
    void deleteAirlineWhenNonExistingAirlineId() {
        // Arrange
        int nonExistingAirlineId = 999;

        when(airlineRepository.existsById(nonExistingAirlineId)).thenReturn(false);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> airlineService.deleteAirline(nonExistingAirlineId));
    }

}
