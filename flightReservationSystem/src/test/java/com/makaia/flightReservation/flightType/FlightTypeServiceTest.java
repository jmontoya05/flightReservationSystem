package com.makaia.flightReservation.flightType;

import com.makaia.flightReservation.dto.FlightTypeDTO;
import com.makaia.flightReservation.exception.NotFoundException;
import com.makaia.flightReservation.mapper.FlightTypeMapper;
import com.makaia.flightReservation.model.FlightType;
import com.makaia.flightReservation.repository.FlightTypeRepository;
import com.makaia.flightReservation.service.FlightTypeService;
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
public class FlightTypeServiceTest {

    private FlightTypeService flightTypeService;
    private FlightTypeRepository flightTypeRepository;
    private FlightTypeMapper flightTypeMapper;

    @BeforeEach
    void setUp() {
        flightTypeRepository = mock(FlightTypeRepository.class);
        flightTypeMapper = mock(FlightTypeMapper.class);
        flightTypeService = new FlightTypeService(flightTypeRepository, flightTypeMapper);
    }

    @Test
    void saveFlightType() {
        // Arrange
        FlightTypeDTO inputFlightTypeDTO = new FlightTypeDTO();
        inputFlightTypeDTO.setFlightTypeId(1);
        inputFlightTypeDTO.setFlightType("Test flightType");

        FlightType inputFlightType = new FlightType();
        inputFlightType.setFlightTypeId(1);
        inputFlightType.setFlightType("Test flightType");

        FlightType savedFlightType = new FlightType();
        savedFlightType.setFlightTypeId(1);
        savedFlightType.setFlightType("Test flightType");

        when(flightTypeMapper.toFlightType(inputFlightTypeDTO)).thenReturn(inputFlightType);
        when(flightTypeRepository.save(inputFlightType)).thenReturn(savedFlightType);
        when(flightTypeMapper.toDto(savedFlightType)).thenReturn(inputFlightTypeDTO);

        // Act
        FlightTypeDTO result = flightTypeService.saveFlightType(inputFlightTypeDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getFlightTypeId());
        assertEquals("Test flightType", result.getFlightType());
    }



    @Test
    void getFlightType() {
        // Arrange
        int existingFlightTypeId = 1;
        FlightType existingFlightType = new FlightType();
        existingFlightType.setFlightTypeId(existingFlightTypeId);
        existingFlightType.setFlightType("Test flightType");

        FlightTypeDTO expectedFlightTypeDTO = new FlightTypeDTO();
        expectedFlightTypeDTO.setFlightTypeId(existingFlightTypeId);
        expectedFlightTypeDTO.setFlightType("Test flightType");

        when(flightTypeRepository.findById(existingFlightTypeId)).thenReturn(Optional.of(existingFlightType));
        when(flightTypeMapper.toDto(existingFlightType)).thenReturn(expectedFlightTypeDTO);

        // Act
        FlightTypeDTO result = flightTypeService.getFlightType(existingFlightTypeId);

        // Assert
        assertNotNull(result);
        assertEquals(existingFlightTypeId, result.getFlightTypeId());
        assertEquals("Test flightType", result.getFlightType());
    }

    @Test
    void getFlightTypeWhenNonExistingFlightTypeId() {
        // Arrange
        int nonExistingFlightTypeId = 999;

        when(flightTypeRepository.findById(nonExistingFlightTypeId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> flightTypeService.getFlightType(nonExistingFlightTypeId));
    }

    @Test
    void getAllFlightTypes() {
        // Arrange
        FlightType flightType1 = new FlightType();
        flightType1.setFlightTypeId(1);
        flightType1.setFlightType("flightType 1");

        FlightType flightType2 = new FlightType();
        flightType2.setFlightTypeId(2);
        flightType2.setFlightType("flightType 2");

        List<FlightType> flightTypes = Arrays.asList(flightType1, flightType2);

        FlightTypeDTO flightTypeDTO1 = new FlightTypeDTO();
        flightTypeDTO1.setFlightTypeId(1);
        flightTypeDTO1.setFlightType("flightType 1");

        FlightTypeDTO flightTypeDTO2 = new FlightTypeDTO();
        flightTypeDTO2.setFlightTypeId(2);
        flightTypeDTO2.setFlightType("flightType 2");

        List<FlightTypeDTO> expectedFlightTypeDTOs = Arrays.asList(flightTypeDTO1, flightTypeDTO2);

        when(flightTypeRepository.findAll()).thenReturn(flightTypes);
        when(flightTypeMapper.toDto(flightType1)).thenReturn(flightTypeDTO1);
        when(flightTypeMapper.toDto(flightType2)).thenReturn(flightTypeDTO2);

        // Act
        List<FlightTypeDTO> result = flightTypeService.getAllFlightTypes();

        // Assert
        assertNotNull(result);
        assertEquals(expectedFlightTypeDTOs.size(), result.size());
        assertEquals(expectedFlightTypeDTOs, result);
    }

    @Test
    void updateFlightType() {
        // Arrange
        int existingFlightTypeId = 1;
        FlightTypeDTO updatedFlightTypeDTO = new FlightTypeDTO();
        updatedFlightTypeDTO.setFlightTypeId(existingFlightTypeId);
        updatedFlightTypeDTO.setFlightType("Updated flightType");

        FlightType existingFlightType = new FlightType();
        existingFlightType.setFlightTypeId(existingFlightTypeId);
        existingFlightType.setFlightType("Old flightType");

        FlightType updatedFlightType = new FlightType();
        updatedFlightType.setFlightTypeId(existingFlightTypeId);
        updatedFlightType.setFlightType("Updated flightType");

        when(flightTypeRepository.findById(existingFlightTypeId)).thenReturn(Optional.of(existingFlightType));
        when(flightTypeRepository.save(any(FlightType.class))).thenReturn(updatedFlightType);
        when(flightTypeMapper.toDto(updatedFlightType)).thenReturn(updatedFlightTypeDTO);

        // Act
        FlightTypeDTO result = flightTypeService.updateFlightType(updatedFlightTypeDTO, existingFlightTypeId);

        // Assert
        assertNotNull(result);
        assertEquals(existingFlightTypeId, result.getFlightTypeId());
        assertEquals("Updated flightType", result.getFlightType());
    }

    @Test
    void updateFlightTypeWhenNonExistingFlightTypeId() {
        // Arrange
        int nonExistingFlightTypeId = 999;
        FlightTypeDTO updatedflightTypeDTO = new FlightTypeDTO();
        updatedflightTypeDTO.setFlightType("Updated flightType");

        when(flightTypeRepository.findById(nonExistingFlightTypeId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> flightTypeService.updateFlightType(updatedflightTypeDTO, nonExistingFlightTypeId));
    }

    @Test
    void deleteFlightType() {
        // Arrange
        int existingFlightTypeId = 1;
        FlightType existingflightType = new FlightType();
        existingflightType.setFlightTypeId(existingFlightTypeId);
        existingflightType.setFlightType("Test flightType");

        when(flightTypeRepository.existsById(existingFlightTypeId)).thenReturn(true);

        // Act
        flightTypeService.deleteFlightType(existingFlightTypeId);

        // Assert
        verify(flightTypeRepository, times(1)).deleteById(existingFlightTypeId);
    }

    @Test
    void deleteFlightTypeWhenNonExistingFlightTypeId() {
        // Arrange
        int nonExistingFlightTypeId = 999;

        when(flightTypeRepository.existsById(nonExistingFlightTypeId)).thenReturn(false);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> flightTypeService.deleteFlightType(nonExistingFlightTypeId));
    }
}
