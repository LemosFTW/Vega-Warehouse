package com.vega.warehouse.infrastructure.persistence.mapper;

import com.vega.warehouse.domain.model.Movement;
import com.vega.warehouse.infrastructure.persistence.entity.MovementEntity;

public class MovementEntityMapper {

    private MovementEntityMapper() {}

    public static Movement toDomain(MovementEntity entity) {
        if (entity == null) return null;

        String ingredientName = entity.getIngredient() != null
                ? entity.getIngredient().getName()
                : null;

        String compartmentCode = entity.getCompartment() != null
                ? entity.getCompartment().getCode()
                : null;

        return new Movement(
                entity.getId(),
                entity.getType(),
                ingredientName,
                compartmentCode,
                entity.getQuantity(),
                entity.getMovementDateTime()
        );
    }
}