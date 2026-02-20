package dev.iraelie.security.role;

public enum Permission {
    private final String permission;
    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
