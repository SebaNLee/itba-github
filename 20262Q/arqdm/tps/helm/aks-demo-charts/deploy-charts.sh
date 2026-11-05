#!/usr/bin/env bash
set -euo pipefail

helm repo add bitnami https://charts.bitnami.com/bitnami 2>/dev/null || true
helm repo update

echo "Desplegando bitnami/mongodb..."
helm upgrade --install mongodb bitnami/mongodb \
  -f "./values-mongodb.yaml" --version 20.0.5 \
  --namespace aks-demo --create-namespace \
  --wait --timeout 5m

echo "Desplegando bitnami/rabbitmq..."
helm upgrade --install rabbitmq bitnami/rabbitmq \
  -f "./values-rabbitmq.yaml" --version 16.0.14 \
  --namespace aks-demo --create-namespace \
  --wait --timeout 10m

echo "Desplegando makeline-service..."
helm upgrade --install makeline-service \
  ./makeline-service \
  --namespace aks-demo --create-namespace \
  --wait --timeout 5m

echo "Desplegando order-service..."
helm upgrade --install order-service \
  ./order-service/ \
  --namespace aks-demo --create-namespace \
  --wait --timeout 5m

echo "Desplegando product-service..."
helm upgrade --install product-service \
  ./product-service/ \
  --namespace aks-demo --create-namespace \
  --wait --timeout 5m

echo "Desplegando store-front..."
helm upgrade --install store-front \
  ./store-front/ \
  --namespace aks-demo --create-namespace \
  --wait --timeout 5m

echo "Desplegando store-admin..."
helm upgrade --install store-admin \
  ./store-admin/ \
  --namespace aks-demo --create-namespace \
  --wait --timeout 5m

echo "Desplegando virtual-worker..."
helm upgrade --install virtual-worker \
  ./virtual-worker/ \
  --namespace aks-demo --create-namespace \
  --wait --timeout 5m

echo "Desplegando virtual-customer..."
helm upgrade --install virtual-customer \
  ./virtual-customer/ \
  --namespace aks-demo --create-namespace \
  --wait --timeout 5m



