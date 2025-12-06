#!/bin/bash

echo "Deploying all services..."

# Build all services
echo "Building services..."
mvn clean package -DskipTests

# Deploy to Kubernetes
echo "Deploying to Kubernetes..."
kubectl apply -f infrastructure/kubernetes/namespaces/
kubectl apply -f infrastructure/kubernetes/configmaps/
kubectl apply -f infrastructure/kubernetes/secrets/
kubectl apply -f infrastructure/kubernetes/deployments/
kubectl apply -f infrastructure/kubernetes/services/
kubectl apply -f infrastructure/kubernetes/ingress/

echo "Deployment complete!"
kubectl get pods -n urbanclone
