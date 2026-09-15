#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

# Function to build a microservice
build_microservice() {
    local service=$1
    echo "Building $service..."
    cd "$service" || exit 1
    ./mvnw clean package -Dmaven.test.skip=true
    cd ..
}

# Array of microservices
MICROSERVICES=(
    "microservice-inventory"
    "microservice-payment"
    "microservice-sale"
)

# Build all microservices
for service in "${MICROSERVICES[@]}"; do
    build_microservice "$service"
done

# Docker operations
echo "Stopping and removing containers, networks, volumes, and images..."
docker compose down --rmi local -v

echo "Starting up Docker containers..."
docker compose up -d

echo "Build and deployment complete. Docker containers are running in detached mode."
echo "To view logs, use: docker compose logs -f"