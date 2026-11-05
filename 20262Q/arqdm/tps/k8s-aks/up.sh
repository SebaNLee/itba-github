#!/usr/bin/env bash
# Levanta el cluster del TP de punta a punta: lo mismo que las partes 2 y 6 del README, en un comando.
#   1. Terraform crea el resource group y el AKS.
#   2. Baja el kubeconfig y espera a que los nodos estén Ready.
#   3. Instala Envoy Gateway y la GatewayClass `eg`.
#
# Uso: ./up.sh                        (usa terraform/terraform.tfvars; si no existe, corre ./elegir-region.sh)
#      ./up.sh -var="node_count=1"    (cualquier -var de Terraform)
set -euo pipefail
cd "$(dirname "$0")"

ENVOY_GATEWAY_VERSION="v1.9.2"

for cmd in az terraform kubectl helm; do
  command -v "$cmd" >/dev/null || { echo "Falta '$cmd' (ver README, parte 1)."; exit 1; }
done
az account show >/dev/null 2>&1 || { echo "No hay sesión de Azure: corré 'az login'."; exit 1; }

echo "==> Suscripción: $(az account show --query name -o tsv)"

if [ ! -f terraform/terraform.tfvars ]; then
  echo "==> No hay terraform/terraform.tfvars: buscando una región permitida"
  ./elegir-region.sh
fi

echo "==> [1/3] Infra con Terraform (tarda ~5-10 min)"
terraform -chdir=terraform init -input=false >/dev/null
# ${1+"$@"}: con `set -u`, el bash 3.2 de macOS trata un "$@" vacío como variable sin definir.
terraform -chdir=terraform apply -auto-approve -input=false ${1+"$@"}

RG=$(terraform -chdir=terraform output -raw resource_group_name)
CLUSTER=$(terraform -chdir=terraform output -raw cluster_name)

echo "==> [2/3] kubeconfig de ${CLUSTER}"
az aks get-credentials --resource-group "$RG" --name "$CLUSTER" --overwrite-existing
kubectl wait --for=condition=Ready nodes --all --timeout=5m

echo "==> [3/3] Envoy Gateway ${ENVOY_GATEWAY_VERSION} + GatewayClass 'eg'"
helm upgrade --install eg oci://docker.io/envoyproxy/gateway-helm \
  --version "$ENVOY_GATEWAY_VERSION" \
  --namespace envoy-gateway-system --create-namespace \
  --wait --timeout 5m
kubectl apply -f infra/gatewayclass.yaml
kubectl wait --for=condition=Accepted gatewayclass/eg --timeout=2m

echo
kubectl get nodes -o wide
echo
echo "Cluster listo. Contexto de kubectl: $(kubectl config current-context)"
echo "Para apagarlo y dejar de gastar crédito: ./down.sh"
