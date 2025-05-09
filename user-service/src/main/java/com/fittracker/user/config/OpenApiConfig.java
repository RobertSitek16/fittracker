package com.fittracker.user.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI fitTrackerOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FitTracker API")
                        .description("REST API for managing users in the FitTracker app")
                        .version("1.0.0"));
    }
}
