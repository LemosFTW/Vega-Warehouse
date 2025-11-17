package com.vega.warehouse.domain.enums;

/**
 * Enum que representa os critérios de ordenação para movimentações.
 *
 * @author LemosFTW
 */
public enum MovementSortBy {
    /** Ordenação por data */
    DATE,
    
    /** Ordenação por compartimento */
    COMPARTMENT;

    /**
     * Converte uma string em um valor do enum.
     * <p>
     * Se o valor for null ou inválido, retorna DATE como padrão.
     * </p>
     *
     * @param value valor a ser convertido (case-insensitive)
     * @return valor do enum correspondente, ou DATE se inválido
     */
    public static MovementSortBy fromParam(String value) {
        if (value == null) return DATE;
        return switch (value.toLowerCase()) {
            case "compartment" -> COMPARTMENT;
            case "date" -> DATE;
            default -> DATE;
        };
    }
}