package com.vega.warehouse.domain.enums;

/**
 * Enum que representa a direção de ordenação (crescente ou decrescente).
 *
 * @author LemosFTW
 */
public enum SortDirection {
    /** Ordenação crescente */
    ASC,
    
    /** Ordenação decrescente */
    DESC;

    /**
     * Converte uma string em um valor do enum.
     * <p>
     * Se o valor for null ou inválido, retorna DESC como padrão.
     * </p>
     *
     * @param value valor a ser convertido (case-insensitive)
     * @return valor do enum correspondente, ou DESC se inválido
     */
    public static SortDirection fromParam(String value) {
        if (value == null) return DESC;
        return switch (value.toLowerCase()) {
            case "asc" -> ASC;
            case "desc" -> DESC;
            default -> DESC;
        };
    }
}