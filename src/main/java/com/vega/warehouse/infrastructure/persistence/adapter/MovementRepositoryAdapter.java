package com.vega.warehouse.infrastructure.persistence.adapter;

import com.vega.warehouse.domain.enums.MovementSortBy;
import com.vega.warehouse.domain.enums.SortDirection;
import com.vega.warehouse.domain.model.Movement;
import com.vega.warehouse.domain.repository.MovementRepositoryPort;
import com.vega.warehouse.infrastructure.persistence.mapper.MovementEntityMapper;
import com.vega.warehouse.infrastructure.persistence.springdata.MovementSpringDataRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovementRepositoryAdapter implements MovementRepositoryPort {

    private final MovementSpringDataRepository springDataRepository;

    public MovementRepositoryAdapter(MovementSpringDataRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public List<Movement> findAllSorted(MovementSortBy sortBy, SortDirection direction) {
        Sort.Direction dir = (direction == SortDirection.ASC)
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;

        String property = switch (sortBy) {
            case DATE -> "movementDateTime";
            case COMPARTMENT -> "compartment.code";
        };

        Sort sort = Sort.by(dir, property);

        return springDataRepository.findAll(sort)
                .stream()
                .map(MovementEntityMapper::toDomain)
                .toList();
    }
}