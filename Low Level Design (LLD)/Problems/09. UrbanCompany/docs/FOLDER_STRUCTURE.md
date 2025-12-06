# Complete Folder Structure - Urban Company Clone

## Full Production-Level Directory Structure

```
urban-company-clone/
│
├── services/                                      # All Microservices
│   │
│   ├── user-service/                              # User Management Service
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   ├── java/com/urbanclone/user/
│   │   │   │   │   ├── UserServiceApplication.java
│   │   │   │   │   ├── entity/
│   │   │   │   │   │   ├── User.java
│   │   │   │   │   │   ├── UserRole.java
│   │   │   │   │   │   ├── UserStatus.java
│   │   │   │   │   │   └── Address.java
│   │   │   │   │   ├── repository/
│   │   │   │   │   │   └── UserRepository.java
│   │   │   │   │   ├── service/
│   │   │   │   │   │   └── UserService.java
│   │   │   │   │   ├── controller/
│   │   │   │   │   │   └── UserController.java
│   │   │   │   │   ├── dto/
│   │   │   │   │   │   ├── UserDto.java
│   │   │   │   │   │   ├── UserRegistrationRequest.java
│   │   │   │   │   │   ├── LoginRequest.java
│   │   │   │   │   │   ├── AuthResponse.java
│   │   │   │   │   │   ├── UserUpdateRequest.java
│   │   │   │   │   │   ├── AddressDto.java
│   │   │   │   │   │   └── UserCreatedEvent.java
│   │   │   │   │   ├── security/
│   │   │   │   │   │   ├── JwtTokenProvider.java
│   │   │   │   │   │   ├── CustomUserDetailsService.java
│   │   │   │   │   │   └── JwtAuthenticationFilter.java
│   │   │   │   │   ├── config/
│   │   │   │   │   │   ├── SecurityConfig.java
│   │   │   │   │   │   └── KafkaProducerConfig.java
│   │   │   │   │   └── exception/
│   │   │   │   │       ├── UserNotFoundException.java
│   │   │   │   │       ├── UserAlreadyExistsException.java
│   │   │   │   │       ├── ErrorResponse.java
│   │   │   │   │       └── GlobalExceptionHandler.java
│   │   │   │   └── resources/
│   │   │   │       ├── application.yml
│   │   │   │       └── application-prod.yml
│   │   │   └── test/
│   │   │       └── java/com/urbanclone/user/
│   │   ├── pom.xml
│   │   └── Dockerfile
│   │
│   ├── booking-service/                           # Booking Management Service
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   ├── java/com/urbanclone/booking/
│   │   │   │   │   ├── BookingServiceApplication.java
│   │   │   │   │   ├── entity/
│   │   │   │   │   │   ├── Booking.java
│   │   │   │   │   │   ├── BookingStatus.java
│   │   │   │   │   │   ├── PaymentStatus.java
│   │   │   │   │   │   ├── PaymentMethod.java
│   │   │   │   │   │   └── BookingAddress.java
│   │   │   │   │   ├── repository/
│   │   │   │   │   │   └── BookingRepository.java
│   │   │   │   │   ├── service/
│   │   │   │   │   │   ├── BookingService.java
│   │   │   │   │   │   ├── PricingService.java
│   │   │   │   │   │   └── PartnerMatchingService.java
│   │   │   │   │   ├── controller/
│   │   │   │   │   │   └── BookingController.java
│   │   │   │   │   ├── dto/
│   │   │   │   │   │   ├── BookingDto.java
│   │   │   │   │   │   ├── BookingCreateRequest.java
│   │   │   │   │   │   ├── BookingAddressDto.java
│   │   │   │   │   │   ├── PricingDetails.java
│   │   │   │   │   │   ├── BookingCreatedEvent.java
│   │   │   │   │   │   ├── PartnerAssignedEvent.java
│   │   │   │   │   │   ├── BookingStatusChangedEvent.java
│   │   │   │   │   │   └── BookingCancelledEvent.java
│   │   │   │   │   ├── statemachine/
│   │   │   │   │   │   └── BookingStateMachine.java
│   │   │   │   │   ├── client/
│   │   │   │   │   │   ├── UserServiceClient.java
│   │   │   │   │   │   └── CatalogServiceClient.java
│   │   │   │   │   ├── config/
│   │   │   │   │   │   └── FeignClientConfig.java
│   │   │   │   │   └── exception/
│   │   │   │   │       ├── BookingNotFoundException.java
│   │   │   │   │       └── InvalidBookingStateException.java
│   │   │   │   └── resources/
│   │   │   │       └── application.yml
│   │   │   └── test/
│   │   ├── pom.xml
│   │   └── Dockerfile
│   │
│   ├── payment-service/                           # Payment Processing Service
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   ├── java/com/urbanclone/payment/
│   │   │   │   │   ├── PaymentServiceApplication.java
│   │   │   │   │   ├── entity/
│   │   │   │   │   │   ├── Payment.java
│   │   │   │   │   │   ├── PaymentStatus.java
│   │   │   │   │   │   ├── PaymentMethod.java
│   │   │   │   │   │   └── PaymentGateway.java
│   │   │   │   │   ├── repository/
│   │   │   │   │   │   └── PaymentRepository.java
│   │   │   │   │   ├── service/
│   │   │   │   │   │   └── PaymentService.java
│   │   │   │   │   ├── controller/
│   │   │   │   │   │   └── PaymentController.java
│   │   │   │   │   ├── dto/
│   │   │   │   │   │   ├── PaymentRequest.java
│   │   │   │   │   │   ├── PaymentResponse.java
│   │   │   │   │   │   └── RefundRequest.java
│   │   │   │   │   ├── strategy/
│   │   │   │   │   │   ├── PaymentGatewayStrategy.java
│   │   │   │   │   │   ├── StripePaymentStrategy.java
│   │   │   │   │   │   └── RazorpayPaymentStrategy.java
│   │   │   │   │   ├── listener/
│   │   │   │   │   │   └── BookingEventListener.java
│   │   │   │   │   └── exception/
│   │   │   │   │       └── PaymentProcessingException.java
│   │   │   │   └── resources/
│   │   │   │       └── application.yml
│   │   │   └── test/
│   │   ├── pom.xml
│   │   └── Dockerfile
│   │
│   ├── partner-service/                           # Service Provider Management
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   ├── java/com/urbanclone/partner/
│   │   │   │   │   ├── PartnerServiceApplication.java
│   │   │   │   │   ├── entity/
│   │   │   │   │   │   ├── Partner.java
│   │   │   │   │   │   ├── PartnerStatus.java
│   │   │   │   │   │   ├── PartnerVerification.java
│   │   │   │   │   │   ├── PartnerSkill.java
│   │   │   │   │   │   ├── PartnerAvailability.java
│   │   │   │   │   │   └── PartnerRating.java
│   │   │   │   │   ├── repository/
│   │   │   │   │   │   ├── PartnerRepository.java
│   │   │   │   │   │   ├── PartnerAvailabilityRepository.java
│   │   │   │   │   │   └── PartnerRatingRepository.java
│   │   │   │   │   ├── service/
│   │   │   │   │   │   ├── PartnerService.java
│   │   │   │   │   │   ├── PartnerOnboardingService.java
│   │   │   │   │   │   ├── AvailabilityService.java
│   │   │   │   │   │   └── RatingService.java
│   │   │   │   │   ├── controller/
│   │   │   │   │   │   ├── PartnerController.java
│   │   │   │   │   │   └── AvailabilityController.java
│   │   │   │   │   └── dto/
│   │   │   │   │       ├── PartnerDto.java
│   │   │   │   │       ├── PartnerRegistrationRequest.java
│   │   │   │   │       └── AvailabilitySlot.java
│   │   │   │   └── resources/
│   │   │   │       └── application.yml
│   │   │   └── test/
│   │   ├── pom.xml
│   │   └── Dockerfile
│   │
│   ├── catalog-service/                           # Service Catalog Management
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   ├── java/com/urbanclone/catalog/
│   │   │   │   │   ├── CatalogServiceApplication.java
│   │   │   │   │   ├── entity/
│   │   │   │   │   │   ├── Service.java
│   │   │   │   │   │   ├── ServiceCategory.java
│   │   │   │   │   │   ├── ServicePricing.java
│   │   │   │   │   │   └── ServiceAddOn.java
│   │   │   │   │   ├── repository/
│   │   │   │   │   │   ├── ServiceRepository.java
│   │   │   │   │   │   └── CategoryRepository.java
│   │   │   │   │   ├── service/
│   │   │   │   │   │   ├── CatalogService.java
│   │   │   │   │   │   └── PricingService.java
│   │   │   │   │   ├── controller/
│   │   │   │   │   │   └── CatalogController.java
│   │   │   │   │   └── dto/
│   │   │   │   │       ├── ServiceDto.java
│   │   │   │   │       └── CategoryDto.java
│   │   │   │   └── resources/
│   │   │   │       └── application.yml
│   │   │   └── test/
│   │   ├── pom.xml
│   │   └── Dockerfile
│   │
│   ├── notification-service/                      # Notification Service
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   ├── java/com/urbanclone/notification/
│   │   │   │   │   ├── NotificationServiceApplication.java
│   │   │   │   │   ├── entity/
│   │   │   │   │   │   ├── Notification.java
│   │   │   │   │   │   └── NotificationType.java
│   │   │   │   │   ├── service/
│   │   │   │   │   │   ├── EmailService.java
│   │   │   │   │   │   ├── SmsService.java
│   │   │   │   │   │   └── PushNotificationService.java
│   │   │   │   │   ├── listener/
│   │   │   │   │   │   └── EventListener.java
│   │   │   │   │   └── config/
│   │   │   │   │       └── TwilioConfig.java
│   │   │   │   └── resources/
│   │   │   │       └── application.yml
│   │   │   └── test/
│   │   ├── pom.xml
│   │   └── Dockerfile
│   │
│   ├── api-gateway/                               # API Gateway
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   ├── java/com/urbanclone/gateway/
│   │   │   │   │   ├── ApiGatewayApplication.java
│   │   │   │   │   ├── filter/
│   │   │   │   │   │   ├── AuthenticationFilter.java
│   │   │   │   │   │   ├── LoggingFilter.java
│   │   │   │   │   │   └── RateLimitFilter.java
│   │   │   │   │   └── config/
│   │   │   │   │       └── GatewayConfig.java
│   │   │   │   └── resources/
│   │   │   │       └── application.yml
│   │   │   └── test/
│   │   ├── pom.xml
│   │   └── Dockerfile
│   │
│   └── discovery-service/                         # Service Discovery (Eureka)
│       ├── src/
│       │   ├── main/
│       │   │   ├── java/com/urbanclone/discovery/
│       │   │   │   └── DiscoveryServiceApplication.java
│       │   │   └── resources/
│       │   │       └── application.yml
│       │   └── test/
│       ├── pom.xml
│       └── Dockerfile
│
├── common/                                        # Shared Libraries
│   ├── common-dto/
│   │   ├── src/main/java/com/urbanclone/common/dto/
│   │   │   ├── ApiResponse.java
│   │   │   ├── PageResponse.java
│   │   │   └── ErrorDto.java
│   │   └── pom.xml
│   │
│   ├── common-utils/
│   │   ├── src/main/java/com/urbanclone/common/utils/
│   │   │   ├── DateUtils.java
│   │   │   ├── StringUtils.java
│   │   │   └── ValidationUtils.java
│   │   └── pom.xml
│   │
│   └── common-security/
│       ├── src/main/java/com/urbanclone/common/security/
│       │   ├── JwtUtil.java
│       │   └── SecurityConstants.java
│       └── pom.xml
│
├── infrastructure/                                # DevOps & Infrastructure
│   ├── docker/
│   │   ├── docker-compose.yml
│   │   ├── docker-compose-prod.yml
│   │   └── .env.example
│   │
│   ├── kubernetes/
│   │   ├── namespaces/
│   │   │   └── urbanclone-namespace.yml
│   │   ├── deployments/
│   │   │   ├── user-service-deployment.yml
│   │   │   ├── booking-service-deployment.yml
│   │   │   ├── payment-service-deployment.yml
│   │   │   ├── partner-service-deployment.yml
│   │   │   ├── catalog-service-deployment.yml
│   │   │   └── notification-service-deployment.yml
│   │   ├── services/
│   │   │   ├── user-service-svc.yml
│   │   │   ├── booking-service-svc.yml
│   │   │   └── ...
│   │   ├── configmaps/
│   │   │   └── app-config.yml
│   │   ├── secrets/
│   │   │   └── app-secrets.yml
│   │   ├── ingress/
│   │   │   └── ingress.yml
│   │   └── hpa/
│   │       └── hpa-config.yml
│   │
│   ├── terraform/
│   │   ├── main.tf
│   │   ├── variables.tf
│   │   ├── outputs.tf
│   │   ├── modules/
│   │   │   ├── eks/
│   │   │   ├── rds/
│   │   │   └── vpc/
│   │   └── environments/
│   │       ├── dev/
│   │       ├── staging/
│   │       └── prod/
│   │
│   ├── monitoring/
│   │   ├── prometheus/
│   │   │   └── prometheus-config.yml
│   │   ├── grafana/
│   │   │   └── dashboards/
│   │   └── elk/
│   │       ├── elasticsearch.yml
│   │       ├── logstash.yml
│   │       └── kibana.yml
│   │
│   └── ci-cd/
│       ├── jenkins/
│       │   └── Jenkinsfile
│       ├── github-actions/
│       │   └── .github/workflows/
│       │       ├── ci.yml
│       │       └── cd.yml
│       └── gitlab-ci/
│           └── .gitlab-ci.yml
│
├── docs/                                          # Documentation
│   ├── architecture/
│   │   ├── system-architecture.md
│   │   ├── component-diagram.md
│   │   └── database-schema.md
│   │
│   ├── api/
│   │   ├── user-service-api.md
│   │   ├── booking-service-api.md
│   │   ├── payment-service-api.md
│   │   └── swagger/
│   │       └── openapi.yaml
│   │
│   ├── deployment/
│   │   ├── deployment-guide.md
│   │   ├── scaling-guide.md
│   │   └── monitoring-guide.md
│   │
│   └── development/
│       ├── setup-guide.md
│       ├── coding-standards.md
│       └── testing-guide.md
│
├── scripts/                                       # Utility Scripts
│   ├── setup/
│   │   ├── install-dependencies.sh
│   │   └── setup-local-env.sh
│   │
│   ├── database/
│   │   ├── create-databases.sql
│   │   ├── seed-data.sql
│   │   └── migration-scripts/
│   │
│   ├── deployment/
│   │   ├── deploy-all-services.sh
│   │   ├── rollback.sh
│   │   └── health-check.sh
│   │
│   └── testing/
│       ├── run-integration-tests.sh
│       └── load-test.sh
│
├── .gitignore
├── README.md
├── pom.xml                                        # Root POM
└── LICENSE

```

