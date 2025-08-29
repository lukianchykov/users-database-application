package com.lukianchykov.usersdatabaseapplication.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User Aggregation Service API")
                        .version("1.0.0")
                        .description("REST API for aggregating user data from multiple databases")
                        .contact(new Contact()
                                .name("Development Team")
                                .email("dev@example.com")
                        )
                );
    }
}