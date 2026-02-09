package dev.iraelie.security.config;

import dev.iraelie.security.exception.BusinessException;
import dev.iraelie.security.exception.ErrorCode;
import dev.iraelie.security.user.User;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static dev.iraelie.security.exception.ErrorCode.USER_NOT_FOUND;

@NullMarked
public class ApplicationAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        User user = (User) authentication.getPrincipal();

        if (user == null) {
            throw new BusinessException(USER_NOT_FOUND);
        }

        return Optional.ofNullable(user.getId());
    }
}
