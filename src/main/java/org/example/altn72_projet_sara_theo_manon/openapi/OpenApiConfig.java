package org.example.altn72_projet_sara_theo_manon.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    // Personnalisation de la doc
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI().info(new Info()
                .title("ALTN72 - API Documentation")
                .description("Documentation Swagger de l’application")
                .version("v1.0"));
    }

    // Permet de grouper les routes
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .pathsToMatch("/api/**")
                .build();
    }
}
