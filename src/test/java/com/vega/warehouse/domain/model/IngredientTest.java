package com.vega.warehouse.domain.model;

import com.vega.warehouse.domain.enums.IngredientType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Ingredient - Testes Unitários")
class IngredientTest {

    private Ingredient ingredient;
    private LocalDateTime testDateTime;

    @BeforeEach
    void setUp() {
        testDateTime = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
        ingredient = new Ingredient(1L, "Farinha", IngredientType.SECO, 
                new BigDecimal("100"), "kg", testDateTime);
    }

    @Test
    @DisplayName("Deve criar ingrediente com todos os campos")
    void testIngredientConstructor_WithAllFields() {
        // When
        Ingredient i = new Ingredient(1L, "Farinha", IngredientType.SECO, 
                new BigDecimal("100"), "kg", testDateTime);

        // Then
        assertEquals(1L, i.getId());
        assertEquals("Farinha", i.getName());
        assertEquals(IngredientType.SECO, i.getType());
        assertEquals(new BigDecimal("100"), i.getQuantity());
        assertEquals("kg", i.getUnit());
        assertEquals(testDateTime, i.getCreatedAt());
    }

    @Test
    @DisplayName("Deve criar ingrediente sem ID (novo ingrediente)")
    void testIngredientConstructor_WithoutId() {
        // When
        Ingredient i = new Ingredient("Açúcar", IngredientType.SECO, 
                new BigDecimal("50"), "kg", testDateTime);

        // Then
        assertNull(i.getId());
        assertEquals("Açúcar", i.getName());
        assertEquals(IngredientType.SECO, i.getType());
        assertEquals(new BigDecimal("50"), i.getQuantity());
        assertEquals("kg", i.getUnit());
    }

    @Test
    @DisplayName("Deve retornar nome do ingrediente corretamente")
    void testGetName() {
        // When & Then
        assertEquals("Farinha", ingredient.getName());
    }

    @Test
    @DisplayName("Deve retornar tipo do ingrediente corretamente")
    void testGetType() {
        // When & Then
        assertEquals(IngredientType.SECO, ingredient.getType());
    }

    @Test
    @DisplayName("Deve retornar quantidade corretamente")
    void testGetQuantity() {
        // When & Then
        assertEquals(new BigDecimal("100"), ingredient.getQuantity());
    }

    @Test
    @DisplayName("Deve retornar unidade corretamente")
    void testGetUnit() {
        // When & Then
        assertEquals("kg", ingredient.getUnit());
    }

    @Test
    @DisplayName("Deve retornar data de criação corretamente")
    void testGetCreatedAt() {
        // When & Then
        assertEquals(testDateTime, ingredient.getCreatedAt());
    }

    @Test
    @DisplayName("Deve atualizar nome do ingrediente")
    void testSetName() {
        // When
        ingredient.setName("Farinha de Trigo");

        // Then
        assertEquals("Farinha de Trigo", ingredient.getName());
    }

    @Test
    @DisplayName("Deve atualizar quantidade do ingrediente")
    void testSetQuantity() {
        // When
        ingredient.setQuantity(new BigDecimal("150"));

        // Then
        assertEquals(new BigDecimal("150"), ingredient.getQuantity());
    }

    @Test
    @DisplayName("Deve atualizar quantidade usando updateQuantity")
    void testUpdateQuantity() {
        // When
        ingredient.updateQuantity(new BigDecimal("200"));

        // Then
        assertEquals(new BigDecimal("200"), ingredient.getQuantity());
    }

    @Test
    @DisplayName("Deve criar ingrediente do tipo LIQUIDO")
    void testIngredient_WithLiquidoType() {
        // When
        Ingredient i = new Ingredient(1L, "Óleo", IngredientType.LIQUIDO, 
                new BigDecimal("200"), "L", testDateTime);

        // Then
        assertEquals(IngredientType.LIQUIDO, i.getType());
        assertEquals("Óleo", i.getName());
        assertEquals("L", i.getUnit());
    }

    @Test
    @DisplayName("Deve criar ingrediente do tipo REFRIGERADO")
    void testIngredient_WithRefrigeradoType() {
        // When
        Ingredient i = new Ingredient(1L, "Leite", IngredientType.REFRIGERADO, 
                new BigDecimal("150"), "kg", testDateTime);

        // Then
        assertEquals(IngredientType.REFRIGERADO, i.getType());
        assertEquals("Leite", i.getName());
    }

    @Test
    @DisplayName("Deve criar ingrediente com quantidade decimal")
    void testIngredient_WithDecimalQuantity() {
        // When
        Ingredient i = new Ingredient(1L, "Sal", IngredientType.SECO, 
                new BigDecimal("12.5"), "kg", testDateTime);

        // Then
        assertEquals(new BigDecimal("12.5"), i.getQuantity());
    }

    @Test
    @DisplayName("Deve criar ingrediente com quantidade zero")
    void testIngredient_WithZeroQuantity() {
        // When
        Ingredient i = new Ingredient(1L, "Farinha", IngredientType.SECO, 
                BigDecimal.ZERO, "kg", testDateTime);

        // Then
        assertEquals(BigDecimal.ZERO, i.getQuantity());
    }

    @Test
    @DisplayName("Deve criar ingrediente com diferentes unidades")
    void testIngredient_WithDifferentUnits() {
        // When
        Ingredient kg = new Ingredient(1L, "Farinha", IngredientType.SECO, 
                new BigDecimal("100"), "kg", testDateTime);
        Ingredient l = new Ingredient(2L, "Óleo", IngredientType.LIQUIDO, 
                new BigDecimal("200"), "L", testDateTime);
        Ingredient g = new Ingredient(3L, "Sal", IngredientType.SECO, 
                new BigDecimal("500"), "g", testDateTime);

        // Then
        assertEquals("kg", kg.getUnit());
        assertEquals("L", l.getUnit());
        assertEquals("g", g.getUnit());
    }

    @Test
    @DisplayName("Deve atualizar ID do ingrediente")
    void testSetId() {
        // When
        ingredient.setId(2L);

        // Then
        assertEquals(2L, ingredient.getId());
    }

    @Test
    @DisplayName("Deve retornar toString corretamente")
    void testToString() {
        // When
        String result = ingredient.toString();

        // Then
        assertNotNull(result);
        assertTrue(result.contains("Farinha"));
        assertTrue(result.contains("SECO"));
        assertTrue(result.contains("100"));
    }

    @Test
    @DisplayName("Deve criar ingredientes diferentes com mesmos valores mas IDs diferentes")
    void testIngredient_EqualityByFields() {
        // Given
        Ingredient i1 = new Ingredient(1L, "Farinha", IngredientType.SECO, 
                new BigDecimal("100"), "kg", testDateTime);
        Ingredient i2 = new Ingredient(2L, "Farinha", IngredientType.SECO, 
                new BigDecimal("100"), "kg", testDateTime);

        // When & Then
        assertNotEquals(i1.getId(), i2.getId());
        assertEquals(i1.getName(), i2.getName());
        assertEquals(i1.getType(), i2.getType());
        assertEquals(i1.getQuantity(), i2.getQuantity());
    }
}

