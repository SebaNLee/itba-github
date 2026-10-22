package router

import (
	"github.com/gin-gonic/gin"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/checkout/internal/handler"
)

func ProvideRouter(
	checkout *handler.CheckoutHandler,
) *gin.Engine {
	return New(checkout)
}

func New(checkout *handler.CheckoutHandler) *gin.Engine {
	// Set debug mode to see registered routes
	gin.SetMode(gin.DebugMode)

	// Create a new Gin router
	router := gin.Default()

	// Define a simple GET route
	router.GET("/", func(c *gin.Context) {
		c.String(200, "Hello, World!")
	})

	CheckoutEndpoints(router, checkout)

	// Debug: Print all registered routes
	for _, route := range router.Routes() {
		println("Registered route:", route.Method, route.Path)
	}

	return router
}

func CheckoutEndpoints(r *gin.Engine, checkout *handler.CheckoutHandler) {
	// Define the checkout endpoint
	r.POST("/checkout", func(c *gin.Context) {
		checkout.HandleCheckout(c)
	})

	// Define the checkout status endpoint
	r.GET("/checkout/:orderId/status", func(c *gin.Context) {
		checkout.HandleGetCheckoutStatus(c)
	})
}
