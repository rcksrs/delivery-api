package com.rcksrs.delivery.infra.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    public static final String SECURITY_NAME = "Authentication Token";

    @Bean
    public OpenAPI openAPI() {
        var contact = new Contact()
                .name("Eryck Soares")
                .url("https://www.linkedin.com/in/erycksrs/");

        var info = new Info()
                .title("Delivery API")
                .description("API do sistema de gerenciamento de entregas")
                .version("1.0.0")
                .contact(contact);

        var securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .bearerFormat("JWT")
                .scheme("bearer");

        var components = new Components()
                .addSecuritySchemes(SECURITY_NAME, securityScheme);

        return new OpenAPI().info(info).components(components);
    }
}
