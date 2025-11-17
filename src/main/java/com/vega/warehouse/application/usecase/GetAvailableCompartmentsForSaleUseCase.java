package com.vega.warehouse.application.usecase;

import com.vega.warehouse.domain.enums.IngredientType;
import com.vega.warehouse.domain.model.Compartment;
import com.vega.warehouse.domain.repository.CompartmentRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAvailableCompartmentsForSaleUseCase {

    private final CompartmentRepositoryPort compartmentRepository;

    public GetAvailableCompartmentsForSaleUseCase(CompartmentRepositoryPort compartmentRepository) {
        this.compartmentRepository = compartmentRepository;
    }

    public List<Compartment> execute(IngredientType type) {
        return compartmentRepository.findAvailableForSaleByType(type);
    }
}

