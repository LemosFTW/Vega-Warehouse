package com.vega.warehouse.infrastructure.persistence.springdata;

import com.vega.warehouse.domain.enums.IngredientType;

import java.math.BigDecimal;

public interface IngredientVolumeByTypeProjection {
    IngredientType getType();
    BigDecimal getTotalQuantity();
}