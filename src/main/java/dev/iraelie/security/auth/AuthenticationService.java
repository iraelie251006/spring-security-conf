package dev.iraelie.security.auth;

import dev.iraelie.security.auth.request.AuthenticationRequest;
import dev.iraelie.security.auth.request.RefreshRequest;
import dev.iraelie.security.auth.response.AuthenticationResponse;
import dev.iraelie.security.request.RegistrationRequest;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest request);

    void register(RegistrationRequest request);

    AuthenticationResponse refreshToken(RefreshRequest req);
}
