package com.vega.warehouse.domain.model;

import com.vega.warehouse.domain.enums.IngredientType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Compartment - Testes Unitários")
class CompartmentTest {

    private Compartment compartment;

    @BeforeEach
    void setUp() {
        compartment = new Compartment(1L, "C1", IngredientType.SECO, 
                new BigDecimal("600"), BigDecimal.ZERO, null);
    }

    @Test
    @DisplayName("Deve calcular espaço disponível corretamente")
    void testGetAvailableSpace() {
        // Given
        Compartment c = new Compartment(1L, "C1", IngredientType.SECO, 
                new BigDecimal("600"), new BigDecimal("200"), null);

        // When
        BigDecimal availableSpace = c.getAvailableSpace();

        // Then
        assertEquals(new BigDecimal("400"), availableSpace);
    }

    @Test
    @DisplayName("Deve retornar true quando tem espaço suficiente")
    void testHasEnoughSpace_ShouldReturnTrue() {
        // Given
        Compartment c = new Compartment(1L, "C1", IngredientType.SECO, 
                new BigDecimal("600"), new BigDecimal("200"), null);
        BigDecimal requiredQuantity = new BigDecimal("300");

        // When
        boolean result = c.hasEnoughSpace(requiredQuantity);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Deve retornar false quando não tem espaço suficiente")
    void testHasEnoughSpace_ShouldReturnFalse() {
        // Given
        Compartment c = new Compartment(1L, "C1", IngredientType.SECO, 
                new BigDecimal("600"), new BigDecimal("200"), null);
        BigDecimal requiredQuantity = new BigDecimal("500");

        // When
        boolean result = c.hasEnoughSpace(requiredQuantity);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Deve permitir armazenar tipo quando compartimento está vazio")
    void testCanStoreType_ShouldReturnTrueWhenEmpty() {
        // Given
        Compartment c = new Compartment(1L, "C1", IngredientType.SECO, 
                new BigDecimal("600"), BigDecimal.ZERO, null);

        // When
        boolean result = c.canStoreType(IngredientType.LIQUIDO);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Deve permitir armazenar mesmo tipo")
    void testCanStoreType_ShouldReturnTrueForSameType() {
        // Given
        Compartment c = new Compartment(1L, "C1", IngredientType.SECO, 
                new BigDecimal("600"), new BigDecimal("100"), null);

        // When
        boolean result = c.canStoreType(IngredientType.SECO);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Não deve permitir armazenar outro tipo se mudou de tipo hoje")
    void testCanStoreType_ShouldReturnFalseWhenChangedTypeToday() {
        // Given
        Compartment c = new Compartment(1L, "C1", IngredientType.SECO, 
                new BigDecimal("600"), new BigDecimal("100"), LocalDate.now());

        // When
        boolean result = c.canStoreType(IngredientType.LIQUIDO);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Deve permitir armazenar outro tipo se mudou de tipo antes de hoje")
    void testCanStoreType_ShouldReturnTrueWhenChangedTypeBeforeToday() {
        // Given
        Compartment c = new Compartment(1L, "C1", IngredientType.SECO, 
                new BigDecimal("600"), new BigDecimal("100"), LocalDate.now().minusDays(1));

        // When
        boolean result = c.canStoreType(IngredientType.LIQUIDO);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Não deve permitir armazenar tipo quando capacidade é insuficiente")
    void testCanStoreType_ShouldReturnFalseWhenCapacityInsufficient() {
        // Given - Compartimento com 400kg tentando armazenar SECO que precisa de 600kg
        Compartment c = new Compartment(1L, "C1", IngredientType.REFRIGERADO, 
                new BigDecimal("400"), BigDecimal.ZERO, null);

        // When
        boolean result = c.canStoreType(IngredientType.SECO);

        // Then
        assertFalse(result);
    }

    @Test
    @DisplayName("Deve permitir armazenar tipo quando capacidade é suficiente")
    void testCanStoreType_ShouldReturnTrueWhenCapacitySufficient() {
        // Given - Compartimento com 600kg pode armazenar LIQUIDO que precisa de 500L
        Compartment c = new Compartment(1L, "C1", IngredientType.SECO, 
                new BigDecimal("600"), BigDecimal.ZERO, null);

        // When
        boolean result = c.canStoreType(IngredientType.LIQUIDO);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("Deve criar compartimento com construtor sem ID")
    void testCompartmentConstructor_WithoutId() {
        // When
        Compartment c = new Compartment("C1", IngredientType.SECO, 
                new BigDecimal("600"), BigDecimal.ZERO, null);

        // Then
        assertNull(c.getId());
        assertEquals("C1", c.getCode());
        assertEquals(IngredientType.SECO, c.getType());
        assertEquals(new BigDecimal("600"), c.getMaxCapacity());
    }

    @Test
    @DisplayName("Deve criar compartimento com construtor completo")
    void testCompartmentConstructor_WithId() {
        // When
        Compartment c = new Compartment(1L, "C1", IngredientType.SECO, 
                new BigDecimal("600"), BigDecimal.ZERO, null);

        // Then
        assertEquals(1L, c.getId());
        assertEquals("C1", c.getCode());
        assertEquals(IngredientType.SECO, c.getType());
    }
}

