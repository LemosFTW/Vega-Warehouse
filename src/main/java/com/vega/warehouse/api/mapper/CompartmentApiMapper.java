package com.vega.warehouse.api.mapper;

import com.vega.warehouse.application.dto.AvailableCompartmentResponse;
import com.vega.warehouse.domain.model.Compartment;

public class CompartmentApiMapper {

    private CompartmentApiMapper() {}

    public static AvailableCompartmentResponse toResponse(Compartment compartment) {
        if (compartment == null) return null;

        return new AvailableCompartmentResponse(
                compartment.getId(),
                compartment.getCode(),
                compartment.getType(),
                compartment.getMaxCapacity(),
                compartment.getCurrentQuantity(),
                compartment.getAvailableSpace(),
                compartment.getLastTypeChangeDate()
        );
    }
}

