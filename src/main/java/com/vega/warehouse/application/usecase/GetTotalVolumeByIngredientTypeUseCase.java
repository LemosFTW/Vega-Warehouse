package com.vega.warehouse.application.usecase;

import com.vega.warehouse.domain.model.IngredientVolumeByType;
import com.vega.warehouse.domain.repository.IngredientRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetTotalVolumeByIngredientTypeUseCase {

    private final IngredientRepositoryPort ingredientRepository;

    public GetTotalVolumeByIngredientTypeUseCase(IngredientRepositoryPort ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public List<IngredientVolumeByType> execute() {
        return ingredientRepository.getTotalVolumeByType();
    }
}