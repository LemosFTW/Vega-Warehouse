package com.vega.warehouse.infrastructure.persistence.mapper;

import com.vega.warehouse.domain.model.Movement;
import com.vega.warehouse.infrastructure.persistence.entity.CompartmentEntity;
import com.vega.warehouse.infrastructure.persistence.entity.IngredientEntity;
import com.vega.warehouse.infrastructure.persistence.entity.MovementEntity;

public class MovementEntityMapper {

    private MovementEntityMapper() {}

    public static MovementEntity toEntity(Movement movement,
                                         IngredientEntity ingredientEntity,
                                         CompartmentEntity compartmentEntity) {
        if (movement == null) return null;

        if (ingredientEntity == null) {
            throw new IllegalArgumentException("IngredientEntity cannot be null when creating MovementEntity");
        }

        if (compartmentEntity == null) {
            throw new IllegalArgumentException("CompartmentEntity cannot be null when creating MovementEntity");
        }

        MovementEntity entity = new MovementEntity(
                movement.getType(),
                ingredientEntity,
                compartmentEntity,
                movement.getQuantity(),
                movement.getResponsible(),
                movement.getIngredientType(),
                movement.getMovementDateTime()
        );

        return entity;
    }

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
                entity.getIngredientType(),
                entity.getResponsible(),
                entity.getMovementDateTime()
        );
    }
}