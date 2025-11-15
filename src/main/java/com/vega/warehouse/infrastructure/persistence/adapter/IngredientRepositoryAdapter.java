package com.vega.warehouse.infrastructure.persistence.adapter;

import com.vega.warehouse.domain.model.Ingredient;
import com.vega.warehouse.domain.repository.IngredientRepositoryPort;
import com.vega.warehouse.infrastructure.persistence.entity.IngredientEntity;
import com.vega.warehouse.infrastructure.persistence.mapper.IngredientEntityMapper;
import com.vega.warehouse.infrastructure.persistence.springdata.IngredientSpringDataRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class IngredientRepositoryAdapter implements IngredientRepositoryPort {

    private final IngredientSpringDataRepository springDataRepository;

    public IngredientRepositoryAdapter(IngredientSpringDataRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        IngredientEntity entity = IngredientEntityMapper.toEntity(ingredient);
        IngredientEntity saved = springDataRepository.save(entity);
        return IngredientEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Ingredient> findById(Long id) {
        return springDataRepository.findById(id)
                .map(IngredientEntityMapper::toDomain);
    }

    @Override
    public List<Ingredient> findAll() {
        return springDataRepository.findAll()
                .stream()
                .map(IngredientEntityMapper::toDomain)
                .toList();
    }
}
