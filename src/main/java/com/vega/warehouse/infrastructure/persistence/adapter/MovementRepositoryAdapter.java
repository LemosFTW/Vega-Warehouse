package com.vega.warehouse.infrastructure.persistence.adapter;

import com.vega.warehouse.domain.enums.MovementSortBy;
import com.vega.warehouse.domain.enums.SortDirection;
import com.vega.warehouse.domain.model.Movement;
import com.vega.warehouse.domain.repository.MovementRepositoryPort;
import com.vega.warehouse.infrastructure.persistence.entity.CompartmentEntity;
import com.vega.warehouse.infrastructure.persistence.entity.IngredientEntity;
import com.vega.warehouse.infrastructure.persistence.entity.MovementEntity;
import com.vega.warehouse.infrastructure.persistence.mapper.MovementEntityMapper;
import com.vega.warehouse.infrastructure.persistence.springdata.CompartmentSpringDataRepository;
import com.vega.warehouse.infrastructure.persistence.springdata.IngredientSpringDataRepository;
import com.vega.warehouse.infrastructure.persistence.springdata.MovementSpringDataRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class MovementRepositoryAdapter implements MovementRepositoryPort {

    private final MovementSpringDataRepository movementSpringDataRepository;
    private final IngredientSpringDataRepository ingredientSpringDataRepository;
    private final CompartmentSpringDataRepository compartmentSpringDataRepository;

    public MovementRepositoryAdapter(MovementSpringDataRepository movementSpringDataRepository,
                                    IngredientSpringDataRepository ingredientSpringDataRepository,
                                    CompartmentSpringDataRepository compartmentSpringDataRepository) {
        this.movementSpringDataRepository = movementSpringDataRepository;
        this.ingredientSpringDataRepository = ingredientSpringDataRepository;
        this.compartmentSpringDataRepository = compartmentSpringDataRepository;
    }
    @Transactional
    @Override
    public Movement save(Movement movement) {
        // Buscar IngredientEntity pelo nome
        IngredientEntity ingredientEntity = ingredientSpringDataRepository
                .findByName(movement.getIngredientName())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Ingredient not found with name: " + movement.getIngredientName()));

        // Buscar CompartmentEntity pelo código
        CompartmentEntity compartmentEntity = compartmentSpringDataRepository
                .findByCode(movement.getCompartmentCode())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Compartment not found with code: " + movement.getCompartmentCode()));

        // Criar MovementEntity com as entidades encontradas
        MovementEntity entity = MovementEntityMapper.toEntity(movement, ingredientEntity, compartmentEntity);
        MovementEntity saved = movementSpringDataRepository.save(entity);
        return MovementEntityMapper.toDomain(saved);
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

        return movementSpringDataRepository.findAll(sort)
                .stream()
                .map(MovementEntityMapper::toDomain)
                .toList();
    }
}