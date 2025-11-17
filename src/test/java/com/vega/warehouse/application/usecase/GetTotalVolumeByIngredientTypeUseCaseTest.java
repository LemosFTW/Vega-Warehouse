package com.vega.warehouse.application.usecase;

import com.vega.warehouse.domain.enums.IngredientType;
import com.vega.warehouse.domain.model.IngredientVolumeByType;
import com.vega.warehouse.domain.repository.IngredientRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetTotalVolumeByIngredientTypeUseCase - Testes Unitários")
class GetTotalVolumeByIngredientTypeUseCaseTest {

    @Mock
    private IngredientRepositoryPort ingredientRepository;

    @InjectMocks
    private GetTotalVolumeByIngredientTypeUseCase useCase;

    private IngredientVolumeByType secoVolume;
    private IngredientVolumeByType liquidoVolume;
    private IngredientVolumeByType refrigeradoVolume;

    @BeforeEach
    void setUp() {
        secoVolume = new IngredientVolumeByType(IngredientType.SECO, new BigDecimal("1200"));
        liquidoVolume = new IngredientVolumeByType(IngredientType.LIQUIDO, new BigDecimal("800"));
        refrigeradoVolume = new IngredientVolumeByType(IngredientType.REFRIGERADO, new BigDecimal("600"));
    }

    @Test
    @DisplayName("Deve retornar volume total por tipo de ingrediente")
    void testExecute_ShouldReturnTotalVolumeByType() {
        // Given
        List<IngredientVolumeByType> expectedVolumes = Arrays.asList(secoVolume, liquidoVolume, refrigeradoVolume);
        when(ingredientRepository.getTotalVolumeByType()).thenReturn(expectedVolumes);

        // When
        List<IngredientVolumeByType> result = useCase.execute();

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals(IngredientType.SECO, result.get(0).getType());
        assertEquals(new BigDecimal("1200"), result.get(0).getTotalQuantity());
        assertEquals(IngredientType.LIQUIDO, result.get(1).getType());
        assertEquals(new BigDecimal("800"), result.get(1).getTotalQuantity());
        assertEquals(IngredientType.REFRIGERADO, result.get(2).getType());
        assertEquals(new BigDecimal("600"), result.get(2).getTotalQuantity());
        verify(ingredientRepository).getTotalVolumeByType();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há ingredientes")
    void testExecute_ShouldReturnEmptyList_WhenNoIngredients() {
        // Given
        when(ingredientRepository.getTotalVolumeByType()).thenReturn(Collections.emptyList());

        // When
        List<IngredientVolumeByType> result = useCase.execute();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(ingredientRepository).getTotalVolumeByType();
    }

    @Test
    @DisplayName("Deve retornar apenas um tipo quando há apenas um tipo de ingrediente")
    void testExecute_ShouldReturnSingleType() {
        // Given
        List<IngredientVolumeByType> expectedVolumes = Arrays.asList(secoVolume);
        when(ingredientRepository.getTotalVolumeByType()).thenReturn(expectedVolumes);

        // When
        List<IngredientVolumeByType> result = useCase.execute();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(IngredientType.SECO, result.get(0).getType());
        verify(ingredientRepository).getTotalVolumeByType();
    }

    @Test
    @DisplayName("Deve retornar volume zero quando tipo existe mas não tem quantidade")
    void testExecute_ShouldReturnZeroVolume_WhenTypeExistsButNoQuantity() {
        // Given
        IngredientVolumeByType zeroVolume = new IngredientVolumeByType(IngredientType.SECO, BigDecimal.ZERO);
        List<IngredientVolumeByType> expectedVolumes = Arrays.asList(zeroVolume);
        when(ingredientRepository.getTotalVolumeByType()).thenReturn(expectedVolumes);

        // When
        List<IngredientVolumeByType> result = useCase.execute();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(BigDecimal.ZERO, result.get(0).getTotalQuantity());
        verify(ingredientRepository).getTotalVolumeByType();
    }

    @Test
    @DisplayName("Deve retornar volumes com valores decimais")
    void testExecute_ShouldReturnDecimalVolumes() {
        // Given
        IngredientVolumeByType decimalVolume = new IngredientVolumeByType(IngredientType.SECO, new BigDecimal("1234.567"));
        List<IngredientVolumeByType> expectedVolumes = Arrays.asList(decimalVolume);
        when(ingredientRepository.getTotalVolumeByType()).thenReturn(expectedVolumes);

        // When
        List<IngredientVolumeByType> result = useCase.execute();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(new BigDecimal("1234.567"), result.get(0).getTotalQuantity());
        verify(ingredientRepository).getTotalVolumeByType();
    }
}

