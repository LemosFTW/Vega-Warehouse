package com.vega.warehouse.domain.model;

import com.vega.warehouse.domain.enums.IngredientType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Ingredient {

    private Long id;
    private String name;
    private IngredientType type;
    private BigDecimal quantity;
    private String unit;
    private LocalDateTime createdAt;

    public Ingredient(Long id,
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

    public Ingredient(String name,
                      IngredientType type,
                      BigDecimal quantity,
                      String unit,
                      LocalDateTime createdAt) {
        this(null, name, type, quantity, unit, createdAt);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IngredientType getType() {
        return type;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void updateQuantity(BigDecimal newQuantity) {
        this.quantity = newQuantity;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
