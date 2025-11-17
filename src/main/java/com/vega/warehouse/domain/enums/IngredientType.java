package com.vega.warehouse.domain.enums;

/**
 * Enum que representa os tipos de ingredientes suportados pelo sistema.
 * <p>
 * Cada tipo possui regras específicas de capacidade máxima
 * para armazenamento nos compartimentos.
 * </p>
 *
 * @author LemosFTW
 */
public enum IngredientType {
    /** Ingrediente seco */
    SECO,
    
    /** Ingrediente líquido */
    LIQUIDO,
    
    /** Ingrediente refrigerado */
    REFRIGERADO
}