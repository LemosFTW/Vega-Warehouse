package com.vega.warehouse.domain.repository;

import com.vega.warehouse.domain.enums.MovementSortBy;
import com.vega.warehouse.domain.enums.SortDirection;
import com.vega.warehouse.domain.model.Movement;

import java.util.List;

/**
 * Porta de repositório para operações de persistência de movimentações.
 * <p>
 * Define o contrato para acesso aos dados de movimentações,
 * seguindo o padrão de Ports and Adapters (Hexagonal Architecture).
 * </p>
 *
 * @author LemosFTW
 */
public interface MovementRepositoryPort {

    /**
     * Salva uma movimentação.
     *
     * @param movement movimentação a ser salva
     * @return movimentação salva com ID atribuído
     */
    Movement save(Movement movement);

    /**
     * Retorna todas as movimentações ordenadas conforme os parâmetros fornecidos.
     *
     * @param sortBy critério de ordenação (data ou compartimento)
     * @param direction direção da ordenação (crescente ou decrescente)
     * @return lista de movimentações ordenadas
     */
    List<Movement> findAllSorted(MovementSortBy sortBy, SortDirection direction);
}