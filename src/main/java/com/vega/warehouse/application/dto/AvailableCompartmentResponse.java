package com.vega.warehouse.application.dto;

import com.vega.warehouse.domain.enums.IngredientType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AvailableCompartmentResponse {

    private Long id;
    private String code;
    private IngredientType type;
    private BigDecimal maxCapacity;
    private BigDecimal currentQuantity;
    private BigDecimal availableSpace;
    private LocalDate lastTypeChangeDate;

    public AvailableCompartmentResponse(Long id,
                                        String code,
                                        IngredientType type,
                                        BigDecimal maxCapacity,
                                        BigDecimal currentQuantity,
                                        BigDecimal availableSpace,
                                        LocalDate lastTypeChangeDate) {
        this.id = id;
        this.code = code;
        this.type = type;
        this.maxCapacity = maxCapacity;
        this.currentQuantity = currentQuantity;
        this.availableSpace = availableSpace;
        this.lastTypeChangeDate = lastTypeChangeDate;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public IngredientType getType() {
        return type;
    }

    public BigDecimal getMaxCapacity() {
        return maxCapacity;
    }

    public BigDecimal getCurrentQuantity() {
        return currentQuantity;
    }

    public BigDecimal getAvailableSpace() {
        return availableSpace;
    }

    public LocalDate getLastTypeChangeDate() {
        return lastTypeChangeDate;
    }
}

