package com.vega.warehouse.infrastructure.persistence.adapter;

import com.vega.warehouse.domain.model.Ingredient;
import com.vega.warehouse.domain.model.IngredientVolumeByType;
import com.vega.warehouse.domain.repository.IngredientRepositoryPort;
import com.vega.warehouse.infrastructure.persistence.entity.IngredientEntity;
import com.vega.warehouse.infrastructure.persistence.mapper.IngredientEntityMapper;
import com.vega.warehouse.infrastructure.persistence.springdata.IngredientSpringDataRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Adapter de repositório para ingredientes.
 * <p>
 * Implementa a porta de repositório do domínio utilizando
 * Spring Data JPA, seguindo o padrão de Ports and Adapters.
 * </p>
 *
 * @author LemosFTW
 */
@Component
public class IngredientRepositoryAdapter implements IngredientRepositoryPort {

    private final IngredientSpringDataRepository springDataRepository;

    public IngredientRepositoryAdapter(IngredientSpringDataRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Transactional
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

    @Transactional
    @Override
    public Ingredient updateVolume(Ingredient ingredient, BigDecimal newVolume) {
        ingredient.setQuantity(ingredient.getQuantity().add(newVolume));
        return ingredient;
    }

    @Override
    public Optional<Ingredient> findbyName(String name) {
        return springDataRepository.findByName(name)
                .map(IngredientEntityMapper::toDomain);
    }

    @Override
    public List<IngredientVolumeByType> getTotalVolumeByType() {
        return springDataRepository.getTotalVolumeByType()
                .stream()
                .map(p -> new IngredientVolumeByType(p.getType(), p.getTotalQuantity()))
                .toList();
    }
}
