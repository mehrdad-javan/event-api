package se.lexicon.eventapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/// Web MVC Configuration - CORS
///
/// This class configures Cross-Origin Resource Sharing (CORS) for the application.
/// CORS is a security mechanism that allows or restricts resources on a web page
/// to be requested from another domain outside the domain from which the first resource was served.
///
/// ### How to use:
///
/// 1.  **Map Paths:** Use `.addMapping("/**")` to specify which API paths should have CORS enabled.
/// 2.  **Allowed Origins:** Define which frontend origins are allowed to access the API.
///     Current allowed origins:
///     - [http://localhost:3000](http://localhost:3000) (Default React)
///     - [http://localhost:5173](http://localhost:5173) (Default Vite)
/// 3.  **Allowed Methods:** List the permitted HTTP methods (`GET`, `POST`, `PUT`, `DELETE`, `PATCH`, `OPTIONS`).
/// 4.  **Allowed Headers:** Specify which headers can be sent in the request (use `*` for all).
/// 5.  **Credentials:** Enable `.allowCredentials(true)` if the frontend needs to send cookies or use HTTP authentication.
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow all paths
                .allowedOrigins("http://localhost:3000", "http://localhost:5173" ) // Replace with your frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
