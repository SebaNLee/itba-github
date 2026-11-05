#!/usr/bin/env bash
# Busca dónde puede vivir el cluster en TU suscripción.
#
# Azure for Students asigna a cada suscripción una lista distinta de regiones permitidas (policy
# "Allowed resource deployment regions"), y no en todas se ofrecen las VMs baratas que tienen cuota.
# Este script recorre tus regiones permitidas, busca una donde se pueda usar un tamaño de VM de la
# lista TAMANIOS y deja el resultado en terraform/terraform.tfvars (para ./up.sh).
#
# Uso: ./elegir-region.sh
set -euo pipefail
cd "$(dirname "$0")"

# Tamaños de 2 vCPU con cuota en Azure for Students, en orden de preferencia, y su familia de cuota.
TAMANIOS=(Standard_B2s_v2 Standard_B2as_v2)
FAMILIAS=(standardBsv2Family standardBasv2Family)
VCPU_NECESARIAS=4 # 2 nodos x 2 vCPU

az account show >/dev/null 2>&1 || { echo "No hay sesión de Azure: corré 'az login'."; exit 1; }

REGIONES=$(az policy assignment list --disable-scope-strict-match \
  --query "[].parameters.listOfAllowedLocations.value[]" -o tsv)
if [ -z "$REGIONES" ]; then
  echo "Tu suscripción no restringe regiones. Probando las más cercanas."
  REGIONES="brazilsouth eastus2"
fi
echo "Regiones permitidas: $(echo $REGIONES)"
echo "Revisando cada una (1-2 minutos por región)..."

# vCPU libres de una cuota ("cores" = total regional, o una familia) en una región.
libres() {
  az vm list-usage -l "$1" --query "[?name.value=='$2'] | [0].[limit, currentValue]" -o tsv |
    tr '\n' ' ' | awk '{ if (NF == 2) print $1 - $2; else print 0 }'
}

for region in $REGIONES; do
  echo -n "  $region: "
  usadas=$(az vm list-usage -l "$region" --query "[?name.value=='cores'] | [0].currentValue" -o tsv)
  if [ "${usadas:-0}" -gt 0 ]; then
    echo -n "(ojo: ya hay $usadas vCPU en uso acá; ¿quedó un cluster prendido? 'az group list -o table') "
  fi
  if [ "$(libres "$region" cores)" -lt "$VCPU_NECESARIAS" ]; then
    echo "sin cuota de vCPU libre"
    continue
  fi
  ofrecidos=$(az vm list-skus -l "$region" --resource-type virtualMachines --size Standard_B2 \
    --query "[?length(restrictions)==\`0\`].name" -o tsv)
  for i in "${!TAMANIOS[@]}"; do
    tamanio=${TAMANIOS[$i]}
    grep -qx "$tamanio" <<<"$ofrecidos" || continue
    [ "$(libres "$region" "${FAMILIAS[$i]}")" -ge "$VCPU_NECESARIAS" ] || continue

    echo "$tamanio disponible ✔"
    tfvars=terraform/terraform.tfvars
    touch "$tfvars"
    # -i.bak funciona igual con el sed de GNU (Linux/WSL) y el de BSD (macOS)
    sed -i.bak '/^location *=/d; /^node_vm_size *=/d' "$tfvars" && rm -f "$tfvars.bak"
    printf 'location     = "%s"\nnode_vm_size = "%s"\n' "$region" "$tamanio" >>"$tfvars"
    echo
    echo "Guardado en $tfvars (lo usa ./up.sh). Para la parte 2 de la guía:"
    echo "  export LOCATION=$region VM_SIZE=$tamanio"
    exit 0
  done
  echo "no ofrece ${TAMANIOS[*]} con cuota"
done

echo
echo "Ninguna región permitida ofrece ${TAMANIOS[*]} con cuota. Avisale a la cátedra con esta salida."
exit 1
