package com.vega.warehouse.infrastructure.persistence.entity;

import com.vega.warehouse.domain.enums.IngredientType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "compartment")
public class CompartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private IngredientType type;

    @Column(nullable = false, precision = 15, scale = 3)
    private BigDecimal maxCapacity;

    @Column(nullable = false, precision = 15, scale = 3)
    private BigDecimal currentQuantity;

    @Column
    private LocalDate lastTypeChangeDate;

    protected CompartmentEntity() {
    }

    public CompartmentEntity(String code,
                             IngredientType type,
                             BigDecimal maxCapacity,
                             BigDecimal currentQuantity,
                             LocalDate lastTypeChangeDate) {
        this.code = code;
        this.type = type;
        this.maxCapacity = maxCapacity;
        this.currentQuantity = currentQuantity;
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

    public LocalDate getLastTypeChangeDate() {
        return lastTypeChangeDate;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setType(IngredientType type) {
        this.type = type;
    }

    public void setMaxCapacity(BigDecimal maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void setCurrentQuantity(BigDecimal currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public void setLastTypeChangeDate(LocalDate lastTypeChangeDate) {
        this.lastTypeChangeDate = lastTypeChangeDate;
    }
}
