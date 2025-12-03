package com.vega.warehouse.domain.model;

import com.vega.warehouse.domain.enums.IngredientType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa um ingrediente no sistema de armazém.
 * <p>
 * Um ingrediente possui nome, tipo, quantidade, unidade de medida
 * e data de criação. A quantidade pode ser atualizada conforme
 * as movimentações do estoque.
 * </p>
 *
 * @author LemosFTW
 */
public class Ingredient {

    /** Identificador único do ingrediente */
    private Long id;
    
    /** Nome do ingrediente */
    private String name;
    
    /** Tipo do ingrediente (SECO, LIQUIDO, REFRIGERADO) */
    private IngredientType type;
    
    /** Quantidade disponível do ingrediente */
    private BigDecimal quantity;
    
    /** Unidade de medida (ex: kg, litros, unidades) */
    private String unit;
    
    /** Data e hora de criação do registro */
    private LocalDateTime createdAt;

    private int price;
    /**
     * Construtor completo do ingrediente.
     *
     * @param id identificador único
     * @param name nome do ingrediente
     * @param type tipo do ingrediente
     * @param quantity quantidade disponível
     * @param unit unidade de medida
     * @param createdAt data e hora de criação
     */
    public Ingredient(Long id,
                      String name,
                      IngredientType type,
                      BigDecimal quantity,
                      String unit,
                      LocalDateTime createdAt,
                      int price
    ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.unit = unit;
        this.createdAt = createdAt;
        this.price = price;
    }

    /**
     * Construtor para criação de novo ingrediente (sem ID).
     *
     * @param name nome do ingrediente
     * @param type tipo do ingrediente
     * @param quantity quantidade disponível
     * @param unit unidade de medida
     * @param createdAt data e hora de criação
     */
    public Ingredient(String name,
                      IngredientType type,
                      BigDecimal quantity,
                      String unit,
                      LocalDateTime createdAt,
                      int price) {
        this(null, name, type, quantity, unit, createdAt, price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IngredientType getType() {
        return type;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }
    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Atualiza a quantidade do ingrediente.
     *
     * @param newQuantity nova quantidade a ser definida
     */
    public void updateQuantity(BigDecimal newQuantity) {
        this.quantity = newQuantity;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                ", createdAt=" + createdAt +
                ", price=" + price +
                '}';
    }
}
