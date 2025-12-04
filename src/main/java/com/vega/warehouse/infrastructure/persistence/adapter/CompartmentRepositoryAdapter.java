package com.vega.warehouse.infrastructure.persistence.adapter;

import com.vega.warehouse.domain.enums.IngredientType;
import com.vega.warehouse.domain.model.Compartment;
import com.vega.warehouse.domain.repository.CompartmentRepositoryPort;
import com.vega.warehouse.infrastructure.persistence.entity.CompartmentEntity;
import com.vega.warehouse.infrastructure.persistence.mapper.CompartmentEntityMapper;
import com.vega.warehouse.infrastructure.persistence.springdata.CompartmentSpringDataRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class CompartmentRepositoryAdapter implements CompartmentRepositoryPort {

    private final CompartmentSpringDataRepository springDataRepository;

    public CompartmentRepositoryAdapter(CompartmentSpringDataRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Transactional
    @Override
    public Compartment save(Compartment compartment) {
        CompartmentEntity entity = CompartmentEntityMapper.toEntity(compartment);
        CompartmentEntity saved = springDataRepository.save(entity);
        return CompartmentEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Compartment> findById(Long id) {
        return springDataRepository.findById(id)
                .map(CompartmentEntityMapper::toDomain);
    }

    @Override
    public Optional<Compartment> findByCode(String code) {
        return springDataRepository.findByCode(code)
                .map(CompartmentEntityMapper::toDomain);
    }

    @Override
    public List<Compartment> findAll() {
        return springDataRepository.findAll()
                .stream()
                .map(CompartmentEntityMapper::toDomain)
                .toList();
    }

    @Override
    public List<Compartment> findAvailableForStorage(IngredientType type, BigDecimal quantity) {
        return springDataRepository.findAll()
                .stream()
                .map(CompartmentEntityMapper::toDomain)
                .filter(compartment -> {
                    // Verifica se pode armazenar o tipo e se tem espaço suficiente
                    return compartment.canStoreType(type) && compartment.hasEnoughSpace(quantity);
                })
                .toList();
    }

    @Override
    public List<Compartment> findAvailableForSaleByType(IngredientType type) {
        return springDataRepository
                .findByTypeAndCurrentQuantityGreaterThan(type, BigDecimal.ZERO)
                .stream()
                .map(CompartmentEntityMapper::toDomain)
                .toList();
    }
}

