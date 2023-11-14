package com.makaia.flightReservation.country;

import com.makaia.flightReservation.dto.CountryDTO;
import com.makaia.flightReservation.exception.NotFoundException;
import com.makaia.flightReservation.mapper.CountryMapper;
import com.makaia.flightReservation.model.Country;
import com.makaia.flightReservation.repository.CountryRepository;
import com.makaia.flightReservation.service.CountryService;
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
public class CountryServiceTest {

    private CountryService countryService;
    private CountryRepository countryRepository;
    private CountryMapper countryMapper;

    @BeforeEach
    void setUp() {
        countryRepository = mock(CountryRepository.class);
        countryMapper = mock(CountryMapper.class);
        countryService = new CountryService(countryRepository, countryMapper);
    }

    @Test
    void saveCountry() {
        // Arrange
        CountryDTO validCountryDTO = new CountryDTO();
        validCountryDTO.setCountryId(1);
        validCountryDTO.setCountry("Test Country");

        Country validCountry = new Country();
        validCountry.setCountryId(1);
        validCountry.setCountry("Test Country");

        when(countryMapper.toCountry(validCountryDTO)).thenReturn(validCountry);
        when(countryRepository.save(validCountry)).thenReturn(validCountry);
        when(countryMapper.toDto(validCountry)).thenReturn(validCountryDTO);

        // Act
        CountryDTO result = countryService.saveCountry(validCountryDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getCountryId());
        assertEquals("Test Country", result.getCountry());
    }

    @Test
    void getCountry() {
        // Arrange
        int existingCountryId = 1;
        Country existingCountry = new Country();
        existingCountry.setCountryId(existingCountryId);
        existingCountry.setCountry("Test Country");

        CountryDTO expectedCountryDTO = new CountryDTO();
        expectedCountryDTO.setCountryId(existingCountryId);
        expectedCountryDTO.setCountry("Test Country");

        when(countryRepository.findById(existingCountryId)).thenReturn(Optional.of(existingCountry));
        when(countryMapper.toDto(existingCountry)).thenReturn(expectedCountryDTO);

        // Act
        CountryDTO result = countryService.getCountry(existingCountryId);

        // Assert
        assertNotNull(result);
        assertEquals(existingCountryId, result.getCountryId());
        assertEquals("Test Country", result.getCountry());
    }

    @Test
    void getCountryWhenNonExistingCountryId() {
        // Arrange
        int nonExistingCountryId = 999;

        when(countryRepository.findById(nonExistingCountryId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> countryService.getCountry(nonExistingCountryId));
    }

    @Test
    void getAllCountries() {
        // Arrange
        Country country1 = new Country();
        country1.setCountryId(1);
        country1.setCountry("Country 1");

        Country country2 = new Country();
        country2.setCountryId(2);
        country2.setCountry("Country 2");

        List<Country> countries = Arrays.asList(country1, country2);

        CountryDTO countryDTO1 = new CountryDTO();
        countryDTO1.setCountryId(1);
        countryDTO1.setCountry("Country 1");

        CountryDTO countryDTO2 = new CountryDTO();
        countryDTO2.setCountryId(2);
        countryDTO2.setCountry("Country 2");

        List<CountryDTO> expectedCountryDTOs = Arrays.asList(countryDTO1, countryDTO2);

        when(countryRepository.findAll()).thenReturn(countries);
        when(countryMapper.toDto(country1)).thenReturn(countryDTO1);
        when(countryMapper.toDto(country2)).thenReturn(countryDTO2);

        // Act
        List<CountryDTO> result = countryService.getAllCountries();

        // Assert
        assertNotNull(result);
        assertEquals(expectedCountryDTOs.size(), result.size());
        assertEquals(expectedCountryDTOs, result);
    }

    @Test
    void updateCountry() {
        // Arrange
        int existingCountryId = 1;
        CountryDTO updatedCountryDTO = new CountryDTO();
        updatedCountryDTO.setCountryId(existingCountryId);
        updatedCountryDTO.setCountry("Updated Country");

        Country existingCountry = new Country();
        existingCountry.setCountryId(existingCountryId);
        existingCountry.setCountry("Old Country");

        Country updatedCountry = new Country();
        updatedCountry.setCountryId(existingCountryId);
        updatedCountry.setCountry("Updated Country");

        when(countryRepository.findById(existingCountryId)).thenReturn(Optional.of(existingCountry));
        when(countryRepository.save(any(Country.class))).thenReturn(updatedCountry);
        when(countryMapper.toDto(updatedCountry)).thenReturn(updatedCountryDTO);

        // Act
        CountryDTO result = countryService.updateCountry(updatedCountryDTO, existingCountryId);

        // Assert
        assertNotNull(result);
        assertEquals(existingCountryId, result.getCountryId());
        assertEquals("Updated Country", result.getCountry());
    }

    @Test
    void updateCountryWhenNonExistingCountryId() {
        // Arrange
        int nonExistingCountryId = 999;
        CountryDTO updatedCountryDTO = new CountryDTO();
        updatedCountryDTO.setCountryId(nonExistingCountryId);
        updatedCountryDTO.setCountry("Updated Country");

        when(countryRepository.findById(nonExistingCountryId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> countryService.updateCountry(updatedCountryDTO, nonExistingCountryId));
    }

    @Test
    void deleteCountry() {
        // Arrange
        int existingCountryId = 1;

        when(countryRepository.existsById(existingCountryId)).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> countryService.deleteCountry(existingCountryId));

        // Assert
        verify(countryRepository, times(1)).deleteById(existingCountryId);
    }

    @Test
    void deleteCountryWhenNonExistingCountryId() {
        // Arrange
        int nonExistingCountryId = 999;

        when(countryRepository.existsById(nonExistingCountryId)).thenReturn(false);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> countryService.deleteCountry(nonExistingCountryId));
    }
}
