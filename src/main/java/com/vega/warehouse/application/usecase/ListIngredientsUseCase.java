package com.vega.warehouse.application.usecase;

import com.vega.warehouse.domain.model.Ingredient;
import com.vega.warehouse.domain.repository.IngredientRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListIngredientsUseCase {

    private final IngredientRepositoryPort ingredientRepository;

    public ListIngredientsUseCase(IngredientRepositoryPort ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<Ingredient> execute() {
        return ingredientRepository.findAll();
    }
}
