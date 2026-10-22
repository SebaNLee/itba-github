package main

import (
	"context"
	"log/slog"
	"os"

	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/checkout/internal/router"
)

func main() {
	logger := slog.New(slog.NewTextHandler(os.Stdout, &slog.HandlerOptions{
		Level: slog.LevelInfo,
	}))

	app, err := InitializeApplication(nil, nil)
	if err != nil {
		logger.Error("Failed to initialize application", "error", err)
		os.Exit(1)
	}

	// consumer in a goroutine
	go func() {
		logger.Info("Starting Kafka consumer for 2PC vote and ack events")
		ctx := context.Background()
		if err := app.Consumer.ConsumeVoteEvents(ctx, app.Service.HandleVote, app.Service.HandleAck); err != nil {
			logger.Error("Consumer error", "error", err)
		}
	}()

	r := router.ProvideRouter(app.Handler)

	logger.Info("Server starting on port 8080")
	if err := r.Run(":8080"); err != nil {
		logger.Error("Failed to start server", "error", err)
		os.Exit(1)
	}
}
