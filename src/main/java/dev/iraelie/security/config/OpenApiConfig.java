package dev.iraelie.security.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Spring security JWT Asymmetric encryption",
                        email = "niyubwayoiraelie5777@gmail.com",
                        url = "https://iraelie.vercel.app"
                ),
                description = "OpenAPI documentation for spring security project",
                title = "JWT Asymmetricc encryption",
                version = "1.0",
                license = @License(
                        name = "MIT",
                        url = "https://iraelie.vercel.app"
                ),
                termsOfService = "https://iraelie.vercel.app"
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Development environment"
                )
        },
        security = {
                @SecurityRequirement(
                        name = "bearerAuth"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        description = "JWT auth description",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
