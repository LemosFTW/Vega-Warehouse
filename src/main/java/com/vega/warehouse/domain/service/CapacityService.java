package com.vega.warehouse.domain.service;

import com.vega.warehouse.domain.enums.IngredientType;

import java.math.BigDecimal;

public class CapacityService {

    private CapacityService() {}

    public static BigDecimal getMaxCapacityForType(IngredientType type) {
        return switch (type) {
            case SECO      -> new BigDecimal("600");
            case LIQUIDO   -> new BigDecimal("500");
            case REFRIGERADO -> new BigDecimal("400");
        };
    }
}
