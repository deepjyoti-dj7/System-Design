# System Architecture - Urban Company Clone

## High-Level Architecture

```
┌─────────────────────────────────────────────────────────────────────┐
│                          Mobile Apps & Web                          │
│                    (React, React Native, iOS, Android)              │
└─────────────────────────────────────────────────────────────────────┘
                                   │
                                   ↓
┌─────────────────────────────────────────────────────────────────────┐
│                          API Gateway (8080)                          │
│              (Authentication, Rate Limiting, Routing)                │
└─────────────────────────────────────────────────────────────────────┘
                                   │
                  ┌────────────────┴────────────────┐
                  │    Service Discovery (Eureka)    │
                  └────────────────┬────────────────┘
                                   │
        ┌──────────────────────────┴──────────────────────────┐
        │                                                      │
┌───────▼──────────┐  ┌──────────────┐  ┌───────────────────┐ │
│  User Service    │  │   Booking    │  │     Payment       │ │
│     (8081)       │  │   Service    │  │     Service       │ │
│                  │  │    (8082)    │  │      (8083)       │ │
│  - Registration  │  │  - Create    │  │  - Process        │ │
│  - Authentication│  │  - Manage    │  │  - Refunds        │ │
│  - Profile Mgmt  │  │  - Track     │  │  - Gateways       │ │
└───────┬──────────┘  └──────┬───────┘  └────────┬──────────┘ │
        │                    │                   │             │
┌───────▼──────────┐  ┌──────▼───────┐  ┌───────▼───────────┐ │
│  Partner Service │  │   Catalog    │  │  Notification     │ │
│     (8084)       │  │   Service    │  │    Service        │ │
│                  │  │    (8085)    │  │     (8086)        │ │
│  - Onboarding    │  │  - Services  │  │  - Email          │ │
│  - Availability  │  │  - Categories│  │  - SMS            │ │
│  - Ratings       │  │  - Pricing   │  │  - Push           │ │
└───────┬──────────┘  └──────┬───────┘  └────────┬──────────┘
        │                    │                   │
        └────────────────────┴───────────────────┘
                             │
                ┌────────────┴────────────┐
                │                         │
        ┌───────▼────────┐       ┌───────▼────────┐
        │  Apache Kafka  │       │     Redis      │
        │  Message Queue │       │  Cache Layer   │
        └───────┬────────┘       └────────────────┘
                │
                ↓
    ┌───────────────────────┐
    │   Event Consumers     │
    │  - Notifications      │
    │  - Analytics          │
    │  - Audit Logs         │
    └───────────────────────┘
```

## Data Flow Architecture

### 1. Booking Flow
```
Customer → API Gateway → Booking Service
                              ↓
                    Check Availability
                              ↓
                    Calculate Pricing
                              ↓
                    Create Booking (DB)
                              ↓
                    Publish Event (Kafka)
                              ↓
            ┌─────────────────┴─────────────────┐
            ↓                                   ↓
    Partner Service                    Notification Service
    (Assign Partner)                   (Send Confirmation)
            ↓
    Update Booking
            ↓
    Notify Customer
```

### 2. Payment Flow
```
Booking Completed → Payment Service
                          ↓
                 Select Gateway (Strategy)
                          ↓
            ┌─────────────┴─────────────┐
            ↓                           ↓
    Stripe Gateway              Razorpay Gateway
            ↓                           ↓
            └─────────────┬─────────────┘
                          ↓
                  Process Payment
                          ↓
                  Update Status
                          ↓
            Publish Payment Event (Kafka)
                          ↓
            ┌─────────────┴─────────────┐
            ↓                           ↓
    Booking Service              Partner Service
    (Update Status)              (Release Earnings)
```

### 3. Partner Matching Algorithm
```
New Booking Created
        ↓
Query Available Partners
        ↓
Filter By:
  - Service Skills
  - Geographic Location
  - Availability
  - Rating (> 4.0)
        ↓
Calculate Distance
        ↓
Sort By:
  1. Distance
  2. Rating
  3. Completion Rate
        ↓
Assign Top Partner
        ↓
Send Notification
```

## Database Schema

### User Service Database
```sql
-- users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    phone_number VARCHAR(20) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    street VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(100),
    zip_code VARCHAR(20),
    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8),
    profile_image_url VARCHAR(500),
    email_verified BOOLEAN DEFAULT FALSE,
    phone_verified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    last_login_at TIMESTAMP,
    version BIGINT
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_phone ON users(phone_number);
CREATE INDEX idx_users_status ON users(status);
```

