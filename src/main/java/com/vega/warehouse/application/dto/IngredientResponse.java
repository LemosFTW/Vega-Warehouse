package com.vega.warehouse.api.dto.response;
//package com.vega.warehouse.application.dto;

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
    private int price;

    public IngredientResponse(Long id,
                              String name,
                              IngredientType type,
                              BigDecimal quantity,
                              String unit,
                              LocalDateTime createdAt,
                              int price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.unit = unit;
        this.createdAt = createdAt;
        this.price = price;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
