package com.vega.warehouse.application.dto;

import com.vega.warehouse.domain.enums.IngredientType;

import java.math.BigDecimal;

public class IngredientVolumeByTypeResponse {

    private IngredientType type;
    private BigDecimal totalQuantity;

    public IngredientVolumeByTypeResponse(IngredientType type, BigDecimal totalQuantity) {
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