package handler

import (
	"encoding/json"
	"fmt"
	"net/http"
	"strconv"

	"github.com/gorilla/mux"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/inventory/internal/service"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/schemas/events"
)

type Handler struct {
	service *service.InventoryService
}

func NewInventoryHandler(svc *service.InventoryService) *Handler {
	return &Handler{service: svc}
}

// Kafka event handler for prepare events
func (h *Handler) HandlePrepareEvent(event *events.PrepareEvent) error {
	fmt.Println("\n=== Inventory Prepare Event Received ===")
	fmt.Printf("Order ID: %s\n", event.OrderId)
	fmt.Printf("Customer: %d\n", event.UserId)
	fmt.Printf("Product ID: %d\n", event.ProductId)
	fmt.Printf("Quantity: %d\n", event.Quantity)
	fmt.Printf("Total Value: %.2f\n", event.TotalValue)
	return h.service.HandlePrepareRequest(*event)
}

// Kafka event handler for commit events
func (h *Handler) HandleCommitEvent(event *events.CommitEvent) error {
	fmt.Println("\n=== Inventory Commit Event Received ===")
	fmt.Printf("Order ID: %s\n", event.OrderId)
	fmt.Printf("Decision: %s\n", event.Decision)
	return h.service.HandleCommitDecision(*event)
}

// HTTP handlers for inventory management
func (h *Handler) GetInventoryStatus(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	productIdStr := vars["productId"]

	productId, err := strconv.Atoi(productIdStr)
	if err != nil {
		http.Error(w, "Invalid product ID", http.StatusBadRequest)
		return
	}

	status, err := h.service.GetInventoryStatus(productId)
	if err != nil {
		http.Error(w, fmt.Sprintf("Failed to get inventory status: %v", err), http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	json.NewEncoder(w).Encode(status)
}
