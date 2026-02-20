package dev.iraelie.security.role;

import lombok.Getter;

@Getter
public enum Permission {
    private final String permission;
    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
