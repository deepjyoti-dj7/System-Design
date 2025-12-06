# Infrastructure

This directory contains all infrastructure-as-code for deploying the Urban Company microservices platform.

## Directory Structure

```
infrastructure/
├── docker/
│   ├── docker-compose.yml              # Complete Docker Compose setup
│   ├── prometheus/
│   │   └── prometheus.yml              # Prometheus configuration
│   ├── grafana/
│   │   ├── provisioning/
│   │   │   ├── datasources/            # Auto-configured data sources
│   │   │   └── dashboards/             # Dashboard provisioning
│   │   └── dashboards/                 # JSON dashboard definitions
│   │       ├── services-overview.json  # All services overview
│   │       ├── booking-service.json    # Booking metrics
│   │       └── payment-service.json    # Payment metrics
│   └── logstash/
│       ├── config/logstash.yml         # Logstash configuration
│       └── pipeline/logstash.conf      # Log processing pipeline
│
└── kubernetes/
    ├── namespace.yaml                   # Namespace definition
    ├── deployments/                     # Service deployments
    │   ├── discovery-service-deployment.yaml
    │   ├── api-gateway-deployment.yaml
    │   ├── user-service-deployment.yaml
    │   ├── booking-service-deployment.yaml
    │   ├── payment-service-deployment.yaml
    │   ├── partner-service-deployment.yaml
    │   └── catalog-service-deployment.yaml
    ├── services/                        # Kubernetes services
    │   ├── discovery-service.yaml
    │   ├── api-gateway-service.yaml
    │   ├── user-service.yaml
    │   ├── booking-service.yaml
    │   ├── payment-service.yaml
    │   ├── partner-service.yaml
    │   ├── catalog-service.yaml
    │   ├── redis-service.yaml
    │   └── kafka-service.yaml
    ├── configmaps/                      # Configuration management
    │   ├── user-service-configmap.yaml
    │   ├── booking-service-configmap.yaml
    │   ├── payment-service-configmap.yaml
    │   ├── partner-service-configmap.yaml
    │   └── catalog-service-configmap.yaml
    ├── secrets/                         # Secret management
    │   ├── user-service-secret.yaml
    │   ├── booking-service-secret.yaml
    │   ├── payment-service-secret.yaml
    │   ├── partner-service-secret.yaml
    │   └── catalog-service-secret.yaml
    ├── ingress/
    │   └── ingress.yaml                 # Ingress routing rules
    └── hpa/                             # Auto-scaling configs
        ├── api-gateway-hpa.yaml
        ├── user-service-hpa.yaml
        ├── booking-service-hpa.yaml
        ├── payment-service-hpa.yaml
        └── partner-service-hpa.yaml
```

## Quick Start

### Docker Deployment

```bash
# From project root
./scripts/deploy-infrastructure.sh docker

# Or manually
cd infrastructure/docker
docker-compose up -d

# Check status
docker-compose ps

# View logs
docker-compose logs -f [service-name]

# Stop
docker-compose down
```

### Kubernetes Deployment

```bash
# From project root
./scripts/deploy-infrastructure.sh kubernetes

# Or manually
cd infrastructure/kubernetes
kubectl apply -f namespace.yaml
kubectl apply -f configmaps/
kubectl apply -f secrets/
kubectl apply -f deployments/
kubectl apply -f services/
kubectl apply -f ingress/
kubectl apply -f hpa/

# Check status
kubectl get all -n urban-company
kubectl get pods -n urban-company -w
```

## Docker Compose

### Services Included

**Infrastructure:**
- Discovery Service (Eureka) - Service registry
- API Gateway - Routing and load balancing

**Business Services:**
- User Service (Port 8081)
- Booking Service (Port 8082)
- Payment Service (Port 8083)
- Partner Service (Port 8084)
- Catalog Service (Port 8085)

**Data Stores:**
- PostgreSQL × 5 (separate DB per service)
- Redis (caching)
- MongoDB (logs)

**Messaging:**
- Apache Kafka
- Zookeeper

