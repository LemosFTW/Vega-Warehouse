package com.vega.warehouse.domain.model;

import com.vega.warehouse.domain.enums.IngredientType;
import com.vega.warehouse.domain.enums.MovementType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Representa uma movimentação de estoque (entrada ou saída).
 * <p>
 * Registra todas as movimentações de ingredientes no armazém,
 * incluindo informações sobre o tipo de movimento, ingrediente,
 * compartimento, quantidade, responsável e data/hora da movimentação.
 * </p>
 *
 * @author LemosFTW
 */
public class Movement {

    /** Identificador único da movimentação */
    private final Long id;
    
    /** Tipo de movimentação (ENTRADA ou SAIDA) */
    private final MovementType type;
    
    /** Nome do ingrediente movimentado */
    private final String ingredientName;
    
    /** Código do compartimento envolvido na movimentação */
    private final String compartmentCode;
    
    /** Quantidade movimentada */
    private final BigDecimal quantity;
    
    /** Tipo do ingrediente movimentado */
    private final IngredientType ingredientType;
    
    /** Nome do responsável pela movimentação */
    private final String responsible;
    
    /** Data e hora da movimentação */
    private final LocalDateTime movementDateTime;

    /**
     * Construtor completo da movimentação.
     *
     * @param id identificador único
     * @param type tipo de movimentação (ENTRADA ou SAIDA)
     * @param ingredientName nome do ingrediente
     * @param compartmentCode código do compartimento
     * @param quantity quantidade movimentada
     * @param ingredientType tipo do ingrediente
     * @param responsible responsável pela movimentação
     * @param movementDateTime data e hora da movimentação
     */
    public Movement(Long id,
                    MovementType type,
                    String ingredientName,
                    String compartmentCode,
                    BigDecimal quantity,
                    IngredientType ingredientType,
                    String responsible,
                    LocalDateTime movementDateTime) {

        this.id = id;
        this.type = type;
        this.ingredientName = ingredientName;
        this.compartmentCode = compartmentCode;
        this.quantity = quantity;
        this.movementDateTime = movementDateTime;
        this.responsible = responsible;
        this.ingredientType = ingredientType;
    }

    public Long getId() {
        return id;
    }

    public MovementType getType() {
        return type;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public String getCompartmentCode() {
        return compartmentCode;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public LocalDateTime getMovementDateTime() {
        return movementDateTime;
    }

    public IngredientType getIngredientType() {
        return ingredientType;
    }

    public String getResponsible() {
        return responsible;
    }
}
