FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /workspace

COPY . .
RUN mvn -B -DskipTests -pl api -am package

FROM eclipse-temurin:21-jre

COPY --from=builder /workspace/api/target/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
