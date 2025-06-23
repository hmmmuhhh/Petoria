package com.petoria.model;

public enum NoticeType {
    LOST, FOUND;

    public static boolean isValid(String value) {
        for (NoticeType type : values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    public static NoticeType fromString(String value) {
        return NoticeType.valueOf(value.toUpperCase());
    }
}
