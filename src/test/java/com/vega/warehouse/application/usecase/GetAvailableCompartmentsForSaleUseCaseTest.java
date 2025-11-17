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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetAvailableCompartmentsForSaleUseCase - Testes Unitários")
class GetAvailableCompartmentsForSaleUseCaseTest {

    @Mock
    private CompartmentRepositoryPort compartmentRepository;

    @InjectMocks
    private GetAvailableCompartmentsForSaleUseCase useCase;

    private Compartment c1;
    private Compartment c2;
    private Compartment c3;
    private Compartment c4;

    @BeforeEach
    void setUp() {
        // C1: 600kg SECO com 200kg ocupados (400kg disponíveis)
        c1 = new Compartment(1L, "C1", IngredientType.SECO, 
                new BigDecimal("600"), new BigDecimal("200"), null);
        
        // C2: 500L LIQUIDO com 100L ocupados (400L disponíveis)
        c2 = new Compartment(2L, "C2", IngredientType.LIQUIDO, 
                new BigDecimal("500"), new BigDecimal("100"), null);
        
        // C3: 400kg REFRIGERADO com 150kg ocupados (250kg disponíveis)
        c3 = new Compartment(3L, "C3", IngredientType.REFRIGERADO, 
                new BigDecimal("400"), new BigDecimal("150"), null);
        
        // C4: 600kg SECO vazio (não deve aparecer para venda)
        c4 = new Compartment(4L, "C4", IngredientType.SECO, 
                new BigDecimal("600"), BigDecimal.ZERO, null);
    }

    @Test
    @DisplayName("Deve retornar compartimentos disponíveis para venda do tipo SECO")
    void testExecute_ShouldReturnCompartmentsForSeco() {
        // Given
        IngredientType tipo = IngredientType.SECO;
        List<Compartment> expectedCompartments = Arrays.asList(c1);

        when(compartmentRepository.findAvailableForSaleByType(eq(tipo)))
                .thenReturn(expectedCompartments);

        // When
        List<Compartment> result = useCase.execute(tipo);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("C1", result.get(0).getCode());
        assertEquals(IngredientType.SECO, result.get(0).getType());
        assertTrue(result.get(0).getCurrentQuantity().compareTo(BigDecimal.ZERO) > 0);
        verify(compartmentRepository).findAvailableForSaleByType(tipo);
    }

    @Test
    @DisplayName("Deve retornar compartimentos disponíveis para venda do tipo LIQUIDO")
    void testExecute_ShouldReturnCompartmentsForLiquido() {
        // Given
        IngredientType tipo = IngredientType.LIQUIDO;
        List<Compartment> expectedCompartments = Arrays.asList(c2);

        when(compartmentRepository.findAvailableForSaleByType(eq(tipo)))
                .thenReturn(expectedCompartments);

        // When
        List<Compartment> result = useCase.execute(tipo);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("C2", result.get(0).getCode());
        assertEquals(IngredientType.LIQUIDO, result.get(0).getType());
        assertTrue(result.get(0).getCurrentQuantity().compareTo(BigDecimal.ZERO) > 0);
        verify(compartmentRepository).findAvailableForSaleByType(tipo);
    }

    @Test
    @DisplayName("Deve retornar compartimentos disponíveis para venda do tipo REFRIGERADO")
    void testExecute_ShouldReturnCompartmentsForRefrigerado() {
        // Given
        IngredientType tipo = IngredientType.REFRIGERADO;
        List<Compartment> expectedCompartments = Arrays.asList(c3);

        when(compartmentRepository.findAvailableForSaleByType(eq(tipo)))
                .thenReturn(expectedCompartments);

        // When
        List<Compartment> result = useCase.execute(tipo);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("C3", result.get(0).getCode());
        assertEquals(IngredientType.REFRIGERADO, result.get(0).getType());
        assertTrue(result.get(0).getCurrentQuantity().compareTo(BigDecimal.ZERO) > 0);
        verify(compartmentRepository).findAvailableForSaleByType(tipo);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há compartimentos disponíveis para venda")
    void testExecute_ShouldReturnEmptyListWhenNoCompartmentsAvailable() {
        // Given
        IngredientType tipo = IngredientType.SECO;

        when(compartmentRepository.findAvailableForSaleByType(eq(tipo)))
                .thenReturn(Collections.emptyList());

        // When
        List<Compartment> result = useCase.execute(tipo);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(compartmentRepository).findAvailableForSaleByType(tipo);
    }

    @Test
    @DisplayName("Deve retornar múltiplos compartimentos quando há vários disponíveis")
    void testExecute_ShouldReturnMultipleCompartments() {
        // Given
        Compartment c5 = new Compartment(5L, "C5", IngredientType.SECO, 
                new BigDecimal("600"), new BigDecimal("300"), null);
        
        IngredientType tipo = IngredientType.SECO;
        List<Compartment> expectedCompartments = Arrays.asList(c1, c5);

        when(compartmentRepository.findAvailableForSaleByType(eq(tipo)))
                .thenReturn(expectedCompartments);

        // When
        List<Compartment> result = useCase.execute(tipo);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("C1", result.get(0).getCode());
        assertEquals("C5", result.get(1).getCode());
        // Verificar que ambos têm quantidade > 0
        assertTrue(result.get(0).getCurrentQuantity().compareTo(BigDecimal.ZERO) > 0);
        assertTrue(result.get(1).getCurrentQuantity().compareTo(BigDecimal.ZERO) > 0);
        verify(compartmentRepository).findAvailableForSaleByType(tipo);
    }

    @Test
    @DisplayName("Deve retornar apenas compartimentos com volume maior que zero")
    void testExecute_ShouldOnlyReturnCompartmentsWithQuantityGreaterThanZero() {
        // Given
        IngredientType tipo = IngredientType.SECO;
        // C1 tem 200kg, C4 está vazio - apenas C1 deve retornar
        List<Compartment> expectedCompartments = Arrays.asList(c1);

        when(compartmentRepository.findAvailableForSaleByType(eq(tipo)))
                .thenReturn(expectedCompartments);

        // When
        List<Compartment> result = useCase.execute(tipo);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        // Verificar que o compartimento retornado tem quantidade > 0
        assertTrue(result.get(0).getCurrentQuantity().compareTo(BigDecimal.ZERO) > 0);
        // Verificar que C4 (vazio) não está na lista
        assertFalse(result.stream().anyMatch(c -> c.getCode().equals("C4")));
        verify(compartmentRepository).findAvailableForSaleByType(tipo);
    }
}

