# Spring Boot Application
# Inherits from the base Dockerfile stages defined in docker/Dockerfile.base.
#
# The builder stage mirrors docker/Dockerfile.base's 'builder' stage (Maven + JDK 21).
# The runtime stage mirrors docker/Dockerfile.base's 'runtime' stage (JRE Alpine).
#
# Build: docker build -t springboot-app:latest .
# Run:   docker run -p 8080:8080 springboot-app:latest

# ---- Builder Stage (mirrors base builder) ----
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Cache dependencies layer
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Build the application
COPY src ./src
RUN mvn package -DskipTests -B

# ---- Runtime Stage (mirrors base runtime) ----
FROM eclipse-temurin:21-jre-alpine AS runtime
WORKDIR /app

RUN apk add --no-cache curl tzdata

# Create non-root user (same as base runtime)
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

COPY --from=builder /app/target/*.jar app.jar

USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
