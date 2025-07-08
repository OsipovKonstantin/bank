package ru.neoflex.calculator.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Bank API",
                version = "1.0",
                description = "Documentation for bank application",
                contact = @Contact(
                        name = "Osipov Konstantin",
                        email = "osipowko@gmail.com"
                )
        )
)
@Configuration
public class OpenApiConfig {
}
