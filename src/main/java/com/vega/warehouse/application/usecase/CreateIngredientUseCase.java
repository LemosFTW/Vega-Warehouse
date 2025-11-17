package com.vega.warehouse.application.usecase;

import com.vega.warehouse.domain.enums.IngredientType;
import com.vega.warehouse.domain.model.Ingredient;
import com.vega.warehouse.domain.repository.IngredientRepositoryPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Caso de uso para criação de ingredientes.
 * <p>
 * Se o ingrediente já existir (mesmo nome), atualiza a quantidade.
 * Caso contrário, cria um novo ingrediente.
 * </p>
 *
 * @author LemosFTW
 */
@Service
public class CreateIngredientUseCase {

    private final IngredientRepositoryPort ingredientRepository;

    /**
     * Construtor do caso de uso.
     *
     * @param ingredientRepository repositório de ingredientes
     */
    public CreateIngredientUseCase(IngredientRepositoryPort ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    /**
     * Executa a criação ou atualização de um ingrediente.
     * <p>
     * Se um ingrediente com o mesmo nome já existir, atualiza sua quantidade.
     * Caso contrário, cria um novo ingrediente.
     * </p>
     *
     * @param name nome do ingrediente
     * @param type tipo do ingrediente
     * @param quantity quantidade do ingrediente
     * @param unit unidade de medida
     * @return ingrediente criado ou atualizado
     */
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
