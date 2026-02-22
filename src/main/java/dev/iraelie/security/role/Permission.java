package dev.iraelie.security.role;

import lombok.Getter;

@Getter
public enum Permission {
    // BLOG PERMISSIONS
    BLOG_READ("blog:read"),               // Anyone can read blogs
    BLOG_CREATE("blog:create"),           // Can write new blog posts
    BLOG_EDIT_OWN("blog:edit:own"),       // Can edit ONLY their own posts
    BLOG_EDIT_ANY("blog:edit:any"),       // Can edit ANY author's post
    BLOG_DELETE_OWN("blog:delete:own"),   // Can delete ONLY their own posts
    BLOG_DELETE_ANY("blog:delete:any"),   // Can delete ANY post
    BLOG_PUBLISH("blog:publish");

    private final String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
