package handler

import (
	"github.com/gin-gonic/gin"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/checkout/internal/service"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/schemas/events"
)

func NewCheckoutHandler(svc *service.CheckoutService) *CheckoutHandler {
	return &CheckoutHandler{svc: svc}
}

type CheckoutRequest struct {
	ProductId int     `json:"productId"`
	Quantity  int     `json:"quantity"`
	UserId    int     `json:"userId"`
	Amount    float64 `json:"amount"`
	UnitPrice float64 `json:"unitPrice"`
}

type CheckoutHandler struct {
	svc *service.CheckoutService
}

func (h *CheckoutHandler) HandleCheckout(c *gin.Context) {
	var request CheckoutRequest
	if err := c.ShouldBindJSON(&request); err != nil {
		c.JSON(400, gin.H{"error": "Invalid request"})
		return
	}

	transactionRequest := events.TransactionRequest{
		ProductId:  request.ProductId,
		Quantity:   request.Quantity,
		UserId:     request.UserId,
		UnitPrice:  request.UnitPrice,
		TotalValue: request.Amount,
	}

	transaction, err := h.svc.CreateTransaction(transactionRequest)

	if err != nil {
		c.JSON(500, gin.H{"error": "Failed to create checkout"})
		return
	}

	c.JSON(200, gin.H{"message": "Checkout created successfully", "orderId": transaction.OrderId})
}

func (h *CheckoutHandler) HandleGetCheckoutStatus(c *gin.Context) {
	orderId := c.Param("orderId")
	if orderId == "" {
		c.JSON(400, gin.H{"error": "Order ID is required"})
		return
	}

	status, err := h.svc.GetCheckoutStatus(orderId)
	if err != nil {
		c.JSON(404, gin.H{"error": "Checkout not found"})
		return
	}

	c.JSON(200, status)
}
