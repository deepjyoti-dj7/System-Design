# Urban Company Clone - Microservices Architecture

## Overview
Production-grade home services platform built with Java Spring Boot microservices architecture.

## Architecture
- **Microservices**: Independent, scalable services
- **API Gateway**: Centralized entry point with routing and authentication
- **Service Discovery**: Eureka for service registration
- **Event-Driven**: Kafka for async communication
- **Caching**: Redis for performance optimization
- **Database**: PostgreSQL (primary), MongoDB (logs/analytics)

## Services
1. **user-service**: User management, authentication
2. **booking-service**: Booking lifecycle management
3. **payment-service**: Payment processing and transactions
4. **partner-service**: Service provider management
5. **catalog-service**: Services catalog and pricing
6. **notification-service**: Email, SMS, push notifications
7. **api-gateway**: API Gateway with Spring Cloud Gateway
8. **discovery-service**: Eureka service discovery

## Technology Stack
- Java 17+
- Spring Boot 3.x
- Spring Cloud
- PostgreSQL
- MongoDB
- Redis
- Kafka
- Docker & Kubernetes
- Elasticsearch (search)

## Project Structure
```
urban-company-clone/
├── services/                 # Microservices
├── common/                   # Shared libraries
├── infrastructure/           # DevOps configs
├── docs/                     # Documentation
└── scripts/                  # Utility scripts
```

## Getting Started
See individual service README files for setup instructions.
