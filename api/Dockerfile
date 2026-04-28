# Multi-stage Dockerfile for the `api` Spring Boot module
# Builder: use Maven with Eclipse Temurin JDK 17 to build the fat jar
FROM maven:3.9.6-eclipse-temurin-17 AS builder

WORKDIR /workspace

# Copy maven wrapper and settings first to leverage cache for dependencies
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

RUN chmod +x mvnw

# Pre-download dependencies (improves cache usage)
RUN ./mvnw -B -DskipTests dependency:go-offline

# Copy sources and build
COPY src ./src
RUN ./mvnw -B -DskipTests package

# Runtime image: small JRE
FROM eclipse-temurin:17-jre

ARG JAVA_OPTS=""
EXPOSE 8080

# Copy the built jar from the builder stage. Use wildcard to avoid hardcoding name.
COPY --from=builder /workspace/target/*.jar /app/app.jar

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]

