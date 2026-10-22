package handler

import (
	"fmt"

	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/sale/internal/service"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/schemas/events"
)

type Handler struct {
	service *service.SaleService
}

func NewSaleHandler(svc *service.SaleService) *Handler {
	return &Handler{service: svc}
}

func (h *Handler) HandlePrepareEvent(event *events.PrepareEvent) error {
	fmt.Println("\n=== Sale Prepare Event Received ===")
	fmt.Printf("Order ID: %s\n", event.OrderId)
	fmt.Printf("Customer: %d\n", event.UserId)
	fmt.Printf("Product ID: %d\n", event.ProductId)
	fmt.Printf("Quantity: %d\n", event.Quantity)
	fmt.Printf("Total Value: %.2f\n", event.TotalValue)
	return h.service.HandlePrepareRequest(*event)
}

func (h *Handler) HandleCommitEvent(event *events.CommitEvent) error {
	fmt.Println("\n=== Sale Commit Event Received ===")
	fmt.Printf("Order ID: %s\n", event.OrderId)
	fmt.Printf("Decision: %s\n", event.Decision)
	return h.service.HandleCommitDecision(*event)
}
