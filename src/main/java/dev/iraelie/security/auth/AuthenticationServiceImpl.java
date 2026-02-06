package dev.iraelie.security.auth;

import dev.iraelie.security.auth.request.AuthenticationRequest;
import dev.iraelie.security.auth.request.RefreshRequest;
import dev.iraelie.security.auth.response.AuthenticationResponse;
import dev.iraelie.security.exception.BusinessException;
import dev.iraelie.security.exception.ErrorCode;
import dev.iraelie.security.request.RegistrationRequest;
import dev.iraelie.security.role.Role;
import dev.iraelie.security.role.RoleRepository;
import dev.iraelie.security.security.JwtService;
import dev.iraelie.security.user.User;
import dev.iraelie.security.user.UserMapper;
import dev.iraelie.security.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static dev.iraelie.security.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService{
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = (User) auth.getPrincipal();

        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND);
        }

        String token = jwtService.generateAccessToken(user.getUsername());
        String refreshToken = jwtService.generateRefreshToken(user.getUsername());

        String tokenType = "Bearer";

        return AuthenticationResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken)
                .tokenType(tokenType)
                .build();
    }

    @Override
    public void register(RegistrationRequest request) {
        checkUserEmail(request.getEmail());
        checkUserPhoneNumber(request.getPhoneNumber());
        checkPasswords(request.getPassword(), request.getConfirmPassword());

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new EntityNotFoundException("Role user does not exist"));

        List<Role> roles = new ArrayList<>();
        roles.add(userRole);

        User user = userMapper.toUser(request);
        user.setRoles(roles);
        userRepository.save(user);

        List<User> users = new ArrayList<>();
        users.add(user);
        userRole.setUsers(users);

        roleRepository.save(userRole);
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest request) {
        String newAccessToken = jwtService.refreshAccessToken(request.getRefreshToken());
        String tokenType = "Bearer";
        return AuthenticationResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(request.getRefreshToken())
                .tokenType(tokenType)
                .build();
    }

    private void checkPasswords(@NotBlank(message = "VALIDATION.REGISTRATION.PASSWORD.BLANK") @Size(min = 8,
            max = 72,
            message = "VALIDATION.REGISTRATION.PASSWORD.SIZE"
    ) @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*\\W).*$",
            message = "VALIDATION.REGISTRATION.PASSWORD.WEAK"
    ) String password, @NotBlank(message = "VALIDATION.REGISTRATION.CONFIRM_PASSWORD.BLANK") @Size(min = 8,
            max = 72,
            message = "VALIDATION.REGISTRATION.CONFIRM_PASSWORD.SIZE"
    ) String confirmPassword) {
        if (password == null || !password.equals(confirmPassword)) {
            throw new BusinessException(PASSWORD_MISMATCH);
        }
    }

    private void checkUserPhoneNumber(@NotBlank(message = "VALIDATION.REGISTRATION.PHONE.BLANK") @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$",
            message = "VALIDATION.REGISTRATION.PHONE.FORMAT"
    ) String phoneNumber) {
        boolean phoneNumberExists = userRepository.existsByPhoneNumber(phoneNumber);

        if (phoneNumberExists) {
            throw new BusinessException(PHONE_ALREADY_EXISTS);
        }
    }

    private void checkUserEmail(@NotBlank(message = "VALIDATION.REGISTRATION.EMAIL.BLANK") @Email(message = "VALIDATION.REGISTRATION.EMAIL.FORMAT") String email) {
        boolean emailExists = userRepository.existsByEmailIgnoreCase(email);

        if (emailExists) {
            throw new BusinessException(EMAIL_ALREADY_EXISTS);
        }
    }
}
