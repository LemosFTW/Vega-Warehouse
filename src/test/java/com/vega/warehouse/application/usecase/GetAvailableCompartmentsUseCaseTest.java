package com.vega.warehouse.application.usecase;

import com.vega.warehouse.domain.enums.IngredientType;
import com.vega.warehouse.domain.model.Compartment;
import com.vega.warehouse.domain.repository.CompartmentRepositoryPort;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetAvailableCompartmentsUseCase - Testes Unitários")
class GetAvailableCompartmentsUseCaseTest {

    @Mock
    private CompartmentRepositoryPort compartmentRepository;

    @InjectMocks
    private GetAvailableCompartmentsUseCase useCase;

    private Compartment c1;
    private Compartment c2;
    private Compartment c3;

    @BeforeEach
    void setUp() {
        c1 = new Compartment(1L, "C1", IngredientType.SECO, 
                new BigDecimal("600"), BigDecimal.ZERO, null);
        c2 = new Compartment(2L, "C2", IngredientType.LIQUIDO, 
                new BigDecimal("500"), BigDecimal.ZERO, null);
        c3 = new Compartment(3L, "C3", IngredientType.REFRIGERADO, 
                new BigDecimal("400"), BigDecimal.ZERO, null);
    }

    @Test
    @DisplayName("Deve retornar lista de compartimentos disponíveis para SECO")
    void testExecute_ShouldReturnAvailableCompartmentsForSeco() {
        // Given
        IngredientType tipo = IngredientType.SECO;
        BigDecimal quantidade = new BigDecimal("100");
        List<Compartment> expectedCompartments = Arrays.asList(c1);

        when(compartmentRepository.findAvailableForStorage(eq(tipo), eq(quantidade)))
                .thenReturn(expectedCompartments);

        // When
        List<Compartment> result = useCase.execute(tipo, quantidade);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("C1", result.get(0).getCode());
        verify(compartmentRepository).findAvailableForStorage(tipo, quantidade);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há compartimentos disponíveis")
    void testExecute_ShouldReturnEmptyListWhenNoCompartmentsAvailable() {
        // Given
        IngredientType tipo = IngredientType.SECO;
        BigDecimal quantidade = new BigDecimal("1000");

        when(compartmentRepository.findAvailableForStorage(eq(tipo), eq(quantidade)))
                .thenReturn(Collections.emptyList());

        // When
        List<Compartment> result = useCase.execute(tipo, quantidade);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(compartmentRepository).findAvailableForStorage(tipo, quantidade);
    }

    @Test
    @DisplayName("Deve retornar múltiplos compartimentos disponíveis para LIQUIDO")
    void testExecute_ShouldReturnMultipleCompartmentsForLiquido() {
        // Given
        IngredientType tipo = IngredientType.LIQUIDO;
        BigDecimal quantidade = new BigDecimal("100");
        List<Compartment> expectedCompartments = Arrays.asList(c1, c2);

        when(compartmentRepository.findAvailableForStorage(eq(tipo), eq(quantidade)))
                .thenReturn(expectedCompartments);

        // When
        List<Compartment> result = useCase.execute(tipo, quantidade);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("C1", result.get(0).getCode());
        assertEquals("C2", result.get(1).getCode());
        verify(compartmentRepository).findAvailableForStorage(tipo, quantidade);
    }

    @Test
    @DisplayName("Deve retornar compartimentos disponíveis para REFRIGERADO")
    void testExecute_ShouldReturnAvailableCompartmentsForRefrigerado() {
        // Given
        IngredientType tipo = IngredientType.REFRIGERADO;
        BigDecimal quantidade = new BigDecimal("400");
        List<Compartment> expectedCompartments = Arrays.asList(c1, c2, c3);

        when(compartmentRepository.findAvailableForStorage(eq(tipo), eq(quantidade)))
                .thenReturn(expectedCompartments);

        // When
        List<Compartment> result = useCase.execute(tipo, quantidade);

        // Then
        assertNotNull(result);
        assertEquals(3, result.size());
        verify(compartmentRepository).findAvailableForStorage(tipo, quantidade);
    }

    @Test
    @DisplayName("Deve aceitar quantidade zero")
    void testExecute_ShouldAcceptZeroQuantity() {
        // Given
        IngredientType tipo = IngredientType.SECO;
        BigDecimal quantidade = BigDecimal.ZERO;
        List<Compartment> expectedCompartments = Arrays.asList(c1);

        when(compartmentRepository.findAvailableForStorage(eq(tipo), eq(quantidade)))
                .thenReturn(expectedCompartments);

        // When
        List<Compartment> result = useCase.execute(tipo, quantidade);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(compartmentRepository).findAvailableForStorage(tipo, quantidade);
    }
}

