# Urban Company Clone - Complete Setup Guide

## Prerequisites

### Required Software
- **Java 17** or higher
- **Maven 3.8+**
- **Docker & Docker Compose**
- **PostgreSQL 15** (if running locally)
- **Redis 7**
- **Apache Kafka**
- **Node.js 18+** (for frontend, if applicable)

### Development Tools
- IntelliJ IDEA / Eclipse / VS Code
- Postman or similar API testing tool
- Git

---

## Project Overview

This is a production-grade microservices-based home services platform (Urban Company clone) built with:
- **Backend**: Java 17, Spring Boot 3.2.0, Spring Cloud
- **Databases**: PostgreSQL (primary), Redis (cache), MongoDB (logs)
- **Message Queue**: Apache Kafka
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Containerization**: Docker, Kubernetes
- **Monitoring**: Prometheus, Grafana, ELK Stack

---

## Quick Start with Docker Compose

### 1. Clone the Repository
```bash
git clone https://github.com/your-org/urban-company-clone.git
cd urban-company-clone
```

### 2. Build All Services
```bash
mvn clean install -DskipTests
```

### 3. Start Infrastructure Services
```bash
cd infrastructure/docker
docker-compose up -d
```

This will start:
- PostgreSQL databases (3 instances)
- Redis
- Kafka + Zookeeper
- Elasticsearch
- MongoDB
- Prometheus
- Grafana

### 4. Verify Services are Running
```bash
docker-compose ps
```

### 5. Access Services
- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:8080
- **Grafana**: http://localhost:3000 (admin/admin)
- **Prometheus**: http://localhost:9090

---

## Local Development Setup

### 1. Setup Databases

#### PostgreSQL
```bash
# Create databases
psql -U postgres
CREATE DATABASE user_db;
CREATE DATABASE booking_db;
CREATE DATABASE payment_db;
CREATE DATABASE partner_db;
CREATE DATABASE catalog_db;
```

#### Redis
```bash
# Start Redis
redis-server
```

#### Kafka
```bash
# Start Zookeeper
bin/zookeeper-server-start.sh config/zookeeper.properties

# Start Kafka
bin/kafka-server-start.sh config/server.properties

# Create topics
bin/kafka-topics.sh --create --topic user-created-events --bootstrap-server localhost:9092
bin/kafka-topics.sh --create --topic booking-created-events --bootstrap-server localhost:9092
bin/kafka-topics.sh --create --topic payment-events --bootstrap-server localhost:9092
```

### 2. Configure Environment Variables

Create `.env` file in project root:
```env
# Database Configuration
DB_HOST=localhost
DB_PORT=5432
DB_USERNAME=postgres
DB_PASSWORD=postgres

# Kafka Configuration
KAFKA_BOOTSTRAP_SERVERS=localhost:9092

# Redis Configuration
REDIS_HOST=localhost
REDIS_PORT=6379

# JWT Configuration
JWT_SECRET=urbancompanysecretsupersecretkeyshouldbe256bits
JWT_EXPIRATION=86400000

# Payment Gateway Configuration
STRIPE_API_KEY=sk_test_your_stripe_key
RAZORPAY_KEY_ID=your_razorpay_key
RAZORPAY_KEY_SECRET=your_razorpay_secret
```

### 3. Run Services in Order

#### Start Discovery Service (First)
```bash
cd services/discovery-service
mvn spring-boot:run
```
Wait for it to start on port 8761

#### Start API Gateway (Second)
```bash
cd services/api-gateway
mvn spring-boot:run
```

#### Start Core Services
Open separate terminals for each:

```bash
# User Service
cd services/user-service
mvn spring-boot:run

# Booking Service
cd services/booking-service
mvn spring-boot:run

# Payment Service
cd services/payment-service
mvn spring-boot:run

# Partner Service
cd services/partner-service
mvn spring-boot:run

# Catalog Service
cd services/catalog-service
mvn spring-boot:run

# Notification Service
cd services/notification-service
mvn spring-boot:run
```

### 4. Verify Services
```bash
curl http://localhost:8761/eureka/apps
```

---

## Testing the APIs

### 1. Register a User
```bash
curl -X POST http://localhost:8080/api/v1/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "phoneNumber": "+919876543210",
    "password": "SecurePass123",
    "firstName": "John",
    "lastName": "Doe",
    "role": "CUSTOMER"
  }'
```

### 2. Login
```bash
curl -X POST http://localhost:8080/api/v1/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "SecurePass123"
  }'
```

Save the `accessToken` from response.

### 3. Create a Booking
```bash
curl -X POST http://localhost:8080/api/v1/bookings \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
  -H "X-User-Id: 1" \
  -d '{
    "serviceId": 1,
    "scheduledAt": "2024-12-10T14:00:00",
    "address": {
      "street": "123 Main St",
      "city": "Mumbai",
      "state": "Maharashtra",
      "zipCode": "400001",
      "latitude": 19.0760,
      "longitude": 72.8777
    },
    "paymentMethod": "UPI"
  }'
```

