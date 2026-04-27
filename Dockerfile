# syntax=docker/dockerfile:1.7

# -------- Build stage --------
FROM maven:3.9-eclipse-temurin-25 AS build
WORKDIR /workspace

# Cache dependencies
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn -q -e -DskipTests dependency:go-offline

# Copy sources and build
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -q -e -DskipTests clean package

# -------- Runtime stage --------
FROM eclipse-temurin:25-jre
WORKDIR /app

# Copy the fat jar
COPY --from=build /workspace/target/event-api-0.0.1-SNAPSHOT.jar /app/app.jar

# Default runtime configuration (can be overridden at runtime)
ENV SPRING_PROFILES_ACTIVE=prd \
    JAVA_OPTS=""

EXPOSE 8080

# Use a slim, explicit entrypoint so JAVA_OPTS can be injected
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
