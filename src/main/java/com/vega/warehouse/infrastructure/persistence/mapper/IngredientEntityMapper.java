package com.vega.warehouse.infrastructure.persistence.mapper;

import com.vega.warehouse.domain.model.Ingredient;
import com.vega.warehouse.infrastructure.persistence.entity.IngredientEntity;

public class IngredientEntityMapper {

    private IngredientEntityMapper() {}

    public static IngredientEntity toEntity(Ingredient ingredient) {
        if (ingredient == null) return null;

        IngredientEntity entity = new IngredientEntity(
                ingredient.getName(),
                ingredient.getType(),
                ingredient.getQuantity(),
                ingredient.getUnit(),
                ingredient.getCreatedAt(),
                ingredient.getPrice()
        );

        if (ingredient.getId() != null)
            entity.setId(ingredient.getId());


        return entity;
    }

    public static Ingredient toDomain(IngredientEntity entity) {
        if (entity == null) return null;

        return new Ingredient(
                entity.getId(),
                entity.getName(),
                entity.getType(),
                entity.getQuantity(),
                entity.getUnit(),
                entity.getCreatedAt(),
                entity.getPrice()
        );
    }
}
