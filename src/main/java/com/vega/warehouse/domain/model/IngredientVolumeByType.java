package com.vega.warehouse.domain.model;

import com.vega.warehouse.domain.enums.IngredientType;

import java.math.BigDecimal;

public class IngredientVolumeByType {

    private final IngredientType type;
    private final BigDecimal totalQuantity;

    public IngredientVolumeByType(IngredientType type, BigDecimal totalQuantity) {
        this.type = type;
        this.totalQuantity = totalQuantity;
    }

    public IngredientType getType() {
        return type;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }
}