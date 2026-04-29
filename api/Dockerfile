# Multi-stage Dockerfile for the `api` Spring Boot module
# Builder: use Maven with Eclipse Temurin JDK 21 to build the fat jar
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /workspace

# Copy maven wrapper and settings first to leverage cache for dependencies
COPY api/mvnw .
COPY api/.mvn .mvn
COPY pom.xml .

# Copy all modules for multi-module build
COPY common ./common
COPY api ./api

RUN chmod +x mvnw

# Build the reactor and the api module (with common as a reactor dependency)
RUN ./mvnw -B -DskipTests package -pl api -am

# Runtime image: small JRE
FROM eclipse-temurin:21-jre

ARG JAVA_OPTS=""
EXPOSE 8080

# Copy the built jar from the builder stage. Use wildcard to avoid hardcoding name.
COPY --from=builder /workspace/api/target/*.jar /app/app.jar

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]

