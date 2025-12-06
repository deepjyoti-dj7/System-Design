# Urban Company - Infrastructure Guide

## Table of Contents
1. [Overview](#overview)
2. [Docker Deployment](#docker-deployment)
3. [Kubernetes Deployment](#kubernetes-deployment)
4. [Monitoring & Observability](#monitoring--observability)
5. [Scaling & Performance](#scaling--performance)
6. [Disaster Recovery](#disaster-recovery)

## Overview

The Urban Company infrastructure supports two deployment models:
- **Docker Compose**: For local development and testing
- **Kubernetes**: For production deployments with auto-scaling and high availability

### Architecture Components

#### Infrastructure Services
- **Discovery Service (Eureka)**: Service registry and discovery
- **API Gateway (Spring Cloud Gateway)**: Single entry point, routing, rate limiting
- **Redis**: Distributed caching and session management
- **Kafka + Zookeeper**: Event streaming and messaging

#### Business Services
- **User Service**: Authentication, authorization, user management
- **Booking Service**: Booking lifecycle, state machine, partner matching
- **Payment Service**: Payment processing, refunds, multiple gateways
- **Partner Service**: Partner onboarding, verification, availability
- **Catalog Service**: Service catalog, categories, pricing

#### Data Stores
- **PostgreSQL**: Separate databases for each service (database-per-service pattern)
- **PostGIS**: Geospatial queries for partner matching
- **Elasticsearch**: Log aggregation and search
- **MongoDB**: Document storage for logs

#### Monitoring Stack
- **Prometheus**: Metrics collection and storage
- **Grafana**: Visualization and dashboards
- **Elasticsearch + Logstash + Kibana (ELK)**: Centralized logging

## Docker Deployment

### Prerequisites
```bash
- Docker 24.0+
- Docker Compose 2.20+
- 16GB RAM minimum
- 50GB disk space
```

### Quick Start

```bash
# Clone the repository
cd urban-company-clone

# Deploy all services
./scripts/deploy-infrastructure.sh docker

# Check service health
./scripts/health-check.sh docker

# View logs
cd infrastructure/docker
docker-compose logs -f [service-name]

# Stop all services
docker-compose down

# Complete cleanup (removes volumes and data)
./scripts/cleanup.sh docker
```

### Service Endpoints (Docker)

| Service | URL | Description |
|---------|-----|-------------|
| API Gateway | http://localhost:8080 | Main entry point |
| Discovery Service | http://localhost:8761 | Eureka dashboard |
| User Service | http://localhost:8081 | Direct access (dev only) |
| Booking Service | http://localhost:8082 | Direct access (dev only) |
| Payment Service | http://localhost:8083 | Direct access (dev only) |
| Partner Service | http://localhost:8084 | Direct access (dev only) |
| Catalog Service | http://localhost:8085 | Direct access (dev only) |
| Prometheus | http://localhost:9090 | Metrics |
| Grafana | http://localhost:3000 | Dashboards (admin/admin) |
| Kibana | http://localhost:5601 | Log viewer |
| Elasticsearch | http://localhost:9200 | Search API |

### Docker Compose Configuration

The `docker-compose.yml` includes:

1. **Service Discovery**: Eureka server with 2 replicas
2. **API Gateway**: Load balancer with 3 replicas
3. **Business Services**: Each with health checks and dependencies
4. **Databases**: 5 PostgreSQL instances (one per service)
5. **Messaging**: Kafka cluster with Zookeeper
6. **Caching**: Redis with persistence
7. **Monitoring**: Prometheus + Grafana with pre-configured dashboards
8. **Logging**: ELK stack for centralized logging

### Environment Variables

Services use environment variables for configuration:
- `SPRING_PROFILES_ACTIVE=docker`: Activates Docker profile
- `SPRING_DATASOURCE_URL`: Database connection string
- `SPRING_REDIS_HOST`: Redis hostname
- `SPRING_KAFKA_BOOTSTRAP_SERVERS`: Kafka brokers
- `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE`: Eureka server URL

## Kubernetes Deployment

### Prerequisites
```bash
- Kubernetes 1.28+
- kubectl configured
- Helm 3.12+ (optional, for charts)
- Ingress controller (nginx-ingress)
- cert-manager (for TLS)
- Metrics server (for HPA)
```

### Cluster Setup

```bash
# Create namespace
kubectl apply -f infrastructure/kubernetes/namespace.yaml

# Deploy infrastructure
./scripts/deploy-infrastructure.sh kubernetes

# Check deployment status
kubectl get all -n urban-company

# Monitor pods
kubectl get pods -n urban-company -w

# Access logs
kubectl logs -f deployment/user-service -n urban-company
```

### Kubernetes Resources

#### 1. Deployments
Each service has:
- **Replica count**: 2-3 replicas (adjustable via HPA)
- **Resource limits**: CPU and memory constraints
- **Health checks**: Liveness and readiness probes
- **Rolling updates**: Zero-downtime deployments
- **Anti-affinity**: Spread pods across nodes

```yaml
resources:
  requests:
    memory: "1Gi"
    cpu: "500m"
  limits:
    memory: "2Gi"
    cpu: "1000m"
```

#### 2. Services
- **ClusterIP**: For internal services (user, booking, payment, etc.)
- **LoadBalancer**: For API Gateway (external access)
- **Session Affinity**: ClientIP for stateful operations

#### 3. ConfigMaps
Store non-sensitive configuration:
- Database URLs
- Redis hostname
- Kafka bootstrap servers
- Eureka discovery URL

#### 4. Secrets
Store sensitive data (base64 encoded):
- Database credentials
- JWT secrets
- Payment gateway API keys
- Encryption keys

⚠️ **Production Warning**: Use external secret management (AWS Secrets Manager, HashiCorp Vault)

#### 5. Horizontal Pod Autoscaler (HPA)

Auto-scaling based on CPU and memory:

```yaml
# User Service: 3-10 replicas
- CPU target: 70%
- Memory target: 80%
- Scale up: Fast (100% in 30s)
- Scale down: Conservative (50% in 5m)

# Booking Service: 3-15 replicas (highest traffic)
- CPU target: 70%
- Memory target: 80%
- Scale up: Aggressive (3 pods in 30s)
- Scale down: Conservative

# API Gateway: 3-10 replicas
- CPU target: 75%
- Memory target: 85%
- Scale up: Very fast (3 pods in 15s)
```

#### 6. Ingress

Nginx ingress with:
- **TLS termination**: Let's Encrypt certificates
- **CORS**: Configured for web/mobile apps
- **Rate limiting**: 100 requests per client
- **Path-based routing**: Routes to appropriate services

Access pattern:
```
https://api.urbancompany.com/api/v1/users → API Gateway → User Service
https://api.urbancompany.com/bookings → Booking Service (direct)
```

### Production Considerations

#### 1. Database Management
- Use managed databases (AWS RDS, Google Cloud SQL)
- Set up read replicas for scaling
- Enable automated backups
- Configure connection pooling (HikariCP)

#### 2. High Availability
- Multi-zone deployment (3+ availability zones)
- Database failover and replication
- Redis cluster mode (not standalone)
- Kafka cluster (3+ brokers)

#### 3. Security
- Network policies for pod-to-pod communication
- Service mesh (Istio/Linkerd) for mTLS
- RBAC for service accounts
- Pod Security Policies
- Secret encryption at rest

#### 4. Monitoring
```bash
# Install Prometheus Operator
helm install prometheus prometheus-community/kube-prometheus-stack

# Configure ServiceMonitor for each service
kubectl apply -f monitoring/service-monitors/
```

## Monitoring & Observability

### Grafana Dashboards

1. **Services Overview**
   - Service health status
   - Request rate (req/s)
   - Response time (p50, p95, p99)
   - Error rate
   - JVM memory usage
   - CPU utilization
   - Database connections

2. **Booking Service Dashboard**
   - Bookings created per minute
   - Booking status distribution
   - Partner matching time
   - State transition metrics
   - Failed booking reasons

3. **Payment Service Dashboard**
   - Payment success rate
   - Total payment volume
   - Average transaction amount
   - Payments by gateway
   - Failure reasons
   - Refund rate

### Prometheus Metrics

Custom application metrics:
```java
// Counters
bookings_created_total
payments_successful_total
partner_registrations_total

// Histograms
partner_matching_duration_seconds
payment_processing_duration_seconds
booking_state_transition_duration_seconds

// Gauges
active_bookings
available_partners_by_location
```

### Alerting

Configure alerts in `prometheus/alerts.yml`:
```yaml
- Alert: HighErrorRate
  Expr: rate(http_server_requests_seconds_count{status=~"5.."}[5m]) > 0.05
  For: 5m
  
- Alert: HighResponseTime
  Expr: histogram_quantile(0.95, http_server_requests_seconds_bucket) > 1
  For: 5m

- Alert: ServiceDown
  Expr: up{job=~".*-service"} == 0
  For: 1m
```

### Log Aggregation

Logstash pipeline processes logs:
1. Receives logs from services (TCP/HTTP)
2. Parses and enriches with metadata
3. Indexes to Elasticsearch
4. Separate index for errors

Query logs in Kibana:
```
# All errors from booking service
level:ERROR AND service:booking-service

# Payment failures
message:"PaymentProcessingException" AND level:ERROR

# Slow queries
duration:>1000 AND logger_name:*Repository
```

## Scaling & Performance

### Horizontal Scaling

```bash
# Manual scaling
kubectl scale deployment booking-service --replicas=10 -n urban-company

# Update HPA
kubectl edit hpa booking-service-hpa -n urban-company
```

### Vertical Scaling

Update resource limits in deployment:
```yaml
resources:
  requests:
    memory: "2Gi"  # Increased from 1Gi
    cpu: "1000m"   # Increased from 500m
  limits:
    memory: "4Gi"
    cpu: "2000m"
```

### Database Optimization

1. **Connection Pooling**
```yaml
spring:
  datasource:
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      connection-timeout: 30000
```

2. **Read Replicas**
```java
@Transactional(readOnly = true)
@ReadOnlyRoute  // Routes to read replica
public List<Booking> findAll() { ... }
```

3. **Caching Strategy**
```java
@Cacheable(value = "services", key = "#serviceId")
public Service getService(Long serviceId) { ... }
```

### Performance Tuning

1. **JVM Tuning**
```bash
JAVA_OPTS: "-Xms1g -Xmx2g -XX:+UseG1GC -XX:MaxGCPauseMillis=200"
```

2. **Database Indexes**
```sql
CREATE INDEX idx_booking_user_id ON bookings(user_id);
CREATE INDEX idx_booking_status ON bookings(status);
CREATE INDEX idx_partner_location ON partners USING GIST(location);
```

## Disaster Recovery

### Backup Strategy

1. **Database Backups**
```bash
# Automated daily backups
0 2 * * * /scripts/backup-databases.sh

# Point-in-time recovery enabled
# Retention: 30 days
```

2. **Configuration Backups**
```bash
# Backup all K8s resources
kubectl get all --all-namespaces -o yaml > backup.yaml
```

### Disaster Recovery Plan

1. **RTO (Recovery Time Objective)**: 4 hours
2. **RPO (Recovery Point Objective)**: 15 minutes

**Recovery Steps:**
1. Deploy infrastructure in backup region
2. Restore database from latest backup
3. Apply Kubernetes manifests
4. Update DNS to point to new cluster
5. Verify all services operational

### Monitoring SLOs

| Service | Availability | Latency (p95) | Error Rate |
|---------|--------------|---------------|------------|
| User Service | 99.9% | < 200ms | < 0.1% |
| Booking Service | 99.95% | < 500ms | < 0.5% |
| Payment Service | 99.99% | < 1000ms | < 0.01% |
| API Gateway | 99.9% | < 100ms | < 0.1% |

## Deployment Scripts

### deploy-infrastructure.sh
Complete deployment for Docker or Kubernetes

```bash
# Docker deployment
./scripts/deploy-infrastructure.sh docker

# Kubernetes deployment
./scripts/deploy-infrastructure.sh kubernetes
```

### health-check.sh
Verify all services are healthy

```bash
./scripts/health-check.sh docker
./scripts/health-check.sh kubernetes
```

### cleanup.sh
Remove all deployed resources

```bash
./scripts/cleanup.sh docker
./scripts/cleanup.sh kubernetes
```

## Troubleshooting

### Common Issues

1. **Service not starting**
```bash
# Check logs
kubectl logs deployment/user-service -n urban-company

# Check events
kubectl describe pod <pod-name> -n urban-company
```

2. **Database connection issues**
```bash
# Test connectivity
kubectl run -it --rm debug --image=postgres:15 --restart=Never -- \
  psql -h postgres-user -U user_service -d user_service_db
```

3. **Service discovery issues**
```bash
# Check Eureka dashboard
# Verify EUREKA_CLIENT_SERVICEURL_DEFAULTZONE is correct
# Ensure discovery-service is running
```

4. **High memory usage**
```bash
# Check metrics
kubectl top pods -n urban-company

# Increase limits or investigate memory leak
```

## Next Steps

1. **CI/CD Pipeline**: Set up GitHub Actions or Jenkins
2. **Security Hardening**: Implement service mesh, pod security policies
3. **Multi-Region Deployment**: Deploy to multiple regions for DR
4. **Performance Testing**: Load testing with JMeter/Gatling
5. **Cost Optimization**: Right-size resources, use spot instances

---

For more information, see:
- [Setup Guide](SETUP_GUIDE.md)
- [System Architecture](SYSTEM_ARCHITECTURE.md)
- [API Documentation](API_DOCUMENTATION.md)
