package com.vega.warehouse.application.usecase;

import com.vega.warehouse.domain.enums.IngredientType;
import com.vega.warehouse.domain.model.Ingredient;
import com.vega.warehouse.domain.repository.IngredientRepositoryPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CreateIngredientUseCase {

    private final IngredientRepositoryPort ingredientRepository;

    public CreateIngredientUseCase(IngredientRepositoryPort ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Ingredient execute(String name, IngredientType type, BigDecimal quantity, String unit) {
        Ingredient ingredient = null;
        Optional<Ingredient> hasIngredient = ingredientRepository.findbyName(name);

        if (hasIngredient.isPresent())
            ingredient = ingredientRepository.updateVolume(hasIngredient.get(), quantity);
        else
            ingredient = new Ingredient(name, type, quantity, unit, LocalDateTime.now());

        return ingredientRepository.save(ingredient);
    }
}
