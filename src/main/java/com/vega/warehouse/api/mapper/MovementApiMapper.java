package com.vega.warehouse.api.mapper;

import com.vega.warehouse.application.dto.MovementLogResponse;
import com.vega.warehouse.domain.model.Movement;

public class MovementApiMapper {

    private MovementApiMapper() {}

    public static MovementLogResponse toResponse(Movement movement) {
        return new MovementLogResponse(
                movement.getType(),
                movement.getIngredientName(),
                movement.getCompartmentCode(),
                movement.getQuantity(),
                movement.getMovementDateTime()
        );
    }
}