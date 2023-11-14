package com.makaia.flightReservation.city;

import com.makaia.flightReservation.dto.CityDTO;
import com.makaia.flightReservation.exception.NotFoundException;
import com.makaia.flightReservation.mapper.CityMapper;
import com.makaia.flightReservation.model.City;
import com.makaia.flightReservation.repository.CityRepository;
import com.makaia.flightReservation.service.CityService;
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
public class CityServiceTest {
    private CityService cityService;
    private CityRepository cityRepository;
    private CityMapper cityMapper;

    @BeforeEach
    void setUp() {
        cityRepository = mock(CityRepository.class);
        cityMapper = mock(CityMapper.class);
        cityService = new CityService(cityRepository, cityMapper);
    }

    @Test
    void saveCity() {
        // Arrange
        CityDTO validCityDTO = new CityDTO();
        validCityDTO.setCityId(1);
        validCityDTO.setCity("Test City");
        validCityDTO.setCountryId(1);

        City validCity = new City();
        validCity.setCityId(1);
        validCity.setCity("Test City");
        validCity.setCountryId(1);

        when(cityMapper.toCity(validCityDTO)).thenReturn(validCity);
        when(cityRepository.save(validCity)).thenReturn(validCity);
        when(cityMapper.toDto(validCity)).thenReturn(validCityDTO);

        // Act
        CityDTO result = cityService.saveCity(validCityDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getCityId());
        assertEquals("Test City", result.getCity());
        assertEquals(1, result.getCountryId());
    }

    @Test
    void getCity() {
        // Arrange
        int existingCityId = 1;
        City existingCity = new City();
        existingCity.setCityId(existingCityId);
        existingCity.setCity("Test City");
        existingCity.setCountryId(1);

        CityDTO expectedCityDTO = new CityDTO();
        expectedCityDTO.setCityId(existingCityId);
        expectedCityDTO.setCity("Test City");
        expectedCityDTO.setCountryId(1);

        when(cityRepository.findById(existingCityId)).thenReturn(Optional.of(existingCity));
        when(cityMapper.toDto(existingCity)).thenReturn(expectedCityDTO);

        // Act
        CityDTO result = cityService.getCity(existingCityId);

        // Assert
        assertNotNull(result);
        assertEquals(existingCityId, result.getCityId());
        assertEquals("Test City", result.getCity());
        assertEquals(1, result.getCountryId());
    }

    @Test
    void getCityWhenNonExistingCityId() {
        // Arrange
        int nonExistingCityId = 999;

        when(cityRepository.findById(nonExistingCityId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> cityService.getCity(nonExistingCityId));
    }

    @Test
    void getAllCities() {
        // Arrange
        City city1 = new City();
        city1.setCityId(1);
        city1.setCity("City 1");
        city1.setCountryId(1);

        City city2 = new City();
        city2.setCityId(2);
        city2.setCity("City 2");
        city2.setCountryId(1);

        List<City> cities = Arrays.asList(city1, city2);

        CityDTO cityDTO1 = new CityDTO();
        cityDTO1.setCityId(1);
        cityDTO1.setCity("City 1");
        cityDTO1.setCountryId(1);

        CityDTO cityDTO2 = new CityDTO();
        cityDTO2.setCityId(2);
        cityDTO2.setCity("City 2");
        cityDTO2.setCountryId(1);

        List<CityDTO> expectedCityDTOs = Arrays.asList(cityDTO1, cityDTO2);

        when(cityRepository.findAll()).thenReturn(cities);
        when(cityMapper.toDto(city1)).thenReturn(cityDTO1);
        when(cityMapper.toDto(city2)).thenReturn(cityDTO2);

        // Act
        List<CityDTO> result = cityService.getAllCities();

        // Assert
        assertNotNull(result);
        assertEquals(expectedCityDTOs.size(), result.size());
        assertEquals(expectedCityDTOs, result);
    }

    @Test
    void updateCity() {
        // Arrange
        int existingCityId = 1;
        CityDTO updatedCityDTO = new CityDTO();
        updatedCityDTO.setCityId(existingCityId);
        updatedCityDTO.setCity("Updated City");
        updatedCityDTO.setCountryId(1);

        City existingCity = new City();
        existingCity.setCityId(existingCityId);
        existingCity.setCity("Old City");
        existingCity.setCountryId(1);

        City updatedCity = new City();
        updatedCity.setCityId(existingCityId);
        updatedCity.setCity("Updated City");
        updatedCity.setCountryId(1);

        when(cityRepository.findById(existingCityId)).thenReturn(Optional.of(existingCity));
        when(cityRepository.save(any(City.class))).thenReturn(updatedCity);
        when(cityMapper.toDto(updatedCity)).thenReturn(updatedCityDTO);

        // Act
        CityDTO result = cityService.updateCity(updatedCityDTO, existingCityId);

        // Assert
        assertNotNull(result);
        assertEquals(existingCityId, result.getCityId());
        assertEquals("Updated City", result.getCity());
        assertEquals(1, result.getCountryId());
    }

    @Test
    void updateCityWhenNonExistingCityId() {
        // Arrange
        int nonExistingCityId = 999;
        CityDTO updatedCityDTO = new CityDTO();
        updatedCityDTO.setCityId(nonExistingCityId);
        updatedCityDTO.setCity("Updated City");
        updatedCityDTO.setCountryId(1);

        when(cityRepository.findById(nonExistingCityId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> cityService.updateCity(updatedCityDTO, nonExistingCityId));
    }

    @Test
    void deleteCity() {
        // Arrange
        int existingCityId = 1;

        when(cityRepository.existsById(existingCityId)).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> cityService.deleteCity(existingCityId));

        // Assert
        verify(cityRepository, times(1)).deleteById(existingCityId);
    }

    @Test
    void deleteCityWhenNonExistingCityId() {
        // Arrange
        int nonExistingCityId = 999;

        when(cityRepository.existsById(nonExistingCityId)).thenReturn(false);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> cityService.deleteCity(nonExistingCityId));
    }
}
