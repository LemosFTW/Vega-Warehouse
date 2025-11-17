package com.vega.warehouse.application.usecase;

import com.vega.warehouse.domain.enums.MovementSortBy;
import com.vega.warehouse.domain.enums.SortDirection;
import com.vega.warehouse.domain.model.Movement;
import com.vega.warehouse.domain.repository.MovementRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Caso de uso para listar movimentações com ordenação.
 * <p>
 * Permite ordenar as movimentações por data ou compartimento,
 * em ordem crescente ou decrescente.
 * </p>
 *
 * @author LemosFTW
 */
@Service
public class ListMovementsUseCase {

    private final MovementRepositoryPort movementRepository;

    /**
     * Construtor do caso de uso.
     *
     * @param movementRepository repositório de movimentações
     */
    public ListMovementsUseCase(MovementRepositoryPort movementRepository) {
        this.movementRepository = movementRepository;
    }

    /**
     * Executa a listagem de movimentações ordenadas.
     *
     * @param sortByParam critério de ordenação ("date" ou "compartment")
     * @param orderParam direção da ordenação ("asc" ou "desc")
     * @return lista de movimentações ordenadas
     */
    public List<Movement> execute(String sortByParam, String orderParam) {
        MovementSortBy sortBy = MovementSortBy.fromParam(sortByParam);
        SortDirection direction = SortDirection.fromParam(orderParam);
        return movementRepository.findAllSorted(sortBy, direction);
    }
}