**Monitoring:**
- Prometheus (metrics)
- Grafana (dashboards)
- Elasticsearch (logs)
- Logstash (log processing)
- Kibana (log viewer)

### Environment Variables

All services use these common variables:
- `SPRING_PROFILES_ACTIVE`: Activation profile (docker/kubernetes)
- `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE`: Eureka server URL
- `SPRING_DATASOURCE_URL`: Database connection
- `SPRING_REDIS_HOST`: Redis hostname
- `SPRING_KAFKA_BOOTSTRAP_SERVERS`: Kafka brokers

### Health Checks

All services have health checks configured:
```yaml
healthcheck:
  test: ["CMD", "curl", "-f", "http://localhost:8081/actuator/health"]
  interval: 30s
  timeout: 10s
  retries: 5
  start_period: 60s
```

## Kubernetes

### Resource Limits

Each service has defined resource requests and limits:

**User Service:**
- Request: 1Gi RAM, 500m CPU
- Limit: 2Gi RAM, 1000m CPU

**Booking Service (high traffic):**
- Request: 1Gi RAM, 500m CPU
- Limit: 2Gi RAM, 1000m CPU

**API Gateway:**
- Request: 1Gi RAM, 500m CPU
- Limit: 2Gi RAM, 1000m CPU

### Auto-Scaling (HPA)

Horizontal Pod Autoscaler configured for all services:

**Scaling Metrics:**
- CPU utilization: 70-75%
- Memory utilization: 80-85%

**Replica Ranges:**
- User Service: 3-10 replicas
- Booking Service: 3-15 replicas
- Payment Service: 2-10 replicas
- Partner Service: 3-12 replicas
- API Gateway: 3-10 replicas

**Scale-up Policy:**
- Fast reaction to increased load
- 100% increase or 2-3 pods per 30 seconds

**Scale-down Policy:**
- Conservative to avoid flapping
- 50% reduction over 5 minutes

### Ingress

