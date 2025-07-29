package com.breudes.gastrohub.model.enums;

public enum UserRole {
    ADMIN("Admin"),
    CLIENT("Client"),
    OWNER("Owner");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "description='" + description + '\'' +
                '}';
    }
}
