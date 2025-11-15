package com.vega.warehouse.domain.repository;

import com.vega.warehouse.domain.model.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientRepositoryPort {

    Ingredient save(Ingredient ingredient);

    Optional<Ingredient> findById(Long id);

    List<Ingredient> findAll();
}
