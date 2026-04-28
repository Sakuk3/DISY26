# DISY26

A multi-module Java application consisting of a JavaFX user interface, Spring Boot REST API, and shared common module for data management.

## Overview

DISY26 is a distributed system project that demonstrates a modern multi-layered architecture with:
- A **JavaFX desktop client** for user interactions
- A **Spring Boot REST API** backend for business logic
- A **Common module** for shared data structures and database access

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

### Common Module
Shared data structures, DTOs, and database access layer used by both UI and API modules.

- **Technology**: Java, Maven
- **Purpose**: Shared utilities, models, and data access objects
- **Location**: `./common/`

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
   This will start the entire backend stack, including the database and API server. The API will then be available at `http://localhost:8080`.

2. **Start the UI Application**
   ```bash
   cd ui
   mvn javafx:run
   ```
   This launches the JavaFX desktop client.

## Configuration

- **API Configuration**: See `api/src/main/resources/application.properties`

## Contributors

- **Fritsch Lukas** - [@Sakuk](https://github.com/Sakuk3)
- **Nagy Csaba**
- **Thaler Lena** - [@mit-elan](https://github.com/mit-elan)

## Notes

- The project uses Maven as the build tool
- Docker Compose is used for containerized services
- Modules are interconnected through the common module for shared data structures