NGINX Ingress Controller with:
- Path-based routing
- TLS termination (Let's Encrypt)
- CORS configuration
- Rate limiting (100 req/s)

Routes:
```
/api/*       → API Gateway
/users/*     → User Service
/bookings/*  → Booking Service
/payments/*  → Payment Service
/partners/*  → Partner Service
/catalog/*   → Catalog Service
```

### Secrets Management

⚠️ **Production Warning**: 
- Current secrets are base64 encoded (not secure)
- Use external secret management in production:
  - AWS Secrets Manager
  - HashiCorp Vault
  - Azure Key Vault
  - Google Secret Manager

### High Availability

**Pod Anti-Affinity:**
Spreads pods across different nodes to avoid single points of failure.

**Multi-Zone Deployment:**
For production, deploy across 3+ availability zones.

**Database:**
Use managed database services with automatic failover:
- AWS RDS Multi-AZ
- Google Cloud SQL High Availability
- Azure Database for PostgreSQL

## Monitoring

### Prometheus

Scrapes metrics from all services via `/actuator/prometheus` endpoint.

Configuration includes:
- Service discovery via static configs
- 15-second scrape interval
- Metrics retention: 15 days (configurable)

### Grafana Dashboards

**Pre-configured dashboards:**

1. **Services Overview**
   - Service health
   - Request rates
   - Response times (p50, p95, p99)
   - Error rates
   - JVM metrics
   - Database connections

2. **Booking Service**
   - Bookings created/minute
   - Status distribution
   - Partner matching performance
   - State transitions
   - Failure analysis

3. **Payment Service**
   - Success rate
   - Payment volume
   - Average transaction amount
   - Gateway distribution
   - Failure reasons
   - Refund metrics

Access: http://localhost:3000 (admin/admin)

### ELK Stack

**Elasticsearch:**
- Stores all application logs
- Indices: `urban-company-logs-*`, `urban-company-errors-*`

**Logstash:**
- Processes logs from all services
- Parses JSON logs
- Enriches with metadata
- Filters errors to separate index

**Kibana:**
- Query and visualize logs
- Create custom dashboards
- Set up alerts

Access: http://localhost:5601

## Networking

### Docker Network

All services communicate via `urbanclone-network` bridge network.

### Kubernetes Network

Services communicate via Kubernetes DNS:
- `user-service.urban-company.svc.cluster.local`
- `booking-service.urban-company.svc.cluster.local`
- etc.

### Service Discovery

Eureka server maintains registry of all service instances.
Services register on startup and send heartbeats.

## Security Considerations

### Current Setup (Development)
- Basic authentication
- No encryption in transit
- Secrets in plain YAML files

### Production Recommendations
1. **Network Security**
   - Network policies for pod-to-pod communication
   - Service mesh (Istio/Linkerd) for mTLS
   - TLS for all external communication

2. **Secret Management**
   - External secret stores
   - Secret rotation policies
   - Encryption at rest

3. **Access Control**
   - RBAC for Kubernetes resources
   - Service accounts with minimal permissions
   - Pod Security Policies/Admission

4. **Image Security**
   - Scan images for vulnerabilities
   - Use minimal base images
   - Sign images with Docker Content Trust

## Troubleshooting

### Docker

**Services not starting:**
```bash
docker-compose logs [service-name]
docker-compose ps
```

**Network issues:**
```bash
docker network inspect urbanclone-network
docker-compose restart [service-name]
```

**Database connection:**
```bash
docker exec -it postgres-user psql -U user_service -d user_service_db
```

### Kubernetes

**Pod not starting:**
```bash
kubectl describe pod [pod-name] -n urban-company
kubectl logs [pod-name] -n urban-company
kubectl get events -n urban-company --sort-by='.lastTimestamp'
```

**Service discovery:**
```bash
kubectl get endpoints -n urban-company
kubectl run -it --rm debug --image=busybox --restart=Never -- nslookup user-service.urban-company
```

**Resource issues:**
```bash
kubectl top nodes
kubectl top pods -n urban-company
kubectl describe node [node-name]
```

## Performance Tuning

### JVM Settings
Adjust in deployment YAML:
```yaml
env:
- name: JAVA_OPTS
  value: "-Xms512m -Xmx1024m -XX:+UseG1GC"
```

### Database Connection Pool
Configure HikariCP in application.yml:
```yaml
spring:
  datasource:
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
```

### Redis Configuration
Enable persistence and clustering for production.

### Kafka Tuning
Increase partitions for high-throughput topics.

## Maintenance

### Updating Services

**Docker:**
```bash
docker-compose build [service-name]
docker-compose up -d [service-name]
```

**Kubernetes:**
```bash
kubectl set image deployment/user-service \
  user-service=urban-company/user-service:v2 \
  -n urban-company
  
kubectl rollout status deployment/user-service -n urban-company
kubectl rollout undo deployment/user-service -n urban-company  # Rollback
```

### Database Migrations

Use Flyway (integrated in Spring Boot):
1. Add migration script to `src/main/resources/db/migration/`
2. Deploy new version
3. Flyway automatically applies migrations

### Backup and Restore

**Databases:**
```bash
# Backup
./scripts/backup-databases.sh

# Restore
./scripts/restore-databases.sh [backup-file]
```

**Kubernetes configs:**
```bash
kubectl get all -n urban-company -o yaml > backup.yaml
```

## Cost Optimization

### Development
- Use minimal resource limits
- Single replicas for non-critical services
- Shared databases

### Production
- Right-size based on metrics
- Use spot/preemptible instances for non-critical workloads
- Enable cluster autoscaler
- Schedule scale-down during off-peak hours

## Support

For issues or questions:
1. Check the [Infrastructure Guide](../docs/INFRASTRUCTURE_GUIDE.md)
2. Review logs and metrics
3. Consult [Troubleshooting](#troubleshooting) section
4. Create an issue with logs and configuration

---

**Last Updated:** December 2025  
**Version:** 1.0.0
