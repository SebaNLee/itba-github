#!/usr/bin/env bash
# Destruye el cluster levantado con ./up.sh y chequea que no quede nada consumiendo crédito.
# Lo que vivía adentro del cluster (pods, PVC y sus discos, IPs públicas) se borra con él.
set -euo pipefail
cd "$(dirname "$0")"

# Se decide por el estado (no por los outputs): un `up.sh` que falló a mitad puede haber creado el
# resource group sin llegar a crear el cluster.
if [ -z "$(terraform -chdir=terraform state list 2>/dev/null)" ]; then
  echo "Terraform no tiene recursos registrados en este directorio. Nada que destruir."
else
  RG=$(terraform -chdir=terraform output -raw resource_group_name 2>/dev/null || true)
  CLUSTER=$(terraform -chdir=terraform output -raw cluster_name 2>/dev/null || true)

  echo "==> Destruyendo ${CLUSTER:-lo que quedó de up.sh} (tarda ~8 min)"
  terraform -chdir=terraform destroy -auto-approve -input=false

  # El kubeconfig apuntaría a un cluster que ya no existe.
  kubectl config delete-context "$CLUSTER" >/dev/null 2>&1 || true
  kubectl config delete-cluster "$CLUSTER" >/dev/null 2>&1 || true
  kubectl config delete-user "clusterUser_${RG}_${CLUSTER}" >/dev/null 2>&1 || true
fi

echo
echo "==> Resource groups que siguen en la suscripción:"
az group list --query "[].{nombre:name, region:location}" -o table
echo
echo "Si aparece alguno del curso (rg-figuritas*, MC_*), sigue gastando crédito:"
echo "  az group delete --name <nombre> --yes"
echo "NetworkWatcherRG lo crea Azure solo y no cobra; se puede dejar."
