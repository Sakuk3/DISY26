# DISY26

A comprehensive distributed system for energy community management, consisting of a JavaFX user interface, Spring Boot REST API, multiple microservices with RabbitMQ messaging, and PostgreSQL database integration.

## Overview

DISY26 is a distributed system project demonstrating a modern multi-layered, event-driven microservices architecture with:

- A **JavaFX desktop client** for user interactions
- A **Spring Boot REST API** backend for business logic
- **Multiple microservices** for energy production, consumption, and usage monitoring
- **Message-driven architecture** using RabbitMQ for inter-service communication
- **PostgreSQL database** with Flyway migrations for data persistence
- Shared modules for API specifications and database definitions

## Project Modules

### UI Module

A JavaFX-based user interface providing desktop application functionality for interacting with the REST API.

- **Technology**: JavaFX, Maven
- **Purpose**: Desktop client application
- **Location**: `./ui/`

### API Module

A Spring Boot REST API providing endpoints for client-server communication and business logic orchestration.

- **Technology**: Spring Boot 4.0.3, Spring Data, Maven
- **Purpose**: RESTful backend services
- **Location**: `./api/`
- **Docker**: Dockerfile included for containerization
- **Port**: 8080

### ApiSpec Module

Shared data transfer objects (DTOs) and contracts for the REST API, used by both the UI client and API server.

- **Technology**: Java, Jackson, Maven
- **Purpose**: Shared DTOs and API contracts
- **Location**: `./api-spec/`

### DB-Spec Module

Database schema management, migrations, and shared data access layer using Flyway and Spring Data JPA.

- **Technology**: PostgreSQL, Flyway, Spring Boot, Maven
- **Purpose**: Database migrations and JPA entity definitions
- **Location**: `./db-spec/`

### MQ-Spec Module

Shared message definitions and contracts for RabbitMQ messaging between microservices.

- **Technology**: RabbitMQ, Jackson, Maven
- **Purpose**: Shared message contracts for event-driven communication
- **Location**: `./mq-spec/`

### Energy Producer Microservice

Simulates energy production and publishes production data to the message queue.

- **Technology**: Spring Boot, RabbitMQ AMQP, Maven
- **Purpose**: Produces and broadcasts energy production metrics
- **Location**: `./energy-producer/`

### Energy Consumer Microservice

Simulates energy consumption and publishes consumption data to the message queue.

- **Technology**: Spring Boot, RabbitMQ AMQP, Maven
- **Purpose**: Produces and broadcasts energy consumption metrics
- **Location**: `./energy-consumer/`

### Energy Usage Service

Monitors and aggregates energy usage data from producer and consumer services.

- **Technology**: Spring Boot, RabbitMQ, PostgreSQL, Maven
- **Purpose**: Aggregate and store energy usage statistics
- **Location**: `./energy-usage/`

### Energy Update Service

Processes energy updates and maintains usage percentages in the database.

- **Technology**: Spring Boot, RabbitMQ, PostgreSQL, Maven
- **Purpose**: Update and reconcile energy usage records
- **Location**: `./energy-update/`

## Getting Started

### Prerequisites

- Java 21+
- Maven 3.6+
- Docker & Docker Compose (for running all backend services)

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

1. **Start the entire backend stack with Docker Compose**
   ```bash
   docker-compose up -d
   ```
    This will start:
    - **PostgreSQL Database** (port 5432, exposed as 15432)
    - **RabbitMQ Message Broker** (AMQP: 5672, Management: 15672)
    - **API Service** (port 8080)
    - **Energy Producer** microservice
    - **Energy Consumer** microservice
    - **Energy Usage** monitoring service
    - **Energy Update** service
    - **pgAdmin** database administration tool

   **Dashboards & Endpoints**
    - REST API: `http://localhost:8080`
    - Health Check: `http://localhost:8080/health`
    - pgAdmin: `http://localhost:15433/`
        - Email: `PGADMIN_DEFAULT_EMAIL` from `.env`
        - Password: `PGADMIN_DEFAULT_PASSWORD` from `.env`
    - RabbitMQ Management: `http://localhost:15672/`
        - Username: `RABBITMQ_DEFAULT_USER` from `.env`
        - Password: `RABBITMQ_DEFAULT_PASS` from `.env`

2. **Start the UI Application**
   ```bash
   cd ui
   mvn javafx:run
   ```
   This launches the JavaFX desktop client that connects to the API running on `http://localhost:8080`.

## Configuration

All configuration is managed through environment variables in the `.env` file:

### Database Configuration
- `POSTGRES_DB`: PostgreSQL database name
- `POSTGRES_USER`: PostgreSQL superuser username
- `POSTGRES_PASSWORD`: PostgreSQL superuser password
- `APP_DB_SCHEMA`: Application schema name
- `APP_DB_USER`: Application database user
- `APP_DB_PASSWORD`: Application database password
- `DB_HOST`: Database host (internal Docker network)
- `DB_PORT`: Database port (internal Docker network)

### Message Queue Configuration
- `RABBITMQ_HOST`: RabbitMQ host (internal Docker network)
- `RABBITMQ_DEFAULT_USER`: RabbitMQ admin username
- `RABBITMQ_DEFAULT_PASS`: RabbitMQ admin password

### Admin Tools Configuration
- `PGADMIN_DEFAULT_EMAIL`: pgAdmin login email
- `PGADMIN_DEFAULT_PASSWORD`: pgAdmin login password

### Application Configuration
- API Configuration: See `api/src/main/resources/application.properties`
- Database Migrations: Managed by Flyway (see `db-spec/src/main/resources/db/migration/`)

## Contributors

- **Fritsch Lukas** - [@Sakuk](https://github.com/Sakuk3)
- **Nagy Csaba** - [@3Xabi](https://github.com/3Xabi)
- **Thaler Lena** - [@mit-elan](https://github.com/mit-elan)

## Development Notes

### Running Without Docker

To run individual services locally for development (without Docker Compose):

1. **Start PostgreSQL and RabbitMQ** using your local installation or Docker individual containers
2. **Update configuration** in `application.properties` files to point to your local services
3. **Run services individually**:
   ```bash
   # Terminal 1 - API Service
   cd api && mvn spring-boot:run
   
   # Terminal 2 - Energy Producer
   cd energy-producer && mvn spring-boot:run
   
   # Terminal 3 - Energy Consumer
   cd energy-consumer && mvn spring-boot:run
   
   # Terminal 4 - Energy Usage
   cd energy-usage && mvn spring-boot:run
   
   # Terminal 5 - Energy Update
   cd energy-update && mvn spring-boot:run
   
   # Terminal 6 - UI
   cd ui && mvn javafx:run
   ```

### Building Docker Images Manually

```bash
# Build parent and dependencies
mvn clean install

# Build individual Docker images
docker build -f api/Dockerfile -t api:latest .
docker build -f energy-producer/Dockerfile -t energy-producer:latest .
docker build -f energy-consumer/Dockerfile -t energy-consumer:latest .
docker build -f energy-usage/Dockerfile -t energy-usage:latest .
docker build -f energy-update/Dockerfile -t energy-update:latest .
```

### Project Build Information

- **Build Tool**: Maven 3.6+
- **Java Version**: 21
- **Spring Boot Version**: 4.0.3
- **Database**: PostgreSQL 18.3
- **Message Queue**: RabbitMQ (Alpine)
- **UI Framework**: JavaFX 21.0.6
- **Containerization**: Docker & Docker Compose

## License

MIT License. See `LICENSE` for details and contributor attribution.
