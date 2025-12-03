package com.vega.warehouse.infrastructure.persistence.entity;

//enum
import com.vega.warehouse.domain.enums.IngredientType;

//libs
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade JPA que representa um ingrediente no banco de dados.
 * <p>
 * Mapeia a tabela "ingredient" e corresponde ao modelo de domínio
 * {@link com.vega.warehouse.domain.model.Ingredient}.
 * </p>
 *
 * @author LemosFTW
 */
@Entity
@Table(name = "ingredient")
public class IngredientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private IngredientType type;

    @Column(nullable = false, precision = 15, scale = 3)
    private BigDecimal quantity;

    @Column(nullable = false, length = 5)
    private String unit;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column()
    private int price;
    protected IngredientEntity() {
    }

    public IngredientEntity(String name,
                            IngredientType type,
                            BigDecimal quantity,
                            String unit,
                            LocalDateTime createdAt,
                            int price) {
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.unit = unit;
        this.createdAt = createdAt;
        this.price = price;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public IngredientType getType() {
        return type;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(IngredientType type) {
        this.type = type;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    public int getPrice() {
        return price;
    }
}
