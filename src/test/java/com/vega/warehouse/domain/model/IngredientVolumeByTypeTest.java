package com.vega.warehouse.domain.model;

import com.vega.warehouse.domain.enums.IngredientType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("IngredientVolumeByType - Testes Unitários")
class IngredientVolumeByTypeTest {

    private IngredientVolumeByType volumeByType;

    @BeforeEach
    void setUp() {
        volumeByType = new IngredientVolumeByType(IngredientType.SECO, new BigDecimal("1200"));
    }

    @Test
    @DisplayName("Deve criar IngredientVolumeByType com tipo SECO")
    void testIngredientVolumeByType_WithSeco() {
        // When
        IngredientVolumeByType v = new IngredientVolumeByType(IngredientType.SECO, new BigDecimal("1200"));

        // Then
        assertEquals(IngredientType.SECO, v.getType());
        assertEquals(new BigDecimal("1200"), v.getTotalQuantity());
    }

    @Test
    @DisplayName("Deve criar IngredientVolumeByType com tipo LIQUIDO")
    void testIngredientVolumeByType_WithLiquido() {
        // When
        IngredientVolumeByType v = new IngredientVolumeByType(IngredientType.LIQUIDO, new BigDecimal("800"));

        // Then
        assertEquals(IngredientType.LIQUIDO, v.getType());
        assertEquals(new BigDecimal("800"), v.getTotalQuantity());
    }

    @Test
    @DisplayName("Deve criar IngredientVolumeByType com tipo REFRIGERADO")
    void testIngredientVolumeByType_WithRefrigerado() {
        // When
        IngredientVolumeByType v = new IngredientVolumeByType(IngredientType.REFRIGERADO, new BigDecimal("600"));

        // Then
        assertEquals(IngredientType.REFRIGERADO, v.getType());
        assertEquals(new BigDecimal("600"), v.getTotalQuantity());
    }

    @Test
    @DisplayName("Deve retornar tipo corretamente")
    void testGetType() {
        // When & Then
        assertEquals(IngredientType.SECO, volumeByType.getType());
    }

    @Test
    @DisplayName("Deve retornar quantidade total corretamente")
    void testGetTotalQuantity() {
        // When & Then
        assertEquals(new BigDecimal("1200"), volumeByType.getTotalQuantity());
    }

    @Test
    @DisplayName("Deve criar com quantidade zero")
    void testIngredientVolumeByType_WithZeroQuantity() {
        // When
        IngredientVolumeByType v = new IngredientVolumeByType(IngredientType.SECO, BigDecimal.ZERO);

        // Then
        assertEquals(BigDecimal.ZERO, v.getTotalQuantity());
        assertEquals(IngredientType.SECO, v.getType());
    }

    @Test
    @DisplayName("Deve criar com quantidade decimal")
    void testIngredientVolumeByType_WithDecimalQuantity() {
        // When
        IngredientVolumeByType v = new IngredientVolumeByType(IngredientType.SECO, new BigDecimal("1234.567"));

        // Then
        assertEquals(new BigDecimal("1234.567"), v.getTotalQuantity());
    }

    @Test
    @DisplayName("Deve criar com quantidade muito grande")
    void testIngredientVolumeByType_WithLargeQuantity() {
        // When
        IngredientVolumeByType v = new IngredientVolumeByType(IngredientType.SECO, new BigDecimal("999999.999"));

        // Then
        assertEquals(new BigDecimal("999999.999"), v.getTotalQuantity());
    }

    @Test
    @DisplayName("Deve criar diferentes instâncias com mesmos valores")
    void testIngredientVolumeByType_EqualityByFields() {
        // Given
        IngredientVolumeByType v1 = new IngredientVolumeByType(IngredientType.SECO, new BigDecimal("1200"));
        IngredientVolumeByType v2 = new IngredientVolumeByType(IngredientType.SECO, new BigDecimal("1200"));

        // When & Then
        assertEquals(v1.getType(), v2.getType());
        assertEquals(v1.getTotalQuantity(), v2.getTotalQuantity());
    }

    @Test
    @DisplayName("Deve criar diferentes instâncias com tipos diferentes")
    void testIngredientVolumeByType_DifferentTypes() {
        // Given
        IngredientVolumeByType seco = new IngredientVolumeByType(IngredientType.SECO, new BigDecimal("1200"));
        IngredientVolumeByType liquido = new IngredientVolumeByType(IngredientType.LIQUIDO, new BigDecimal("800"));
        IngredientVolumeByType refrigerado = new IngredientVolumeByType(IngredientType.REFRIGERADO, new BigDecimal("600"));

        // When & Then
        assertEquals(IngredientType.SECO, seco.getType());
        assertEquals(IngredientType.LIQUIDO, liquido.getType());
        assertEquals(IngredientType.REFRIGERADO, refrigerado.getType());
        assertNotEquals(seco.getType(), liquido.getType());
        assertNotEquals(liquido.getType(), refrigerado.getType());
    }
}

