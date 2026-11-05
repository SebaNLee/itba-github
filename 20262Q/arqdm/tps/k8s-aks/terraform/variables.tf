variable "project_name" {
  description = "Prefijo de los recursos de Azure. Minúsculas, números y guiones."
  type        = string
  default     = "figuritas"

  validation {
    condition     = can(regex("^[a-z][a-z0-9-]{1,20}[a-z0-9]$", var.project_name))
    error_message = "project_name: 3 a 22 caracteres, minúsculas, números y guiones; empieza con letra."
  }
}

# Sin default: cada suscripción de Azure for Students tiene otras regiones permitidas.
# ./elegir-region.sh la encuentra y la escribe en terraform.tfvars.
variable "location" {
  description = "Región de Azure permitida para tu suscripción (ver README, parte 1.3)."
  type        = string
}

variable "node_count" {
  description = "Cantidad de nodos. Cada nodo consume crédito por hora mientras el cluster existe."
  type        = number
  default     = 2

  validation {
    condition     = var.node_count >= 1 && var.node_count <= 3
    error_message = "node_count tiene que estar entre 1 y 3 (cuota de vCPU de Azure for Students)."
  }
}

variable "node_vm_size" {
  description = "Tamaño de VM de los nodos. Standard_B2s_v2 = 2 vCPU / 8 GiB, permitido para AKS en Azure for Students."
  type        = string
  default     = "Standard_B2s_v2"
}

variable "node_os_disk_size_gb" {
  description = "Disco de sistema por nodo, en GB. 30 es el mínimo de AKS."
  type        = number
  default     = 30
}

variable "tags" {
  description = "Tags extra para los recursos."
  type        = map(string)
  default     = {}
}
