#!/bin/bash

echo "Setting up Urban Company Clone Development Environment..."

# Install dependencies
echo "Installing Maven dependencies..."
mvn clean install -DskipTests

# Start infrastructure
echo "Starting infrastructure with Docker Compose..."
cd infrastructure/docker
docker-compose up -d

echo "Waiting for services to be ready..."
sleep 30

echo "Setup complete! Services are running on:"
echo "- Eureka Discovery: http://localhost:8761"
echo "- API Gateway: http://localhost:8080"
echo "- User Service: http://localhost:8081"
echo "- Booking Service: http://localhost:8082"
echo "- Payment Service: http://localhost:8083"
echo "- Partner Service: http://localhost:8084"
echo "- Catalog Service: http://localhost:8085"
