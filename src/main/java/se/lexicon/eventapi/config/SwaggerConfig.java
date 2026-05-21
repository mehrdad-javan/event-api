package se.lexicon.eventapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/// Swagger UI Configuration
///
/// Swagger UI is a tool that allows you to visualize and interact with the API's resources
/// without having any of the implementation logic in place. It's automatically generated
/// from the OpenAPI specification of this application.
///
/// ### How to use:
///
/// 1.  **Add Dependency:** Ensure the following dependency is in your `pom.xml`:
///     ```xml
///     <dependency>
///         <groupId>org.springdoc</groupId>
///         <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
///         <version>2.8.5</version>
///     </dependency>
///     ```
/// 2.  **Configuration:** Create a configuration class (like this `SwaggerConfig`)
///     annotated with `@Configuration`. Define `@Bean`s for `OpenAPI`
///     (to customize API info) and `GroupedOpenApi` (to group endpoints).
/// 3.  **Application Properties:** (Optional) Customize the path in `application.properties`:
///     ```properties
///     springdoc.swagger-ui.path=/swagger-ui.html
///     ```
/// 4.  **Access UI:** Start the application and navigate to:
///     [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

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
