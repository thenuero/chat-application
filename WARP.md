# WARP.md

This file provides guidance to WARP (warp.dev) when working with code in this repository.

## Project Overview

This is a microservices-based chat application built with Spring Boot 3.5.4 and Java 17. The system is designed for practicing microservice architecture patterns and consists of three main services under the `com.talkit` package.

## Architecture

The application follows a microservices architecture with three distinct services:

- **Users Service** (`services/users/`) - User management with PostgreSQL database, Spring Security, and JPA
- **Chat Service** (`services/chat/`) - Message handling with MongoDB, WebSocket support, and Kafka integration  
- **Presence Service** (`services/presence/`) - User presence tracking with Redis

Each service is independently deployable and has its own data store, following microservice best practices.

## Technology Stack

- **Framework**: Spring Boot 3.5.4
- **Java Version**: 17
- **Build Tool**: Gradle with multi-module setup
- **Databases**: PostgreSQL (Users), MongoDB (Chat), Redis (Presence)  
- **Messaging**: Apache Kafka (Chat service)
- **Security**: Spring Security (Users service)
- **Testing**: JUnit 5

## Common Development Commands

### Building the Project
```bash
# Build all services
./gradlew build

# Build specific service
./gradlew :services:chat:build
./gradlew :services:users:build  
./gradlew :services:presence:build
```

### Running Services
```bash
# Run specific service
./gradlew :services:users:bootRun
./gradlew :services:chat:bootRun
./gradlew :services:presence:bootRun
```

### Testing
```bash
# Run all tests
./gradlew test

# Run tests for specific service
./gradlew :services:users:test
./gradlew :services:chat:test
./gradlew :services:presence:test

# Run single test class
./gradlew :services:users:test --tests "UserServiceTests"
```

### Database Setup
The Users service requires PostgreSQL setup:
```sql
-- Create database and user
CREATE DATABASE chat_app_users;
CREATE USER chatuser WITH ENCRYPTED PASSWORD 'chatpass';
GRANT ALL PRIVILEGES ON DATABASE chat_app_users TO chatuser;
```

## Service Details

### Users Service (Port 8080)
- **Main Class**: `com.talkit.users.UsersApplication`
- **Database**: PostgreSQL (`chat_app_users`)
- **Dependencies**: Spring Security, JPA, Lombok
- **Endpoints**: `/users/register`, `/users/{id}`
- **Architecture**: Standard layered architecture (Controller → Service → Repository → Entity)

### Chat Service
- **Main Class**: `com.talkit.chat.ChatApplication`  
- **Database**: MongoDB
- **Dependencies**: WebSocket, Kafka, Spring Data MongoDB
- **Purpose**: Real-time messaging and message persistence

### Presence Service  
- **Main Class**: `com.talkit.presence.PresenceApplication`
- **Database**: Redis
- **Dependencies**: Spring Data Redis
- **Purpose**: Track user online/offline status

## Code Organization

Services follow Spring Boot conventions:
- `src/main/java/com/talkit/{service}/` - Main source code
- `src/main/resources/` - Configuration files (application.properties)
- `src/test/java/` - Test classes

The Users service demonstrates the full layered architecture pattern:
- `model/` - JPA entities (User.java)
- `controller/` - REST controllers (UserController.java) 
- `service/` - Business logic (UserService.java)
- `repository/` - Data access (UserRepository.java)
- `configuration/` - Spring configuration (SecurityConfig.java)

## Development Notes

- All services use constructor injection for dependencies
- The Users service has security disabled for development (`SecurityConfig.java`)
- Password handling is not implemented yet (stored as plain text)
- Database DDL is set to `update` mode for the Users service
- Lombok is used for reducing boilerplate code (Users service only)
- JUnit 5 is configured for all services

## External Dependencies

Ensure the following are running for full functionality:
- PostgreSQL (Users service)
- MongoDB (Chat service)
- Redis (Presence service)  
- Apache Kafka (Chat service)
