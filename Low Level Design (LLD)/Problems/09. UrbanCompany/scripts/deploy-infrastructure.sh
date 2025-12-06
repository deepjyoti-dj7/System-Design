#!/bin/bash

# Urban Company - Complete Infrastructure Deployment Script
# This script deploys the entire Urban Company microservices platform

set -e

echo "======================================"
echo "Urban Company Infrastructure Deployment"
echo "======================================"
echo ""

# Color codes for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to print colored output
print_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

# Check if deployment type is provided
if [ -z "$1" ]; then
    print_error "Deployment type not specified!"
    echo "Usage: $0 [docker|kubernetes|k8s]"
    exit 1
fi

DEPLOYMENT_TYPE=$1

# Docker Deployment
if [ "$DEPLOYMENT_TYPE" == "docker" ]; then
    print_info "Starting Docker deployment..."
    
    # Navigate to docker directory
    cd "$(dirname "$0")/../infrastructure/docker"
    
    print_info "Building Docker images..."
    docker-compose build
    
    print_info "Starting services..."
    docker-compose up -d
    
    print_info "Waiting for services to be healthy..."
    sleep 30
    
    print_info "Checking service health..."
    docker-compose ps
    
    echo ""
    print_info "Docker deployment completed!"
    echo ""
    print_info "Services are available at:"
    echo "  - API Gateway: http://localhost:8080"
    echo "  - Eureka Dashboard: http://localhost:8761"
    echo "  - Grafana: http://localhost:3000 (admin/admin)"
    echo "  - Prometheus: http://localhost:9090"
    echo "  - Kibana: http://localhost:5601"
    echo ""
    print_info "To view logs: docker-compose logs -f [service-name]"
    print_info "To stop all services: docker-compose down"
    
# Kubernetes Deployment
elif [ "$DEPLOYMENT_TYPE" == "kubernetes" ] || [ "$DEPLOYMENT_TYPE" == "k8s" ]; then
    print_info "Starting Kubernetes deployment..."
    
    # Navigate to kubernetes directory
    cd "$(dirname "$0")/../infrastructure/kubernetes"
    
    # Check if kubectl is installed
    if ! command -v kubectl &> /dev/null; then
        print_error "kubectl is not installed. Please install kubectl first."
        exit 1
    fi
    
    # Create namespace
    print_info "Creating namespace..."
    kubectl apply -f namespace.yaml
    
    # Apply ConfigMaps
    print_info "Creating ConfigMaps..."
    kubectl apply -f configmaps/
    
    # Apply Secrets
    print_warning "Creating Secrets (Update these in production!)..."
    kubectl apply -f secrets/
    
    # Deploy stateful services (databases, cache, messaging)
    print_info "Deploying stateful services..."
    # Note: In production, use StatefulSets or managed services
    print_warning "Database deployments should be handled separately in production"
    
    # Deploy infrastructure services
    print_info "Deploying Discovery Service..."
    kubectl apply -f deployments/discovery-service-deployment.yaml
    kubectl apply -f services/discovery-service.yaml
    
    sleep 30
    
    print_info "Deploying API Gateway..."
    kubectl apply -f deployments/api-gateway-deployment.yaml
    kubectl apply -f services/api-gateway-service.yaml
    
    # Deploy business services
    print_info "Deploying business services..."
    kubectl apply -f deployments/user-service-deployment.yaml
    kubectl apply -f services/user-service.yaml
    
    kubectl apply -f deployments/booking-service-deployment.yaml
    kubectl apply -f services/booking-service.yaml
    
    kubectl apply -f deployments/payment-service-deployment.yaml
    kubectl apply -f services/payment-service.yaml
    
    kubectl apply -f deployments/partner-service-deployment.yaml
    kubectl apply -f services/partner-service.yaml
    
    kubectl apply -f deployments/catalog-service-deployment.yaml
    kubectl apply -f services/catalog-service.yaml
    
    # Deploy HPA
    print_info "Configuring auto-scaling..."
    kubectl apply -f hpa/
    
    # Deploy Ingress
    print_info "Configuring Ingress..."
    kubectl apply -f ingress/ingress.yaml
    
    print_info "Waiting for deployments to be ready..."
    kubectl wait --for=condition=available --timeout=300s \
        deployment/discovery-service \
        deployment/api-gateway \
        deployment/user-service \
        deployment/booking-service \
        deployment/payment-service \
        deployment/partner-service \
        deployment/catalog-service \
        -n urban-company
    
    echo ""
    print_info "Kubernetes deployment completed!"
    echo ""
    print_info "Checking deployment status:"
    kubectl get pods -n urban-company
    echo ""
    kubectl get services -n urban-company
    echo ""
    
    print_info "To access services:"
    echo "  - Get API Gateway external IP: kubectl get svc api-gateway -n urban-company"
    echo "  - Port forward for local access: kubectl port-forward svc/api-gateway 8080:8080 -n urban-company"
    echo "  - View logs: kubectl logs -f deployment/[service-name] -n urban-company"
    echo "  - Scale service: kubectl scale deployment [service-name] --replicas=N -n urban-company"
    
else
    print_error "Invalid deployment type: $DEPLOYMENT_TYPE"
    echo "Usage: $0 [docker|kubernetes|k8s]"
    exit 1
fi

echo ""
print_info "Deployment script completed successfully!"
