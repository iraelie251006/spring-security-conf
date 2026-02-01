package dev.iraelie.security.user;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;
    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;
    @Column(name = "PHONE_NUMBER", unique = true, nullable = false)
    private String phoneNumber;
    @Column(name = "PASSWORD", nullable = false)
    private String password;
    @Column(name = "DATE_OF_BIRTH")
    private LocalDate dateOfBirth;
    @Column(name = "IS_ENABLED")
    private boolean enabled;
    @Column(name = "IS_ACCOUNT_LOCKED")
    private boolean locked;
    @Column(name = "IS_CRENDETIAL_EXPIRED")
    private boolean credentialsExpired;
    @Column(name = "IS_EMAIL_VERIFIED")
    private boolean emailVerified;
    @Column(name = "PROFILE_PICTURE_URL")
    private String profilePictureUrl;
    @Column(name = "IS_PHONE_VERIFIED")
    private boolean phoneVerified;
    @CreatedDate
    @Column(name = "CREATED_DATE", updatable = false, nullable = false)
    private LocalDateTime createdDate;
    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE", insertable = false)
    private LocalDateTime lastModifiedDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    @NonNull
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }
}