### Booking Service Database
```sql
-- bookings table
CREATE TABLE bookings (
    id BIGSERIAL PRIMARY KEY,
    booking_number VARCHAR(50) UNIQUE NOT NULL,
    customer_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    partner_id BIGINT,
    scheduled_at TIMESTAMP NOT NULL,
    status VARCHAR(30) NOT NULL,
    street VARCHAR(255),
    city VARCHAR(100),
    state VARCHAR(100),
    country VARCHAR(100),
    zip_code VARCHAR(20),
    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8),
    base_price DECIMAL(10, 2) NOT NULL,
    discount DECIMAL(10, 2) DEFAULT 0,
    tax DECIMAL(10, 2) DEFAULT 0,
    total_price DECIMAL(10, 2) NOT NULL,
    customer_notes TEXT,
    cancellation_reason TEXT,
    cancelled_at TIMESTAMP,
    completed_at TIMESTAMP,
    partner_assigned_at TIMESTAMP,
    partner_arrived_at TIMESTAMP,
    service_started_at TIMESTAMP,
    payment_status VARCHAR(30),
    payment_method VARCHAR(30),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    version BIGINT
);

CREATE INDEX idx_bookings_customer ON bookings(customer_id);
CREATE INDEX idx_bookings_partner ON bookings(partner_id);
CREATE INDEX idx_bookings_status ON bookings(status);
CREATE INDEX idx_bookings_scheduled ON bookings(scheduled_at);
CREATE INDEX idx_bookings_number ON bookings(booking_number);
```

### Payment Service Database
```sql
-- payments table
CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,
    transaction_id VARCHAR(100) UNIQUE NOT NULL,
    booking_id BIGINT NOT NULL,
    customer_id BIGINT NOT NULL,
    partner_id BIGINT,
    amount DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(30) NOT NULL,
    status VARCHAR(30) NOT NULL,
    gateway VARCHAR(30),
    gateway_transaction_id VARCHAR(255),
    gateway_response TEXT,
    failure_reason TEXT,
    processed_at TIMESTAMP,
    refunded_at TIMESTAMP,
    refunded_amount DECIMAL(10, 2),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    version BIGINT
);

CREATE INDEX idx_payments_booking ON payments(booking_id);
CREATE INDEX idx_payments_customer ON payments(customer_id);
CREATE INDEX idx_payments_status ON payments(status);
CREATE INDEX idx_payments_txn ON payments(transaction_id);
```

### Partner Service Database
```sql
-- partners table
CREATE TABLE partners (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    business_name VARCHAR(255) NOT NULL,
    status VARCHAR(30) NOT NULL,
    average_rating DECIMAL(3, 2),
    total_completed_bookings INTEGER DEFAULT 0,
    profile_image_url VARCHAR(500),
    bio TEXT,
    years_of_experience INTEGER,
    city VARCHAR(100),
    state VARCHAR(100),
    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8),
    radius_km DECIMAL(5, 2) DEFAULT 10.0,
    is_available BOOLEAN DEFAULT TRUE,
    identity_verified BOOLEAN DEFAULT FALSE,
    address_verified BOOLEAN DEFAULT FALSE,
    background_check_completed BOOLEAN DEFAULT FALSE,
    certificates_verified BOOLEAN DEFAULT FALSE,
    verified_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    version BIGINT
);

-- partner_skills table
CREATE TABLE partner_skills (
    partner_id BIGINT NOT NULL,
    service_id BIGINT NOT NULL,
    PRIMARY KEY (partner_id, service_id)
);

-- partner_availability table
CREATE TABLE partner_availability (
    id BIGSERIAL PRIMARY KEY,
    partner_id BIGINT NOT NULL,
    day_of_week VARCHAR(20) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    is_available BOOLEAN DEFAULT TRUE
);

CREATE INDEX idx_partners_status ON partners(status);
CREATE INDEX idx_partners_location ON partners(latitude, longitude);
CREATE INDEX idx_partner_skills_service ON partner_skills(service_id);
```

