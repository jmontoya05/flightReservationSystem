package com.makaia.flightReservation.airport;

import com.makaia.flightReservation.dto.AirportDTO;
import com.makaia.flightReservation.exception.NotFoundException;
import com.makaia.flightReservation.mapper.AirportMapper;
import com.makaia.flightReservation.model.Airport;
import com.makaia.flightReservation.repository.AirportRepository;
import com.makaia.flightReservation.service.AirportService;
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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AirportServiceTest {

    private AirportService airportService;
    private AirportRepository airportRepository;
    private AirportMapper airportMapper;

    @BeforeEach
    void setUp() {
        airportRepository = mock(AirportRepository.class);
        airportMapper = mock(AirportMapper.class);
        airportService = new AirportService(airportRepository, airportMapper);
    }

    @Test
    void saveAirport() {
        // Arrange
        AirportDTO validAirportDTO = new AirportDTO();
        validAirportDTO.setCityId(1);
        validAirportDTO.setAirportName("Test Airport");
        validAirportDTO.setCityId(1);  // Assuming cityId is valid for the test

        Airport validAirport = new Airport();
        validAirport.setAirportId(1);
        validAirport.setAirportName("Test Airport");
        validAirport.setCityId(1);

        AirportDTO expectedAirportDTO = new AirportDTO();
        expectedAirportDTO.setAirportId(1);
        expectedAirportDTO.setAirportName("Test Airport");
        expectedAirportDTO.setCityId(1);

        when(airportMapper.toAirport(validAirportDTO)).thenReturn(validAirport);
        when(airportRepository.save(validAirport)).thenReturn(validAirport);
        when(airportMapper.toDto(validAirport)).thenReturn(expectedAirportDTO);

        // Act
        AirportDTO result = airportService.saveAirport(validAirportDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getAirportId());
        assertEquals("Test Airport", result.getAirportName());
        assertEquals(1, result.getCityId());
    }

    @Test
    void getAirport() {
        // Arrange
        int existingAirportId = 1;
        Airport existingAirport = new Airport();
        existingAirport.setAirportId(existingAirportId);
        existingAirport.setAirportName("Test Airport");
        existingAirport.setCityId(1);

        AirportDTO expectedAirportDTO = new AirportDTO();
        expectedAirportDTO.setAirportId(existingAirportId);
        expectedAirportDTO.setAirportName("Test Airport");
        expectedAirportDTO.setCityId(1);

        when(airportRepository.findById(existingAirportId)).thenReturn(Optional.of(existingAirport));
        when(airportMapper.toDto(existingAirport)).thenReturn(expectedAirportDTO);

        // Act
        AirportDTO result = airportService.getAirport(existingAirportId);

        // Assert
        assertNotNull(result);
        assertEquals(existingAirportId, result.getAirportId());
        assertEquals("Test Airport", result.getAirportName());
        assertEquals(1, result.getCityId());
    }

    @Test
    void getAirportWhenNonExistingAirportId() {
        // Arrange
        int nonExistingAirportId = 999;

        when(airportRepository.findById(nonExistingAirportId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> airportService.getAirport(nonExistingAirportId));
    }

    @Test
    void getAllAirports() {
        // Arrange
        Airport airport1 = new Airport();
        airport1.setAirportId(1);
        airport1.setAirportName("Airport 1");
        airport1.setCityId(1);

        Airport airport2 = new Airport();
        airport2.setAirportId(2);
        airport2.setAirportName("Airport 2");
        airport2.setCityId(1);

        List<Airport> airports = Arrays.asList(airport1, airport2);

        AirportDTO airportDTO1 = new AirportDTO();
        airportDTO1.setAirportId(1);
        airportDTO1.setAirportName("Airport 1");
        airportDTO1.setCityId(1);

        AirportDTO airportDTO2 = new AirportDTO();
        airportDTO2.setAirportId(2);
        airportDTO2.setAirportName("Airport 2");
        airportDTO2.setCityId(1);

        List<AirportDTO> expectedAirportDTOs = Arrays.asList(airportDTO1, airportDTO2);

        when(airportRepository.findAll()).thenReturn(airports);
        when(airportMapper.toDto(airport1)).thenReturn(airportDTO1);
        when(airportMapper.toDto(airport2)).thenReturn(airportDTO2);

        // Act
        List<AirportDTO> result = airportService.getAllAirports();

        // Assert
        assertNotNull(result);
        assertEquals(expectedAirportDTOs.size(), result.size());
        assertEquals(expectedAirportDTOs, result);
    }

    @Test
    void updateAirport() {
        // Arrange
        int existingAirportId = 1;
        AirportDTO updatedAirportDTO = new AirportDTO();
        updatedAirportDTO.setAirportId(existingAirportId);
        updatedAirportDTO.setAirportName("Updated Airport");
        updatedAirportDTO.setCityId(1);

        Airport existingAirport = new Airport();
        existingAirport.setAirportId(existingAirportId);
        existingAirport.setAirportName("Old Airport");
        existingAirport.setCityId(1);

        Airport updatedAirport = new Airport();
        updatedAirport.setAirportId(existingAirportId);
        updatedAirport.setAirportName("Updated Airport");
        updatedAirport.setCityId(1);

        when(airportRepository.findById(existingAirportId)).thenReturn(Optional.of(existingAirport));
        when(airportRepository.save(any(Airport.class))).thenReturn(updatedAirport);
        when(airportMapper.toDto(updatedAirport)).thenReturn(updatedAirportDTO);

        // Act
        AirportDTO result = airportService.updateAirport(updatedAirportDTO, existingAirportId);

        // Assert
        assertNotNull(result);
        assertEquals(existingAirportId, result.getAirportId());
        assertEquals("Updated Airport", result.getAirportName());
        assertEquals(1, result.getCityId());
    }

    @Test
    void updateAirport_NonExistingAirportId_ShouldThrowNotFoundException() {
        // Arrange
        int nonExistingAirportId = 999;
        AirportDTO updatedAirportDTO = new AirportDTO();
        updatedAirportDTO.setAirportId(nonExistingAirportId);
        updatedAirportDTO.setAirportName("Updated Airport");
        updatedAirportDTO.setCityId(1);

        when(airportRepository.findById(nonExistingAirportId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> airportService.updateAirport(updatedAirportDTO, nonExistingAirportId));
    }

    @Test
    void deleteAirport_ExistingAirportId_ShouldDeleteAirport() {
        // Arrange
        int existingAirportId = 1;

        when(airportRepository.existsById(existingAirportId)).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> airportService.deleteAirport(existingAirportId));

        // Assert
        verify(airportRepository, times(1)).deleteById(existingAirportId);
    }

    @Test
    void deleteAirport_NonExistingAirportId_ShouldThrowNotFoundException() {
        // Arrange
        int nonExistingAirportId = 999;

        when(airportRepository.existsById(nonExistingAirportId)).thenReturn(false);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> airportService.deleteAirport(nonExistingAirportId));
    }
}
