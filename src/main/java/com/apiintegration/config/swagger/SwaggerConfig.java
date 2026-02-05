package com.apiintegration.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.*;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Payment Service API",
                version = "1.0",
                description = "Secure APIs with JWT, Refresh Token & OAuth2 (Google)",
                contact = @Contact(
                        name = "Payment Team",
                        email = "support@payment.com"
                )
        ),
        security = {
                @SecurityRequirement(name = "bearerAuth"),
                @SecurityRequirement(name = "googleOAuth")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
@SecurityScheme(
        name = "googleOAuth",
        type = SecuritySchemeType.OAUTH2,
        flows = @OAuthFlows(
                authorizationCode = @OAuthFlow(
                        authorizationUrl = "https://accounts.google.com/o/oauth2/v2/auth",
                        tokenUrl = "https://oauth2.googleapis.com/token",
                        scopes = {
                                @OAuthScope(name = "openid", description = "OpenID"),
                                @OAuthScope(name = "profile", description = "Profile"),
                                @OAuthScope(name = "email", description = "Email")
                        }
                )
        )
)
public class SwaggerConfig {
}

