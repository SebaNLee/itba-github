# Sufijo aleatorio: los nombres de AKS y su DNS tienen que ser únicos.
resource "random_string" "suffix" {
  length  = 5
  lower   = true
  numeric = true
  special = false
  upper   = false
}

locals {
  name = "${var.project_name}-${random_string.suffix.result}"

  tags = merge(
    {
      curso      = "73.40"
      managed_by = "terraform"
    },
    var.tags
  )
}

resource "azurerm_resource_group" "main" {
  name     = "rg-${local.name}"
  location = var.location
  tags     = local.tags
}

# Es el mismo cluster que la parte 2 del README crea con `az aks create`, flag por flag.
resource "azurerm_kubernetes_cluster" "main" {
  name                = "aks-${local.name}"
  location            = azurerm_resource_group.main.location
  resource_group_name = azurerm_resource_group.main.name
  dns_prefix          = local.name

  # Grupo donde Azure pone lo que el cluster crea: VMs de los nodos, balanceador, IPs, discos.
  node_resource_group = "rg-${local.name}-nodos"

  # Free: el control plane no se cobra (sin SLA). Se pagan los nodos y lo que creen.
  sku_tier = "Free"

  default_node_pool {
    name            = "system"
    node_count      = var.node_count
    vm_size         = var.node_vm_size
    os_disk_size_gb = var.node_os_disk_size_gb

    # Cambiar vm_size sobre un cluster existente obliga a rotar el pool por uno temporal.
    temporary_name_for_rotation = "temp"
  }

  identity {
    type = "SystemAssigned"
  }

  # Azure CNI Overlay: los pods toman IPs de una red propia (overlay), no de la VNet.
  # Cilium (eBPF) reemplaza a kube-proxy y es el que hace cumplir las NetworkPolicy.
  network_profile {
    network_plugin      = "azure"
    network_plugin_mode = "overlay"
    network_data_plane  = "cilium"
    network_policy      = "cilium"
    load_balancer_sku   = "standard"
  }

  role_based_access_control_enabled = true

  tags = local.tags
}
