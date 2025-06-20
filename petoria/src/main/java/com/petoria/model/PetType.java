package com.petoria.model;

public enum PetType {
    DOG, CAT, BIRD, REPTILE, FISH, FARM, OTHER;

    public static boolean isValid(String value) {
        for (PetType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    public static PetType fromString(String value) {
        return PetType.valueOf(value.toUpperCase());
    }
}