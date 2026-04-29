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
COPY ui ./ui
COPY api ./api

RUN chmod +x mvnw

# Build the reactor and the api module (with common as a reactor dependency)
# The api module repackages itself during `package`, producing the executable jar.
RUN ./mvnw -B -DskipTests -pl api -am package

# Runtime image: small JRE
FROM eclipse-temurin:21-jre

ARG JAVA_OPTS=""
EXPOSE 8080

# Copy the executable Spring Boot jar from the builder stage.
COPY --from=builder /workspace/api/target/api-0.0.1-SNAPSHOT.jar /app/app.jar

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]

