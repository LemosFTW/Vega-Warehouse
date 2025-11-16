package com.vega.warehouse.infrastructure.persistence.entity;

import com.vega.warehouse.domain.enums.IngredientType;
import com.vega.warehouse.domain.enums.MovementType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movement")
public class MovementEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MovementType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private IngredientEntity ingredient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compartment_id", nullable = false)
    private CompartmentEntity compartment;

    @Column(nullable = false, precision = 15, scale = 3)
    private BigDecimal quantity;

    @Column(nullable = false)
    private LocalDateTime movementDateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private IngredientType ingredientType;

    @Column(nullable = false)
    private String responsible;

    protected MovementEntity() {
    }

    public MovementEntity(MovementType type,
                          IngredientEntity ingredient,
                          CompartmentEntity compartment,
                          BigDecimal quantity,
                          String responsible,
                          IngredientType ingredientType,
                          LocalDateTime movementDateTime) {
        this.type = type;
        this.ingredient = ingredient;
        this.compartment = compartment;
        this.quantity = quantity;
        this.responsible = responsible;
        this.ingredientType = ingredientType;
        this.movementDateTime = movementDateTime;
    }

    public Long getId() {
        return id;
    }

    public MovementType getType() {
        return type;
    }

    public IngredientEntity getIngredient() {
        return ingredient;
    }

    public CompartmentEntity getCompartment() {
        return compartment;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public LocalDateTime getMovementDateTime() {
        return movementDateTime;
    }

    public void setType(MovementType type) {
        this.type = type;
    }

    public void setIngredient(IngredientEntity ingredient) {
        this.ingredient = ingredient;
    }

    public void setCompartment(CompartmentEntity compartment) {
        this.compartment = compartment;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void setMovementDateTime(LocalDateTime movementDateTime) {
        this.movementDateTime = movementDateTime;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public MovementType getMovementType() {
        return this.type;
    }

    public IngredientType getIngredientType() {
        return this.ingredientType;
    }

    public void setIngredientType(IngredientType ingredientType) {
        this.ingredientType = ingredientType;
    }
}
