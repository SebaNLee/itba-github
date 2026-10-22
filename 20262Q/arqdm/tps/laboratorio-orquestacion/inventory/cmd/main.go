package main

import (
	"context"
	"fmt"
	"log/slog"
	"os"
	"os/signal"
	"syscall"

	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/inventory/internal/messaging/kafka"
)

func main() {
	fmt.Println("Inventory service starting...")

	// Initialize logger
	logger := slog.New(slog.NewTextHandler(os.Stdout, &slog.HandlerOptions{
		Level: slog.LevelInfo,
	}))

	ctx, cancel := context.WithCancel(context.Background())
	defer cancel()

	// Initialize Kafka consumer
	kafkaConsumer := kafka.NewKafkaConsumer(nil)
	defer kafkaConsumer.Close()

	// Use Wire to initialize all dependencies
	handler, err := InitializeApplication(nil)
	if err != nil {
		logger.Error("Failed to initialize application", "error", err)
		os.Exit(1)
	}

	// Start unified Kafka consumer for 2PC events
	go func() {
		fmt.Println("Kafka consumer ready, waiting for messages...")
		if err := kafkaConsumer.Consume2PCEvents(ctx, handler.HandlePrepareEvent, handler.HandleCommitEvent); err != nil {
			if err != context.Canceled {
				logger.Error("Kafka 2PC consumer error", "error", err)
			}
		}
	}()

	// Graceful shutdown
	sigChan := make(chan os.Signal, 1)
	signal.Notify(sigChan, syscall.SIGINT, syscall.SIGTERM)

	<-sigChan
	fmt.Println("\nShutting down inventory service...")
	cancel()

	fmt.Println("Inventory service shutdown complete")
}
