# JavaFX Application
# Inherits from the base Dockerfile stages defined in docker/Dockerfile.base.
#
# The builder stage mirrors docker/Dockerfile.base's 'builder' stage (Maven + JDK 21).
# The runtime stage uses a Debian-based JRE image (instead of Alpine) because JavaFX
# native libraries require glibc, and includes Xvfb for headless display support.
#
# Build: docker build -t javafx-app:latest .
# Run:   docker run javafx-app:latest

# ---- Builder Stage (mirrors base builder) ----
FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /app

# Cache dependencies layer
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Build the application and copy dependencies
COPY src ./src
RUN mvn package -DskipTests -B

# ---- Runtime Stage (Debian-based for glibc + JavaFX native libs) ----
FROM eclipse-temurin:21-jre AS runtime
WORKDIR /app

# Install Xvfb and JavaFX native dependencies
RUN apt-get update && apt-get install -y --no-install-recommends \
        xvfb \
        libx11-6 \
        libxext6 \
        libxrender1 \
        libxtst6 \
        libxi6 \
        libgl1 \
        fontconfig \
    && rm -rf /var/lib/apt/lists/*

# Create non-root user (same pattern as base runtime)
RUN groupadd -r appgroup && useradd -r -g appgroup appuser

COPY --from=builder /app/target/javafx-app.jar javafx-app.jar
COPY --from=builder /app/target/libs ./libs
COPY docker-entrypoint.sh /usr/local/bin/docker-entrypoint.sh

RUN chmod +x /usr/local/bin/docker-entrypoint.sh \
    && chown -R appuser:appgroup /app

USER appuser

ENTRYPOINT ["docker-entrypoint.sh"]
