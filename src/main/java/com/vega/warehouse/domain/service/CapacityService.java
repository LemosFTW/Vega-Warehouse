package com.vega.warehouse.domain.service;

import com.vega.warehouse.domain.enums.IngredientType;

import java.math.BigDecimal;

/**
 * Serviço responsável por gerenciar as capacidades máximas
 * dos compartimentos conforme o tipo de ingrediente.
 * <p>
 * Define as regras de capacidade máxima para cada tipo:
 * <ul>
 *   <li>SECO: 600 unidades</li>
 *   <li>LIQUIDO: 500 unidades</li>
 *   <li>REFRIGERADO: 400 unidades</li>
 * </ul>
 * </p>
 *
 * @author LemosFTW
 */
public class CapacityService {

    /**
     * Construtor privado para impedir instanciação.
     * Esta classe contém apenas métodos estáticos.
     */
    private CapacityService() {}

    /**
     * Retorna a capacidade máxima permitida para um tipo de ingrediente.
     *
     * @param type tipo do ingrediente
     * @return capacidade máxima em BigDecimal
     */
    public static BigDecimal getMaxCapacityForType(IngredientType type) {
        return switch (type) {
            case SECO      -> new BigDecimal("600");
            case LIQUIDO   -> new BigDecimal("500");
            case REFRIGERADO -> new BigDecimal("400");
        };
    }
}
