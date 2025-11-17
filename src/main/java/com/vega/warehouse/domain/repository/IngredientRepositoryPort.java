package com.vega.warehouse.domain.repository;

import com.vega.warehouse.domain.model.Ingredient;
import com.vega.warehouse.domain.model.IngredientVolumeByType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Porta de repositório para operações de persistência de ingredientes.
 * <p>
 * Define o contrato para acesso aos dados de ingredientes,
 * seguindo o padrão de Ports and Adapters (Hexagonal Architecture).
 * </p>
 *
 * @author LemosFTW
 */
public interface IngredientRepositoryPort {

    /**
     * Salva ou atualiza um ingrediente.
     *
     * @param ingredient ingrediente a ser salvo
     * @return ingrediente salvo com ID atribuído
     */
    Ingredient save(Ingredient ingredient);

    /**
     * Busca um ingrediente pelo ID.
     *
     * @param id identificador do ingrediente
     * @return Optional contendo o ingrediente se encontrado
     */
    Optional<Ingredient> findById(Long id);

    /**
     * Retorna todos os ingredientes cadastrados.
     *
     * @return lista de todos os ingredientes
     */
    List<Ingredient> findAll();

    /**
     * Atualiza o volume de um ingrediente.
     *
     * @param ingredient ingrediente a ser atualizado
     * @param quantity nova quantidade
     * @return ingrediente atualizado
     */
    Ingredient updateVolume(Ingredient ingredient, BigDecimal quantity);

    /**
     * Busca um ingrediente pelo nome.
     *
     * @param name nome do ingrediente
     * @return Optional contendo o ingrediente se encontrado
     */
    Optional<Ingredient> findbyName(String name);

    /**
     * Retorna o volume total de ingredientes agrupado por tipo.
     *
     * @return lista com o volume total de cada tipo de ingrediente
     */
    List<IngredientVolumeByType> getTotalVolumeByType();

}
