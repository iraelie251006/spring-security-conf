package dev.iraelie.security.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class User {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private LocalDate dateOfBirth;
    private boolean enabled;
    private boolean locked;
    private boolean credentialsExpired;
    private boolean emailVerified;
    private String profilePictureUrl;
    private boolean phoneVerified;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

}