---

## Kubernetes Deployment

### 1. Setup Kubernetes Cluster
```bash
# For local development, use Minikube
minikube start --cpus=4 --memory=8192
```

### 2. Create Namespace
```bash
kubectl create namespace urbanclone
```

### 3. Apply ConfigMaps and Secrets
```bash
kubectl apply -f infrastructure/kubernetes/configmaps/
kubectl apply -f infrastructure/kubernetes/secrets/
```

### 4. Deploy Services
```bash
kubectl apply -f infrastructure/kubernetes/deployments/
kubectl apply -f infrastructure/kubernetes/services/
```

### 5. Setup Ingress
```bash
kubectl apply -f infrastructure/kubernetes/ingress/
```

### 6. Monitor Deployments
```bash
kubectl get pods -n urbanclone
kubectl get services -n urbanclone
```

---

## Database Migrations

### Using Flyway (Recommended)
Add migration scripts in `src/main/resources/db/migration/`:

```
V1__create_users_table.sql
V2__create_bookings_table.sql
V3__add_indexes.sql
```

### Manual Migration
```bash
cd scripts/database
psql -U postgres -d user_db -f create-databases.sql
psql -U postgres -d user_db -f seed-data.sql
```

---

## Monitoring & Logging

### Prometheus Metrics
Access: http://localhost:9090

Key Metrics:
- `http_server_requests_seconds_count` - Request count
- `jvm_memory_used_bytes` - Memory usage
- `system_cpu_usage` - CPU usage

### Grafana Dashboards
Access: http://localhost:3000 (admin/admin)

Import pre-built dashboards:
- JVM Micrometer Dashboard (ID: 4701)
- Spring Boot 2.1 Statistics (ID: 10280)

### ELK Stack Logging
```bash
# Start ELK stack
docker-compose -f infrastructure/monitoring/elk/docker-compose.yml up -d

# Access Kibana
http://localhost:5601
```

---

## Performance Optimization

### 1. Database Connection Pooling
```yaml
spring:
  datasource:
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      connection-timeout: 30000
```

### 2. Redis Caching
```java
@Cacheable(value = "services", key = "#serviceId")
public ServiceDto getService(Long serviceId) {
    // method implementation
}
```

### 3. Kafka Performance
```yaml
spring:
  kafka:
    producer:
      batch-size: 16384
      buffer-memory: 33554432
      compression-type: snappy
```

---

## Security Best Practices

1. **Never commit secrets** to version control
2. Use **environment variables** for sensitive data
3. Enable **HTTPS** in production
4. Implement **rate limiting** on API Gateway
5. Use **strong JWT secrets** (256-bit minimum)
6. Enable **SQL injection protection** via parameterized queries
7. Implement **CORS** properly
8. Use **encrypted connections** for databases

---

## Troubleshooting

### Service Discovery Issues
```bash
# Check Eureka server
curl http://localhost:8761/eureka/apps

# Check service registration
curl http://localhost:8761/eureka/apps/USER-SERVICE
```

### Database Connection Issues
```bash
# Test PostgreSQL connection
psql -h localhost -U postgres -d user_db

# Check connection pool
curl http://localhost:8081/actuator/metrics/hikaricp.connections
```

### Kafka Issues
```bash
# List topics
kafka-topics.sh --list --bootstrap-server localhost:9092

# Check consumer groups
kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list

# Read messages
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic booking-created-events --from-beginning
```

### Performance Issues
```bash
# Check service health
curl http://localhost:8081/actuator/health

# Check metrics
curl http://localhost:8081/actuator/metrics

# Thread dump
curl http://localhost:8081/actuator/threaddump
```

---

## CI/CD Pipeline

### GitHub Actions
```yaml
# .github/workflows/ci.yml
name: CI/CD Pipeline

on:
  push:
    branches: [ main, develop ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK 17
        uses: actions/setup-java@v2
        with:
          java-version: '17'
      - name: Build with Maven
        run: mvn clean install
      - name: Run tests
        run: mvn test
      - name: Build Docker images
        run: docker-compose build
      - name: Push to registry
        run: docker-compose push
```

---

## Production Deployment Checklist

- [ ] All secrets configured in environment variables
- [ ] Database backups automated
- [ ] Monitoring and alerting setup
- [ ] Load balancers configured
- [ ] SSL certificates installed
- [ ] Auto-scaling policies defined
- [ ] Disaster recovery plan documented
- [ ] Security audit completed
- [ ] Performance testing done
- [ ] Documentation updated

---

## Support & Contribution

### Reporting Issues
Open an issue on GitHub with:
- Environment details
- Steps to reproduce
- Expected vs actual behavior
- Logs/screenshots

### Contributing
1. Fork the repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

---

## License
This project is licensed under the MIT License - see LICENSE file for details.

## Contact
- Project Lead: your-email@example.com
- Slack Channel: #urban-clone-dev
- Documentation: https://docs.urbanclone.com
