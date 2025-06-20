package com.petoria.model;

public enum ServiceType {
    VET, GROOMING, PET_SHOP, SHELTER, MARKET, OTHER;

    public static boolean isValid(String value) {
        for (ServiceType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    public static ServiceType fromString(String value) {
        return ServiceType.valueOf(value.toUpperCase());
    }
}
