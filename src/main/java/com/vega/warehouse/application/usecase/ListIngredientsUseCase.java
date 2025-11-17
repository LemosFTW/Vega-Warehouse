package com.vega.warehouse.application.usecase;

import com.vega.warehouse.domain.model.Ingredient;
import com.vega.warehouse.domain.repository.IngredientRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Caso de uso para listar todos os ingredientes cadastrados.
 *
 * @author LemosFTW
 */
@Service
public class ListIngredientsUseCase {

    private final IngredientRepositoryPort ingredientRepository;

    /**
     * Construtor do caso de uso.
     *
     * @param ingredientRepository repositório de ingredientes
     */
    public ListIngredientsUseCase(IngredientRepositoryPort ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    /**
     * Executa a listagem de todos os ingredientes.
     *
     * @return lista de todos os ingredientes cadastrados
     */
    public List<Ingredient> execute() {
        return ingredientRepository.findAll();
    }
}
