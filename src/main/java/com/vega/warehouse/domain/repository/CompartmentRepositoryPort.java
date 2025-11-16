package com.vega.warehouse.domain.repository;

import com.vega.warehouse.domain.enums.IngredientType;
import com.vega.warehouse.domain.model.Compartment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CompartmentRepositoryPort {

    Compartment save(Compartment compartment);

    Optional<Compartment> findById(Long id);

    Optional<Compartment> findByCode(String code);

    List<Compartment> findAll();

    List<Compartment> findAvailableForStorage(IngredientType type, BigDecimal quantity);

}

