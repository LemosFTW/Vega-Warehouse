package com.vega.warehouse.domain.model;

import com.vega.warehouse.domain.enums.IngredientType;
import com.vega.warehouse.domain.enums.MovementType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Movement - Testes Unitários")
class MovementTest {

    private Movement movement;
    private LocalDateTime testDateTime;

    @BeforeEach
    void setUp() {
        testDateTime = LocalDateTime.of(2024, 1, 15, 10, 30, 0);
        movement = new Movement(1L, MovementType.ENTRADA, "Farinha", "C1", 
                new BigDecimal("100"), IngredientType.SECO, "João Silva", testDateTime);
    }

    @Test
    @DisplayName("Deve criar movimento com todos os campos")
    void testMovementConstructor_WithAllFields() {
        // When
        Movement m = new Movement(1L, MovementType.ENTRADA, "Farinha", "C1", 
                new BigDecimal("100"), IngredientType.SECO, "João Silva", testDateTime);

        // Then
        assertEquals(1L, m.getId());
        assertEquals(MovementType.ENTRADA, m.getType());
        assertEquals("Farinha", m.getIngredientName());
        assertEquals("C1", m.getCompartmentCode());
        assertEquals(new BigDecimal("100"), m.getQuantity());
        assertEquals(IngredientType.SECO, m.getIngredientType());
        assertEquals("João Silva", m.getResponsible());
        assertEquals(testDateTime, m.getMovementDateTime());
    }

    @Test
    @DisplayName("Deve criar movimento sem ID (novo movimento)")
    void testMovementConstructor_WithoutId() {
        // When
        Movement m = new Movement(null, MovementType.SAIDA, "Açúcar", "C2", 
                new BigDecimal("50"), IngredientType.SECO, "Maria Santos", testDateTime);

        // Then
        assertNull(m.getId());
        assertEquals(MovementType.SAIDA, m.getType());
        assertEquals("Açúcar", m.getIngredientName());
        assertEquals("C2", m.getCompartmentCode());
        assertEquals(new BigDecimal("50"), m.getQuantity());
        assertEquals(IngredientType.SECO, m.getIngredientType());
        assertEquals("Maria Santos", m.getResponsible());
    }

    @Test
    @DisplayName("Deve retornar tipo de movimento corretamente")
    void testGetType() {
        // Given
        Movement entrada = new Movement(1L, MovementType.ENTRADA, "Farinha", "C1", 
                new BigDecimal("100"), IngredientType.SECO, "João", testDateTime);
        Movement saida = new Movement(2L, MovementType.SAIDA, "Açúcar", "C2", 
                new BigDecimal("50"), IngredientType.SECO, "Maria", testDateTime);

        // When & Then
        assertEquals(MovementType.ENTRADA, entrada.getType());
        assertEquals(MovementType.SAIDA, saida.getType());
    }

    @Test
    @DisplayName("Deve retornar nome do ingrediente corretamente")
    void testGetIngredientName() {
        // When & Then
        assertEquals("Farinha", movement.getIngredientName());
    }

    @Test
    @DisplayName("Deve retornar código do compartimento corretamente")
    void testGetCompartmentCode() {
        // When & Then
        assertEquals("C1", movement.getCompartmentCode());
    }

    @Test
    @DisplayName("Deve retornar quantidade corretamente")
    void testGetQuantity() {
        // When & Then
        assertEquals(new BigDecimal("100"), movement.getQuantity());
    }

    @Test
    @DisplayName("Deve retornar tipo do ingrediente corretamente")
    void testGetIngredientType() {
        // When & Then
        assertEquals(IngredientType.SECO, movement.getIngredientType());
    }

    @Test
    @DisplayName("Deve retornar responsável corretamente")
    void testGetResponsible() {
        // When & Then
        assertEquals("João Silva", movement.getResponsible());
    }

    @Test
    @DisplayName("Deve retornar data e hora do movimento corretamente")
    void testGetMovementDateTime() {
        // When & Then
        assertEquals(testDateTime, movement.getMovementDateTime());
    }

