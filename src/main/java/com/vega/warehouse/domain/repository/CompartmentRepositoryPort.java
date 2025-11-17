package com.vega.warehouse.domain.repository;

import com.vega.warehouse.domain.enums.IngredientType;
import com.vega.warehouse.domain.model.Compartment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Porta de repositório para operações de persistência de compartimentos.
 * <p>
 * Define o contrato para acesso aos dados de compartimentos,
 * seguindo o padrão de Ports and Adapters (Hexagonal Architecture).
 * </p>
 *
 * @author LemosFTW
 */
public interface CompartmentRepositoryPort {

    /**
     * Salva ou atualiza um compartimento.
     *
     * @param compartment compartimento a ser salvo
     * @return compartimento salvo com ID atribuído
     */
    Compartment save(Compartment compartment);

    /**
     * Busca um compartimento pelo ID.
     *
     * @param id identificador do compartimento
     * @return Optional contendo o compartimento se encontrado
     */
    Optional<Compartment> findById(Long id);

    /**
     * Busca um compartimento pelo código.
     *
     * @param code código único do compartimento
     * @return Optional contendo o compartimento se encontrado
     */
    Optional<Compartment> findByCode(String code);

    /**
     * Retorna todos os compartimentos cadastrados.
     *
     * @return lista de todos os compartimentos
     */
    List<Compartment> findAll();

    /**
     * Busca compartimentos disponíveis para armazenar um tipo e quantidade específicos.
     * <p>
     * Retorna compartimentos que podem armazenar o tipo solicitado
     * e possuem espaço suficiente para a quantidade requerida.
     * </p>
     *
     * @param type tipo de ingrediente
     * @param quantity quantidade necessária
     * @return lista de compartimentos disponíveis
     */
    List<Compartment> findAvailableForStorage(IngredientType type, BigDecimal quantity);

    /**
     * Busca compartimentos disponíveis para venda de um tipo específico.
     * <p>
     * Retorna compartimentos que contêm o tipo solicitado e possuem
     * quantidade disponível para venda.
     * </p>
     *
     * @param type tipo de ingrediente
     * @return lista de compartimentos disponíveis para venda
     */
    List<Compartment> findAvailableForSaleByType(IngredientType type);
}

