#!/bin/bash

# Urban Company - Health Check Script
# Monitors the health of all microservices

set -e

echo "======================================"
echo "Urban Company Services Health Check"
echo "======================================"
echo ""

# Color codes
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

check_service() {
    local service_name=$1
    local url=$2
    
    echo -n "Checking $service_name... "
    
    response=$(curl -s -o /dev/null -w "%{http_code}" "$url" || echo "000")
    
    if [ "$response" == "200" ]; then
        echo -e "${GREEN}✓ Healthy${NC}"
        return 0
    else
        echo -e "${RED}✗ Unhealthy (HTTP $response)${NC}"
        return 1
    fi
}

# Check deployment type
if [ "$1" == "docker" ]; then
    BASE_URL="http://localhost"
    
    check_service "Discovery Service" "$BASE_URL:8761/actuator/health"
    check_service "API Gateway" "$BASE_URL:8080/actuator/health"
    check_service "User Service" "$BASE_URL:8081/actuator/health"
    check_service "Booking Service" "$BASE_URL:8082/actuator/health"
    check_service "Payment Service" "$BASE_URL:8083/actuator/health"
    check_service "Partner Service" "$BASE_URL:8084/actuator/health"
    check_service "Catalog Service" "$BASE_URL:8085/actuator/health"
    check_service "Prometheus" "$BASE_URL:9090/-/healthy"
    check_service "Grafana" "$BASE_URL:3000/api/health"
    
elif [ "$1" == "kubernetes" ] || [ "$1" == "k8s" ]; then
    echo "Checking Kubernetes pods status..."
    kubectl get pods -n urban-company
    
    echo ""
    echo "Checking service endpoints..."
    kubectl get endpoints -n urban-company
    
else
    echo "Usage: $0 [docker|kubernetes|k8s]"
    exit 1
fi

echo ""
echo "Health check completed!"
