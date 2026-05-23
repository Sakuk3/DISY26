FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /workspace

COPY . .
RUN mvn -B -DskipTests -pl energy-update -am package

FROM eclipse-temurin:21-jre

COPY --from=builder /workspace/energy-update/target/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
