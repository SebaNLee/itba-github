output "resource_group_name" {
  description = "Resource group del cluster."
  value       = azurerm_resource_group.main.name
}

output "cluster_name" {
  description = "Nombre del cluster AKS."
  value       = azurerm_kubernetes_cluster.main.name
}

output "node_resource_group" {
  description = "Resource group que administra AKS: nodos, balanceador, IPs públicas, discos."
  value       = azurerm_kubernetes_cluster.main.node_resource_group
}

output "get_credentials_command" {
  description = "Comando para configurar kubectl contra este cluster."
  value       = "az aks get-credentials --resource-group ${azurerm_resource_group.main.name} --name ${azurerm_kubernetes_cluster.main.name} --overwrite-existing"
}
