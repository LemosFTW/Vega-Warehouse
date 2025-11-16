package com.vega.warehouse.application.dto;

import com.vega.warehouse.domain.enums.MovementType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MovementLogResponse {

    private MovementType type;
    private String ingredientName;
    private String compartmentCode;
    private BigDecimal quantity;
    private LocalDateTime movementDateTime;

    public MovementLogResponse(MovementType type,
                               String ingredientName,
                               String compartmentCode,
                               BigDecimal quantity,
                               LocalDateTime movementDateTime) {
        this.type = type;
        this.ingredientName = ingredientName;
        this.compartmentCode = compartmentCode;
        this.quantity = quantity;
        this.movementDateTime = movementDateTime;
    }

    public MovementType getType() {
        return type;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public String getCompartmentCode() {
        return compartmentCode;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public LocalDateTime getMovementDateTime() {
        return movementDateTime;
    }
}
