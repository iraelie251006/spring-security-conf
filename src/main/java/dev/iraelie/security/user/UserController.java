package dev.iraelie.security.user;

import dev.iraelie.security.exception.BusinessException;
import dev.iraelie.security.request.ChangePasswordRequest;
import dev.iraelie.security.request.ProfileUpdateRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import static dev.iraelie.security.exception.ErrorCode.ERR_USER_DISABLED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User API endpoints")
public class UserController {
    private final UserServiceImpl userService;

    @PatchMapping("/me")
    @ResponseStatus(code = NO_CONTENT)
    public void updateProfileInfo(
            @RequestBody
            @Valid
            final ProfileUpdateRequest request,
            final Authentication principal
            ) {
        userService.updateProfileInfo(request, getUserId(principal));
    }

    @PostMapping("/me/password")
    @ResponseStatus(code = NO_CONTENT)
    public void changePassword(
            @RequestBody
            @Valid
            final ChangePasswordRequest request,
            final Authentication principal
            ) {
        userService.changePassword(request, getUserId(principal));
    }

    @PatchMapping("/me/deactivate")
    @ResponseStatus(code = NO_CONTENT)
    public void deActivateAccount(final Authentication principal) {
        userService.deactivateAccount(getUserId(principal));
    }

    @PatchMapping("/me/reactivate")
    @ResponseStatus(code = NO_CONTENT)
    public void reactivateAccount(final Authentication principal) {
        userService.reactivateAccount(getUserId(principal));
    }

    @DeleteMapping("/me")
    @ResponseStatus(code = NO_CONTENT)
    public void deleteAccount(final Authentication principal) {
        userService.deleteAccount(getUserId(principal));
    }

    private String getUserId(final Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException(ERR_USER_DISABLED);
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof User user) {
            return user.getId();
        }

        throw new BusinessException(ERR_USER_DISABLED);
    }
}
