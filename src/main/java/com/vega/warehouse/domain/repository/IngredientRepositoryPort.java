package com.vega.warehouse.domain.repository;

import com.vega.warehouse.domain.model.Ingredient;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IngredientRepositoryPort {

    Ingredient save(Ingredient ingredient);

    Optional<Ingredient> findById(Long id);

    List<Ingredient> findAll();

    Ingredient updateVolume(Ingredient ingredient, BigDecimal quantity);

    Optional<Ingredient> findbyName(String name);
}