    @Test
    @DisplayName("Deve criar movimento com tipo LIQUIDO")
    void testMovement_WithLiquidoType() {
        // When
        Movement m = new Movement(1L, MovementType.ENTRADA, "Óleo", "C2", 
                new BigDecimal("200"), IngredientType.LIQUIDO, "Pedro", testDateTime);

        // Then
        assertEquals(IngredientType.LIQUIDO, m.getIngredientType());
        assertEquals("Óleo", m.getIngredientName());
    }

    @Test
    @DisplayName("Deve criar movimento com tipo REFRIGERADO")
    void testMovement_WithRefrigeradoType() {
        // When
        Movement m = new Movement(1L, MovementType.ENTRADA, "Leite", "C3", 
                new BigDecimal("150"), IngredientType.REFRIGERADO, "Ana", testDateTime);

        // Then
        assertEquals(IngredientType.REFRIGERADO, m.getIngredientType());
        assertEquals("Leite", m.getIngredientName());
    }

    @Test
    @DisplayName("Deve criar movimento com quantidade decimal")
    void testMovement_WithDecimalQuantity() {
        // When
        Movement m = new Movement(1L, MovementType.ENTRADA, "Sal", "C1", 
                new BigDecimal("12.5"), IngredientType.SECO, "Carlos", testDateTime);

        // Then
        assertEquals(new BigDecimal("12.5"), m.getQuantity());
    }

    @Test
    @DisplayName("Deve criar movimento com quantidade zero (caso limite)")
    void testMovement_WithZeroQuantity() {
        // When
        Movement m = new Movement(1L, MovementType.SAIDA, "Farinha", "C1", 
                BigDecimal.ZERO, IngredientType.SECO, "João", testDateTime);

        // Then
        assertEquals(BigDecimal.ZERO, m.getQuantity());
    }

    @Test
    @DisplayName("Deve criar movimento com data e hora atual")
    void testMovement_WithCurrentDateTime() {
        // Given
        LocalDateTime now = LocalDateTime.now();

        // When
        Movement m = new Movement(1L, MovementType.ENTRADA, "Farinha", "C1", 
                new BigDecimal("100"), IngredientType.SECO, "João", now);

        // Then
        assertNotNull(m.getMovementDateTime());
        assertEquals(now, m.getMovementDateTime());
    }

    @Test
    @DisplayName("Deve criar movimentos diferentes com mesmos valores mas IDs diferentes")
    void testMovement_EqualityByFields() {
        // Given
        Movement m1 = new Movement(1L, MovementType.ENTRADA, "Farinha", "C1", 
                new BigDecimal("100"), IngredientType.SECO, "João", testDateTime);
        Movement m2 = new Movement(2L, MovementType.ENTRADA, "Farinha", "C1", 
                new BigDecimal("100"), IngredientType.SECO, "João", testDateTime);

        // When & Then
        assertNotEquals(m1.getId(), m2.getId());
        assertEquals(m1.getType(), m2.getType());
        assertEquals(m1.getIngredientName(), m2.getIngredientName());
        assertEquals(m1.getCompartmentCode(), m2.getCompartmentCode());
        assertEquals(m1.getQuantity(), m2.getQuantity());
    }

    @Test
    @DisplayName("Deve criar movimento com responsável com nome longo")
    void testMovement_WithLongResponsibleName() {
        // Given
        String longName = "João da Silva Santos Oliveira";

        // When
        Movement m = new Movement(1L, MovementType.ENTRADA, "Farinha", "C1", 
                new BigDecimal("100"), IngredientType.SECO, longName, testDateTime);

        // Then
        assertEquals(longName, m.getResponsible());
    }

    @Test
    @DisplayName("Deve criar movimento com compartimento diferente")
    void testMovement_WithDifferentCompartments() {
        // When
        Movement m1 = new Movement(1L, MovementType.ENTRADA, "Farinha", "C1", 
                new BigDecimal("100"), IngredientType.SECO, "João", testDateTime);
        Movement m2 = new Movement(2L, MovementType.ENTRADA, "Açúcar", "C2", 
                new BigDecimal("50"), IngredientType.SECO, "Maria", testDateTime);

        // Then
        assertEquals("C1", m1.getCompartmentCode());
        assertEquals("C2", m2.getCompartmentCode());
        assertNotEquals(m1.getCompartmentCode(), m2.getCompartmentCode());
    }
}

