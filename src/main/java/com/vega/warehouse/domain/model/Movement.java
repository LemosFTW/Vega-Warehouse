package com.vega.warehouse.domain.model;

import com.vega.warehouse.domain.enums.MovementType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Movement {

    private final Long id;
    private final MovementType type;
    private final String ingredientName;
    private final String compartmentCode;
    private final BigDecimal quantity;
    private final LocalDateTime movementDateTime;

    public Movement(Long id,
                    MovementType type,
                    String ingredientName,
                    String compartmentCode,
                    BigDecimal quantity,
                    LocalDateTime movementDateTime) {
        this.id = id;
        this.type = type;
        this.ingredientName = ingredientName;
        this.compartmentCode = compartmentCode;
        this.quantity = quantity;
        this.movementDateTime = movementDateTime;
    }

    public Long getId() {
        return id;
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
