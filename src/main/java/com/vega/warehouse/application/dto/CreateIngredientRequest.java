package com.vega.warehouse.api.dto.request;

import com.vega.warehouse.domain.enums.IngredientType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CreateIngredientRequest {

    @NotBlank
    private String name;

    @NotNull
    private IngredientType type;

    @NotNull
    @Min(0)
    private BigDecimal quantity;

    @NotBlank
    private String unit;

    @NotNull
    @Min(0)
    private int price;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setType(IngredientType type) {
        this.type = type;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
