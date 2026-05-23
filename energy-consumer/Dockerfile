FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /workspace

COPY . .
RUN mvn -B -DskipTests -pl energy-consumer -am package

FROM eclipse-temurin:21-jre

COPY --from=builder /workspace/energy-consumer/target/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