### Catalog Service Database
```sql
-- service_categories table
CREATE TABLE service_categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    description TEXT,
    icon_url VARCHAR(500),
    parent_category_id BIGINT,
    display_order INTEGER,
    is_active BOOLEAN DEFAULT TRUE
);

-- services table
CREATE TABLE services (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    category_id BIGINT NOT NULL,
    base_price DECIMAL(10, 2) NOT NULL,
    duration_minutes INTEGER,
    image_url VARCHAR(500),
    status VARCHAR(20) NOT NULL,
    is_popular BOOLEAN DEFAULT FALSE,
    average_rating DECIMAL(3, 2),
    total_bookings INTEGER DEFAULT 0,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES service_categories(id)
);

-- service_features table
CREATE TABLE service_features (
    service_id BIGINT NOT NULL,
    feature VARCHAR(500) NOT NULL
);

CREATE INDEX idx_services_category ON services(category_id);
CREATE INDEX idx_services_status ON services(status);
CREATE INDEX idx_services_popular ON services(is_popular);
```

## API Endpoints

### User Service (Port 8081)
- POST   `/api/v1/users/register` - Register new user
- POST   `/api/v1/users/login` - User login
- GET    `/api/v1/users/{userId}` - Get user by ID
- PUT    `/api/v1/users/{userId}` - Update user
- DELETE `/api/v1/users/{userId}` - Delete user
- GET    `/api/v1/users/search` - Search users

### Booking Service (Port 8082)
- POST   `/api/v1/bookings` - Create booking
- GET    `/api/v1/bookings/{bookingId}` - Get booking
- GET    `/api/v1/bookings/customer/{customerId}` - Get customer bookings
- GET    `/api/v1/bookings/partner/{partnerId}` - Get partner bookings
- PUT    `/api/v1/bookings/{bookingId}/assign-partner/{partnerId}` - Assign partner
- PUT    `/api/v1/bookings/{bookingId}/status` - Update status
- PUT    `/api/v1/bookings/{bookingId}/cancel` - Cancel booking

### Payment Service (Port 8083)
- POST   `/api/v1/payments` - Initiate payment
- GET    `/api/v1/payments/{paymentId}` - Get payment
- GET    `/api/v1/payments/booking/{bookingId}` - Get payment by booking
- POST   `/api/v1/payments/{paymentId}/refund` - Process refund

### Partner Service (Port 8084)
- POST   `/api/v1/partners` - Register partner
- GET    `/api/v1/partners/{partnerId}` - Get partner
- PUT    `/api/v1/partners/{partnerId}` - Update partner
- GET    `/api/v1/partners/available` - Get available partners
- POST   `/api/v1/partners/{partnerId}/availability` - Set availability

### Catalog Service (Port 8085)
- GET    `/api/v1/catalog/services` - List all services
- GET    `/api/v1/catalog/services/{serviceId}` - Get service
- GET    `/api/v1/catalog/categories` - List categories
- GET    `/api/v1/catalog/services/category/{categoryId}` - Services by category

## Security Architecture

### JWT Token Flow
```
1. User Login → User Service
2. Validate Credentials
3. Generate JWT Token
4. Return Token to Client
5. Client includes token in Authorization header
6. API Gateway validates token
7. Forward request to service
```

### Role-Based Access Control (RBAC)
- **CUSTOMER**: Can create bookings, view own bookings
- **PARTNER**: Can view assigned bookings, update status
- **ADMIN**: Full access to all resources

## Scalability Strategy

### Horizontal Scaling
- Each microservice can scale independently
- Kubernetes HPA based on CPU/Memory usage
- Auto-scaling: Min 3, Max 10 replicas per service

### Caching Strategy
- Redis for:
  - User sessions
  - Service catalog
  - Partner availability
  - Frequently accessed data

### Database Optimization
- Read replicas for heavy read operations
- Connection pooling
- Indexing on frequently queried columns
- Partitioning for large tables (bookings, payments)

## Monitoring & Observability

### Metrics (Prometheus + Grafana)
- Request rate, latency, error rate
- Database connection pool metrics
- JVM metrics (heap, GC)
- Business metrics (bookings/hour, revenue)

### Logging (ELK Stack)
- Centralized logging
- Structured JSON logs
- Correlation IDs for tracing

### Distributed Tracing (Jaeger/Zipkin)
- End-to-end request tracing
- Performance bottleneck identification

## Disaster Recovery

### Backup Strategy
- Daily automated database backups
- Transaction log backups every hour
- S3 for backup storage with versioning

### High Availability
- Multi-AZ deployment
- Database replication
- Load balancer health checks
- Automatic failover
