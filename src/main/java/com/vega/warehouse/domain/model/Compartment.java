package com.vega.warehouse.domain.model;

import com.vega.warehouse.domain.enums.IngredientType;
import com.vega.warehouse.domain.service.CapacityService;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Compartment {

    private Long id;
    private String code;
    private IngredientType type;
    private BigDecimal maxCapacity;
    private BigDecimal currentQuantity;
    private LocalDate lastTypeChangeDate;

    public Compartment(Long id,
                       String code,
                       IngredientType type,
                       BigDecimal maxCapacity,
                       BigDecimal currentQuantity,
                       LocalDate lastTypeChangeDate) {
        this.id = id;
        this.code = code;
        this.type = type;
        this.maxCapacity = maxCapacity;
        this.currentQuantity = currentQuantity;
        this.lastTypeChangeDate = lastTypeChangeDate;
    }

    public Compartment(String code,
                       IngredientType type,
                       BigDecimal maxCapacity,
                       BigDecimal currentQuantity,
                       LocalDate lastTypeChangeDate) {
        this(null, code, type, maxCapacity, currentQuantity, lastTypeChangeDate);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public IngredientType getType() {
        return type;
    }

    public void setType(IngredientType type) {
        this.type = type;
    }

    public BigDecimal getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(BigDecimal maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public BigDecimal getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(BigDecimal currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public LocalDate getLastTypeChangeDate() {
        return lastTypeChangeDate;
    }

    public void setLastTypeChangeDate(LocalDate lastTypeChangeDate) {
        this.lastTypeChangeDate = lastTypeChangeDate;
    }

    public BigDecimal getAvailableSpace() {
        return maxCapacity.subtract(currentQuantity);
    }

    public boolean hasEnoughSpace(BigDecimal requiredQuantity) {
        return getAvailableSpace().compareTo(requiredQuantity) >= 0;
    }

    public boolean canStoreType(IngredientType requestedType) {
        // Se está vazio, pode armazenar qualquer tipo (mas precisa ter capacidade correta)
        if (currentQuantity.compareTo(BigDecimal.ZERO) == 0) {
            // Verifica se a capacidade máxima do compartimento é adequada para o tipo solicitado
            BigDecimal requiredCapacity = CapacityService.getMaxCapacityForType(requestedType);
            return maxCapacity.compareTo(requiredCapacity) >= 0;
        }

        // Se tem o mesmo tipo, pode armazenar
        if (type.equals(requestedType)) {
            return true;
        }

        // Se mudou de tipo hoje, não pode armazenar outro tipo até amanhã
        if (lastTypeChangeDate != null && lastTypeChangeDate.equals(LocalDate.now())) {
            return false;
        }

        // Se mudou de tipo antes de hoje, pode armazenar novo tipo (mas precisa ter capacidade correta)
        BigDecimal requiredCapacity = CapacityService.getMaxCapacityForType(requestedType);
        return maxCapacity.compareTo(requiredCapacity) >= 0;
    }

    @Override
    public String toString() {
        return "Compartment{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", type=" + type +
                ", maxCapacity=" + maxCapacity +
                ", currentQuantity=" + currentQuantity +
                ", lastTypeChangeDate=" + lastTypeChangeDate +
                '}';
    }
}

