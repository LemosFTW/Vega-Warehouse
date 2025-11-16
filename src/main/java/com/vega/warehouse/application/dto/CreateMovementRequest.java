package com.vega.warehouse.application.dto;

import com.vega.warehouse.domain.enums.IngredientType;
import com.vega.warehouse.domain.enums.MovementType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class CreateMovementRequest {

    @NotNull
    private MovementType type;          // ENTRADA ou SAIDA

    @NotNull
    private Long ingredientId;

    @NotNull
    private String compartmentCode;

    @NotNull
    @Min(1)
    private BigDecimal quantity;

    @NotNull
    private String responsible;

    public MovementType getType() {
        return type;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public String getCompartmentCode() {
        return compartmentCode;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setType(MovementType type) {
        this.type = type;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public void setCompartmentCode(String compartmentCode) {
        this.compartmentCode = compartmentCode;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }
}