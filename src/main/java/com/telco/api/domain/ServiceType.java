package com.telco.api.domain;

public enum ServiceType {
    INTERNET,
    TELEFON,
    TELEVIZIJA;

    public static ServiceType fromSeedValue(String value) {
        if (value == null) throw new IllegalArgumentException("service type is null");
        return switch (value.trim().toLowerCase()) {
            case "internet" -> INTERNET;
            case "telefon" -> TELEFON;
            case "televizija" -> TELEVIZIJA;
            default -> throw new IllegalArgumentException("Unknown service type: " + value);
        };
    }
}
