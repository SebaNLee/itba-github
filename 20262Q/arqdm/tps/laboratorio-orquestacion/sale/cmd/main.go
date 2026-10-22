package main

import (
	"context"
	"fmt"
	"log/slog"
	"os"
	"os/signal"
	"syscall"

	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/sale/internal/messaging/kafka"
)

func main() {
	fmt.Println("Checkout consumer service starting...")

	// Initialize logger (if needed)
	logger := slog.New(slog.NewTextHandler(os.Stdout, &slog.HandlerOptions{
		Level: slog.LevelInfo,
	}))

	ctx, cancel := context.WithCancel(context.Background())
	defer cancel()

	kafkaConsumer := kafka.NewKafkaConsumer(nil)
	defer kafkaConsumer.Close()

	// Graceful shutdown
	sigChan := make(chan os.Signal, 1)
	signal.Notify(sigChan, syscall.SIGINT, syscall.SIGTERM)

	go func() {
		<-sigChan
		fmt.Println("\nShutting down...")
		cancel()
	}()

	// Use Wire to initialize all dependencies
	handler, err := InitializeApplication(nil)
	if err != nil {
		logger.Error("Failed to initialize application", "error", err)
		os.Exit(1)
	}

	fmt.Println("Consumer ready, waiting for messages... (Press Ctrl+C to stop)")

	// Start unified 2PC events consumer
	go func() {
		if err := kafkaConsumer.Consume2PCEvents(ctx, handler.HandlePrepareEvent, handler.HandleCommitEvent); err != nil {
			if err == context.Canceled {
				fmt.Println("2PC consumer stopped gracefully")
			} else {
				fmt.Printf("2PC consumer error: %v\n", err)
			}
		}
	}()

	// Wait for shutdown signal
	<-ctx.Done()

	fmt.Println("Service shutdown complete")
}
