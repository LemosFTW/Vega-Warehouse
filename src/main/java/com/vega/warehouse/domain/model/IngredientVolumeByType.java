package com.vega.warehouse.domain.model;

import com.vega.warehouse.domain.enums.IngredientType;

import java.math.BigDecimal;

/**
 * Representa o volume total de ingredientes agrupado por tipo.
 * <p>
 * Utilizado para relatórios e consultas sobre a quantidade total
 * de cada tipo de ingrediente no armazém.
 * </p>
 *
 * @author LemosFTW
 */
public class IngredientVolumeByType {

    /** Tipo do ingrediente */
    private final IngredientType type;
    
    /** Quantidade total do tipo de ingrediente */
    private final BigDecimal totalQuantity;

    /**
     * Construtor do volume por tipo.
     *
     * @param type tipo do ingrediente
     * @param totalQuantity quantidade total do tipo
     */
    public IngredientVolumeByType(IngredientType type, BigDecimal totalQuantity) {
        this.type = type;
        this.totalQuantity = totalQuantity;
    }

    public IngredientType getType() {
        return type;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }
}