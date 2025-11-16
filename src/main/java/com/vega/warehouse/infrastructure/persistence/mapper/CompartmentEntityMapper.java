package com.vega.warehouse.infrastructure.persistence.mapper;

import com.vega.warehouse.domain.model.Compartment;
import com.vega.warehouse.infrastructure.persistence.entity.CompartmentEntity;

public class CompartmentEntityMapper {

    private CompartmentEntityMapper() {}

    public static CompartmentEntity toEntity(Compartment compartment) {
        if (compartment == null) return null;

        CompartmentEntity entity = new CompartmentEntity(
                compartment.getCode(),
                compartment.getType(),
                compartment.getMaxCapacity(),
                compartment.getCurrentQuantity(),
                compartment.getLastTypeChangeDate()
        );

        if (compartment.getId() != null)
            entity.setId(compartment.getId());

        return entity;
    }

    public static Compartment toDomain(CompartmentEntity entity) {
        if (entity == null) return null;

        return new Compartment(
                entity.getId(),
                entity.getCode(),
                entity.getType(),
                entity.getMaxCapacity(),
                entity.getCurrentQuantity(),
                entity.getLastTypeChangeDate()
        );
    }
}

