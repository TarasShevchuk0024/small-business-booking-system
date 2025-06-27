package com.system.SmallBusinessBookingSystem.service.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserType {

    ROLE_USER(UserTypeConstants.USER),
    ROLE_ADMIN(UserTypeConstants.ADMIN);

    private final String value;

    /**
     * Гнучкий метод для створення UserType з будь-якого формату рядка:
     * "USER", "ROLE_USER", "role_admin", тощо.
     */
    public static UserType fromValue(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("UserType value is null or empty");
        }

        String normalized = input.toUpperCase();

        // Якщо передали просто "USER" → додаємо "ROLE_"
        if (!normalized.startsWith("ROLE_")) {
            normalized = "ROLE_" + normalized;
        }

        for (UserType type : UserType.values()) {
            if (type.name().equals(normalized)) {
                return type;
            }
        }

        throw new IllegalArgumentException("No UserType with value: " + input);
    }

    public static class UserTypeConstants {
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String USER = "ROLE_USER";
    }
}