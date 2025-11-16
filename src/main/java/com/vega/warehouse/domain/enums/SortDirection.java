package com.vega.warehouse.domain.enums;

public enum SortDirection {
    ASC,
    DESC;

    public static SortDirection fromParam(String value) {
        if (value == null) return DESC;
        return switch (value.toLowerCase()) {
            case "asc" -> ASC;
            case "desc" -> DESC;
            default -> DESC;
        };
    }
}