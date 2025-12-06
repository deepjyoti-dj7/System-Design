#!/bin/bash

# Urban Company - Cleanup Script
# Removes all deployed resources

set -e

echo "======================================"
echo "Urban Company Infrastructure Cleanup"
echo "======================================"
echo ""

RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m'

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check deployment type
if [ -z "$1" ]; then
    print_error "Deployment type not specified!"
    echo "Usage: $0 [docker|kubernetes|k8s]"
    exit 1
fi

DEPLOYMENT_TYPE=$1

# Confirmation
print_warning "This will remove all Urban Company services and data!"
read -p "Are you sure you want to continue? (yes/no): " confirmation

if [ "$confirmation" != "yes" ]; then
    echo "Cleanup cancelled."
    exit 0
fi

# Docker Cleanup
if [ "$DEPLOYMENT_TYPE" == "docker" ]; then
    echo "Cleaning up Docker deployment..."
    
    cd "$(dirname "$0")/../infrastructure/docker"
    
    echo "Stopping containers..."
    docker-compose down
    
    read -p "Remove volumes (this will delete all data)? (yes/no): " remove_volumes
    if [ "$remove_volumes" == "yes" ]; then
        echo "Removing volumes..."
        docker-compose down -v
    fi
    
    read -p "Remove images? (yes/no): " remove_images
    if [ "$remove_images" == "yes" ]; then
        echo "Removing images..."
        docker-compose down --rmi all
    fi
    
    echo "Docker cleanup completed!"
    
# Kubernetes Cleanup
elif [ "$DEPLOYMENT_TYPE" == "kubernetes" ] || [ "$DEPLOYMENT_TYPE" == "k8s" ]; then
    echo "Cleaning up Kubernetes deployment..."
    
    cd "$(dirname "$0")/../infrastructure/kubernetes"
    
    echo "Deleting HPA..."
    kubectl delete -f hpa/ --ignore-not-found=true
    
    echo "Deleting Ingress..."
    kubectl delete -f ingress/ --ignore-not-found=true
    
    echo "Deleting Services..."
    kubectl delete -f services/ --ignore-not-found=true
    
    echo "Deleting Deployments..."
    kubectl delete -f deployments/ --ignore-not-found=true
    
    echo "Deleting ConfigMaps and Secrets..."
    kubectl delete -f configmaps/ --ignore-not-found=true
    kubectl delete -f secrets/ --ignore-not-found=true
    
    read -p "Delete namespace (this will delete everything)? (yes/no): " delete_namespace
    if [ "$delete_namespace" == "yes" ]; then
        echo "Deleting namespace..."
        kubectl delete namespace urban-company --ignore-not-found=true
    fi
    
    echo "Kubernetes cleanup completed!"
    
else
    print_error "Invalid deployment type: $DEPLOYMENT_TYPE"
    echo "Usage: $0 [docker|kubernetes|k8s]"
    exit 1
fi

echo ""
echo "Cleanup completed!"
