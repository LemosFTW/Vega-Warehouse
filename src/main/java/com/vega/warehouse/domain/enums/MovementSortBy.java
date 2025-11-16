package com.vega.warehouse.domain.enums;

public enum MovementSortBy {
    DATE,
    COMPARTMENT;

    public static MovementSortBy fromParam(String value) {
        if (value == null) return DATE;
        return switch (value.toLowerCase()) {
            case "compartment" -> COMPARTMENT;
            case "date" -> DATE;
            default -> DATE;
        };
    }
}