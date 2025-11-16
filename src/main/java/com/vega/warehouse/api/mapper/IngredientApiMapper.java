package com.vega.warehouse.api.mapper;

import com.vega.warehouse.api.dto.response.IngredientResponse;
import com.vega.warehouse.application.dto.IngredientVolumeByTypeResponse;
import com.vega.warehouse.domain.model.Ingredient;
import com.vega.warehouse.domain.model.IngredientVolumeByType;


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
    public static IngredientVolumeByTypeResponse toResponse(IngredientVolumeByType volume) {
        return new IngredientVolumeByTypeResponse(
                volume.getType(),
                volume.getTotalQuantity()
        );
    }
}
