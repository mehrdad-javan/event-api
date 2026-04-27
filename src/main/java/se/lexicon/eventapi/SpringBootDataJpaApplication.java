package se.lexicon.eventapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * What is Spring Boot?
 * -------------------
 * Spring Boot is a framework that helps you create Spring applications quickly and with minimal configuration.
 *
 * It provides:
 * - Auto-configuration
 * - Embedded web server (Tomcat)
 * - Sensible default settings
 *
 * You focus on writing business logic instead of configuration.
 *
 *
 * What happens when the application runs?
 * ---------------------------------------
 * When you start a Spring Boot application:
 *
 * 1) Spring Boot scans the project for components, entities, and repositories.
 *
 * 2) All classes annotated with @Entity are detected and mapped to
 *    database tables automatically.
 *
 * 3) Spring Data JPA finds repository interfaces and creates their
 *    implementations at runtime.
 *
 * 4) A DataSource and EntityManager are configured automatically.
 *
 * 5) Hibernate creates or updates database tables based on your
 *    entity classes (depending on ddl-auto setting).
 *
 *
 * - Tables are created in the database
 * - Repositories are ready to use
 * - You can save, find, update, and delete entities without writing SQL
 */
@SpringBootApplication
public class SpringBootDataJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDataJpaApplication.class, args);
    }

}



/*
 * application.properties & application.yml
 * ----------------------------------------
 * These files are used to configure how your Spring Boot application behaves.
 *
 * They contain settings such as:
 * - Database connection details
 * - Server port
 * - Logging levels
 * - JPA / Hibernate configuration
 *
 *
 * Why do we use them?
 * ------------------
 * They separate configuration from code.
 * This means:
 * - You don’t hard-code values inside Java classes
 * - You can change behavior without recompiling the application
 *
 *
 * application.properties:
 * -----------------------
 * Uses key=value format.
 * Example:
 * server.port=8080
 *
 *
 * application.yml (YAML):
 * ----------------------
 * Uses indentation and hierarchy.
 * Example:
 * server:
 *   port: 8080
 *
 *
 * Both files do the same job.
 * Choose ONE format, not both.
 *
 *
 * Common Use Case:
 * ---------------
 * Configure database connection:
 * spring.datasource.url
 * spring.datasource.username
 * spring.datasource.password
 */