## Key Design Patterns Implemented

### 1. **Microservices Architecture**
- Service-per-functionality design
- Independent deployment and scaling
- Database-per-service pattern

### 2. **API Gateway Pattern**
- Centralized entry point
- Request routing and composition
- Authentication and authorization

### 3. **Service Discovery**
- Eureka for service registration
- Client-side load balancing
- Health monitoring

### 4. **Event-Driven Architecture**
- Kafka for async communication
- Event sourcing for critical workflows
- CQRS pattern for read/write separation

### 5. **State Machine Pattern**
- Booking status transitions
- Payment workflow management
- Order lifecycle management

### 6. **Strategy Pattern**
- Multiple payment gateways
- Different notification channels
- Pricing strategies

### 7. **Repository Pattern**
- Data access abstraction
- Clean separation of concerns

### 8. **Circuit Breaker Pattern**
- Resilience for inter-service calls
- Fallback mechanisms
- Fault tolerance

## Database Schema Design

### User Service DB
- users
- user_addresses
- user_preferences

### Booking Service DB
- bookings
- booking_history
- booking_audit_log

### Payment Service DB
- payments
- transactions
- refunds

### Partner Service DB
- partners
- partner_skills
- partner_availability
- partner_ratings
- partner_earnings

### Catalog Service DB
- services
- service_categories
- service_pricing
- service_addons

## Technology Stack Summary

**Backend**: Java 17, Spring Boot 3.2.0, Spring Cloud
**Databases**: PostgreSQL (primary), MongoDB (logs), Redis (cache)
**Message Queue**: Apache Kafka
**Service Discovery**: Netflix Eureka
**API Gateway**: Spring Cloud Gateway
**Caching**: Redis
**Search**: Elasticsearch
**Monitoring**: Prometheus + Grafana
**Logging**: ELK Stack
**Container**: Docker
**Orchestration**: Kubernetes
**CI/CD**: Jenkins / GitHub Actions
**Cloud**: AWS (EKS, RDS, S3)
