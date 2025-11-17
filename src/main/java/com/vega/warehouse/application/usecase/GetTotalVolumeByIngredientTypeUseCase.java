package com.vega.warehouse.application.usecase;

import com.vega.warehouse.domain.model.IngredientVolumeByType;
import com.vega.warehouse.domain.repository.IngredientRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Caso de uso para obter o volume total de ingredientes agrupado por tipo.
 * <p>
 * Retorna a quantidade total de cada tipo de ingrediente no armazém,
 * útil para relatórios e análises de estoque.
 * </p>
 *
 * @author LemosFTW
 */
@Service
public class GetTotalVolumeByIngredientTypeUseCase {

    private final IngredientRepositoryPort ingredientRepository;

    /**
     * Construtor do caso de uso.
     *
     * @param ingredientRepository repositório de ingredientes
     */
    public GetTotalVolumeByIngredientTypeUseCase(IngredientRepositoryPort ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    /**
     * Executa a busca do volume total por tipo de ingrediente.
     *
     * @return lista com o volume total de cada tipo de ingrediente
     */
    public List<IngredientVolumeByType> execute() {
        return ingredientRepository.getTotalVolumeByType();
    }
}