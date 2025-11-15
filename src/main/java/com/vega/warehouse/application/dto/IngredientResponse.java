package com.vega.warehouse.api.dto.response;

import com.vega.warehouse.domain.enums.IngredientType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class IngredientResponse {

    private Long id;
    private String name;
    private IngredientType type;
    private BigDecimal quantity;
    private String unit;
    private LocalDateTime createdAt;

    public IngredientResponse(Long id,
                              String name,
                              IngredientType type,
                              BigDecimal quantity,
                              String unit,
                              LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.unit = unit;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public IngredientType getType() {
        return type;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
