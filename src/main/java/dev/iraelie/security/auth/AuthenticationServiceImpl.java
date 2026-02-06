package dev.iraelie.security.auth;

import dev.iraelie.security.auth.request.AuthenticationRequest;
import dev.iraelie.security.auth.request.RefreshRequest;
import dev.iraelie.security.auth.response.AuthenticationResponse;
import dev.iraelie.security.exception.BusinessException;
import dev.iraelie.security.exception.ErrorCode;
import dev.iraelie.security.request.RegistrationRequest;
import dev.iraelie.security.security.JwtService;
import dev.iraelie.security.user.User;
import dev.iraelie.security.user.UserMapper;
import dev.iraelie.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshRequest req) {
        return null;
    }
}
