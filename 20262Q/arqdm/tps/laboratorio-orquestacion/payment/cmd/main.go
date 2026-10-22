package main

import (
	"context"
	"log/slog"
	"os"
	"os/signal"
	"sync"
	"syscall"
	"time"
)

func main() {
	logger := slog.New(slog.NewTextHandler(os.Stdout, &slog.HandlerOptions{
		Level: slog.LevelInfo,
	}))

	logger.Info("Starting Payment Service (2PC Participant)")
	logger.Info("Payment service will process 2PC events and mock payment operations")

	// Initialize all dependencies using Wire
	app, err := InitializeApplication()
	if err != nil {
		logger.Error("Failed to initialize application", "error", err)
		os.Exit(1)
	}

	// Create context for graceful shutdown
	ctx, cancel := context.WithCancel(context.Background())
	defer cancel()

	// Wait group for goroutines
	var wg sync.WaitGroup

	// Start unified Kafka consumer for 2PC events
	logger.Info("Starting Kafka consumers for 2PC events")

	// Consumer for both prepare and commit/abort events
	wg.Add(1)
	go func() {
		defer wg.Done()
		logger.Info("Starting 2PC events consumer")
		if err := app.Consumer.Consume2PCEvents(ctx, app.Service.HandlePrepareRequest, app.Service.HandleCommitDecision); err != nil {
			if ctx.Err() == nil { // Only log if not cancelled
				logger.Error("2PC events consumer error", "error", err)
			}
		}
		logger.Info("2PC events consumer stopped")
	}()

	// Log payment service configuration
	stats := app.Service.GetFailureStatistics()
	logger.Info("Payment failure simulation configured",
		slog.Any("failureSimulation", stats))

	logger.Info("Payment service is ready to process 2PC transactions")
	logger.Info("Listening for checkout events on Kafka...")

	// Wait for interrupt signal for graceful shutdown
	sigChan := make(chan os.Signal, 1)
	signal.Notify(sigChan, os.Interrupt, syscall.SIGTERM)

	// Block until we receive a signal
	<-sigChan
	logger.Info("Shutdown signal received, starting graceful shutdown...")

	// Cancel context to stop all goroutines
	cancel()

	// Give goroutines time to finish
	done := make(chan struct{})
	go func() {
		wg.Wait()
		close(done)
	}()

	// Wait for graceful shutdown or timeout
	select {
	case <-done:
		logger.Info("All goroutines finished")
	case <-time.After(30 * time.Second):
		logger.Warn("Timeout waiting for goroutines to finish")
	}

	// Close Kafka consumer
	if err := app.Consumer.Close(); err != nil {
		logger.Error("Error closing consumer", "error", err)
	}

	logger.Info("Payment service shutdown complete")
}
