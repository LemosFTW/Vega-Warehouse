package com.vega.warehouse.application.usecase;

import com.vega.warehouse.domain.enums.IngredientType;
import com.vega.warehouse.domain.model.Compartment;
import com.vega.warehouse.domain.repository.CompartmentRepositoryPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class GetAvailableCompartmentsUseCase {

    private final CompartmentRepositoryPort compartmentRepository;

    public GetAvailableCompartmentsUseCase(CompartmentRepositoryPort compartmentRepository) {
        this.compartmentRepository = compartmentRepository;
    }

    public List<Compartment> execute(IngredientType type, BigDecimal quantity) {
        return compartmentRepository.findAvailableForStorage(type, quantity);
    }
}

