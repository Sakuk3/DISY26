# DISY26

A multi-module Java application consisting of a JavaFX user interface, Spring Boot REST API, and shared common module
for data management.

## Overview

DISY26 is a distributed system project that demonstrates a modern multi-layered architecture with:

- A **JavaFX desktop client** for user interactions
- A **Spring Boot REST API** backend for business logic
- A **ApiSpec module** for shared data structures and database access

## Project Modules

### UI Module

A JavaFX-based user interface providing desktop application functionality for interacting with the API.

- **Technology**: JavaFX, Maven
- **Purpose**: Desktop client application
- **Location**: `./ui/`

### API Module

A Spring Boot REST API providing backend services and business logic.

- **Technology**: Spring Boot, Spring Data, Maven
- **Purpose**: RESTful backend services
- **Location**: `./api/`
- **Docker**: Dockerfile included for containerization

### ApiSpec Module

DTOs for the Rest API.

- **Technology**: Java, Maven
- **Purpose**: Dtos for the Rest API
- **Location**: `./api-spec/`

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.6+
- Docker & Docker Compose (for running the database)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Sakuk3/DISY26
   cd DISY26
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

### Running the Application

1. **Start the backend with Docker Compose**
   ```bash
   docker-compose up -d
   ```
   This will start the entire backend stack, including the database and API server. The API will then be available at
   `http://localhost:8080`.

   **Dashboards**
    - pgAdmin: `http://localhost:15433/`
        - Login uses `PGADMIN_DEFAULT_EMAIL` / `PGADMIN_DEFAULT_PASSWORD` from `.env`.
    - RabbitMQ management: `http://localhost:15672/`
        - Login uses `RABBITMQ_DEFAULT_USER` / `RABBITMQ_DEFAULT_PASS` from `.env`.

2. **Start the UI Application**
   ```bash
   cd ui
   mvn javafx:run
   ```
   This launches the JavaFX desktop client.

## Configuration

- **Database Schema/User**: `POSTGRES_DB`, `APP_DB_SCHEMA`, `APP_DB_USER`, `APP_DB_PASSWORD` in `.env` (used by
  `db/init.sql` and API datasource)
- **API Configuration**: See `api/src/main/resources/application.properties`
- **pgAdmin Credentials**: Set `PGADMIN_DEFAULT_EMAIL` and `PGADMIN_DEFAULT_PASSWORD` in `.env`
- **RabbitMQ Management Credentials**: Set `RABBITMQ_DEFAULT_USER` and `RABBITMQ_DEFAULT_PASS` in `.env`

## Contributors

- **Fritsch Lukas** - [@Sakuk](https://github.com/Sakuk3)
- **Nagy Csaba** - [@3Xabi](https://github.com/3Xabi)
- **Thaler Lena** - [@mit-elan](https://github.com/mit-elan)

## Notes

- The project uses Maven as the build tool
- Docker Compose is used for containerized services

## License

MIT License. See `LICENSE` for details and contributor attribution.
