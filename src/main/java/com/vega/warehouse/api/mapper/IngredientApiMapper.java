package com.vega.warehouse.api.mapper;

import com.vega.warehouse.api.dto.response.IngredientResponse;
import com.vega.warehouse.domain.model.Ingredient;

public class IngredientApiMapper {

    private IngredientApiMapper() {}

    public static IngredientResponse toResponse(Ingredient ingredient) {
        return new IngredientResponse(
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType(),
                ingredient.getQuantity(),
                ingredient.getUnit(),
                ingredient.getCreatedAt()
        );
    }
}
