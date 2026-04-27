package se.lexicon.eventapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Event Management API")
                        .version("1.0.0")
                        .description("This API provides endpoints for managing users, their profiles, and events. " +
                                "It supports full CRUD operations for events and user registrations.")
                        /*.contact(new Contact()
                                .name("Lexicon Support")
                                .email("")
                                .url("#"))*/
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("event-management-public")
                .pathsToMatch("/api/v1/**", "/api/demo/**")
                .build();
    }
}
