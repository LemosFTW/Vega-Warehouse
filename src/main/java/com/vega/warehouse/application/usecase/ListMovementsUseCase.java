package com.vega.warehouse.application.usecase;

import com.vega.warehouse.domain.enums.MovementSortBy;
import com.vega.warehouse.domain.enums.SortDirection;
import com.vega.warehouse.domain.model.Movement;
import com.vega.warehouse.domain.repository.MovementRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListMovementsUseCase {

    private final MovementRepositoryPort movementRepository;

    public ListMovementsUseCase(MovementRepositoryPort movementRepository) {
        this.movementRepository = movementRepository;
    }

    public List<Movement> execute(String sortByParam, String orderParam) {
        MovementSortBy sortBy = MovementSortBy.fromParam(sortByParam);
        SortDirection direction = SortDirection.fromParam(orderParam);
        return movementRepository.findAllSorted(sortBy, direction);
    }
}