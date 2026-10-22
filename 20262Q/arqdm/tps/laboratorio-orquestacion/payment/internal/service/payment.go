package service

import (
	"context"
	"fmt"
	"log/slog"
	"math/rand"
	"os"
	"strconv"
	"time"

	"github.com/google/uuid"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/payment/internal/messaging/kafka"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/payment/internal/repository/postgres"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/payment/pkg/model"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/schemas"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/schemas/events"
)

type PaymentService struct {
	repository *postgres.PaymentPostgresRepository
	messaging  *kafka.KafkaPublisher
	logger     *slog.Logger

	// Configuration for failure simulation
	failureRate    float64 // 0.0 to 1.0 (0% to 100%)
	enableFailures bool
}

func NewPaymentService(repo *postgres.PaymentPostgresRepository, msg *kafka.KafkaPublisher) *PaymentService {
	service := &PaymentService{
		repository: repo,
		messaging:  msg,
		logger:     slog.Default(),
	}

	// Initialize failure simulation from environment variables
	service.initializeFailureSimulation()

	return service
}

func (s *PaymentService) initializeFailureSimulation() {
	// Enable failures from environment variable (default: true for educational purposes)
	enableFailuresEnv := os.Getenv("PAYMENT_ENABLE_FAILURES")
	s.enableFailures = enableFailuresEnv != "false" // Default to true unless explicitly disabled

	// Set failure rate from environment variable (default: 20%)
	failureRateEnv := os.Getenv("PAYMENT_FAILURE_RATE")
	if failureRateEnv != "" {
		if rate, err := strconv.ParseFloat(failureRateEnv, 64); err == nil {
			s.failureRate = rate
		} else {
			s.failureRate = 0.2 // Default 20%
		}
	} else {
		s.failureRate = 0.2 // Default 20%
	}

	s.logger.Info("payment failure simulation configured",
		slog.Bool("enabled", s.enableFailures),
		slog.Float64("failureRate", s.failureRate))
}

// 2PC Participant Phase 1: PREPARE - Handle prepare request and vote
func (s *PaymentService) HandlePrepareRequest(prepare *events.PrepareEvent) error {
	s.logger.Info("received prepare request",
		slog.String("orderId", prepare.OrderId),
		slog.Float64("amount", prepare.TotalValue))

	ctx := context.Background()

	// Simulate payment processing and validation
	result := s.processPaymentValidation(prepare)

	var vote schemas.Vote
	var reason string

	if result.Success {
		// Create payment record in reserved state
		payment := &model.Payment{
			PaymentId:     uuid.New().String(),
			OrderId:       prepare.OrderId,
			Amount:        prepare.TotalValue,
			UserID:        prepare.UserId,
			PaymentMethod: s.selectPaymentMethod(prepare),
			Status:        "reserved",
		}

		// Reserve the payment (Phase 1)
		if err := s.repository.ReservePayment(prepare.OrderId, payment); err != nil {
			s.logger.Error("failed to reserve payment", slog.String("error", err.Error()))
			vote = schemas.VoteNo
			reason = fmt.Sprintf("Database reservation failed: %s", err.Error())
		} else {
			vote = schemas.VoteYes
			s.logger.Info("payment reserved successfully",
				slog.String("orderId", prepare.OrderId),
				slog.String("paymentId", payment.PaymentId))
		}
	} else {
		vote = schemas.VoteNo
		reason = result.FailureReason

		// Mark payment as failed for audit trail
		if result.FailureType != model.FailureTypeNone {
			s.repository.MarkPaymentFailed(prepare.OrderId, reason)
		}
	}

	// Send vote response to coordinator
	voteEvent := events.VoteEvent{
		OrderId:   prepare.OrderId,
		Service:   string(schemas.ServicePayment),
		Vote:      string(vote),
		Reason:    reason,
		Timestamp: time.Now(),
	}

	if err := s.messaging.PublishVote(ctx, voteEvent); err != nil {
		s.logger.Error("failed to publish vote", slog.String("error", err.Error()))
		return err
	}

	s.logger.Info("vote sent",
		slog.String("orderId", prepare.OrderId),
		slog.String("vote", string(vote)),
		slog.String("reason", reason))

	return nil
}

// 2PC Participant Phase 2: Handle commit/abort decision
func (s *PaymentService) HandleCommitDecision(commit *events.CommitEvent) error {
	s.logger.Info("received commit decision",
		slog.String("orderId", commit.OrderId),
		slog.String("decision", commit.Decision))

	ctx := context.Background()
	var status string

	if commit.Decision == string(schemas.DecisionCommit) {
		// Commit the reserved payment
		if err := s.repository.CommitPayment(commit.OrderId); err != nil {
			s.logger.Error("failed to commit payment", slog.String("error", err.Error()))
			return err
		}
		status = "COMMITTED"
		s.logger.Info("payment committed", slog.String("orderId", commit.OrderId))
	} else {
		// Abort/rollback the reserved payment
		if err := s.repository.AbortPayment(commit.OrderId); err != nil {
			s.logger.Error("failed to abort payment", slog.String("error", err.Error()))
			return err
		}
		status = "ABORTED"
		s.logger.Info("payment aborted", slog.String("orderId", commit.OrderId))
	}

	// Send acknowledgment to coordinator
	ackEvent := events.AckEvent{
		OrderId:   commit.OrderId,
		Service:   string(schemas.ServicePayment),
		Status:    status,
		Timestamp: time.Now(),
	}

	if err := s.messaging.PublishAck(ctx, ackEvent); err != nil {
		s.logger.Error("failed to publish ack", slog.String("error", err.Error()))
		return err
	}

	return nil
}

// PaymentValidationResult represents the result of payment validation
type PaymentValidationResult struct {
	Success       bool
	FailureType   model.FailureType
	FailureReason string
}

// processPaymentValidation simulates payment processing with random failures
func (s *PaymentService) processPaymentValidation(prepare *events.PrepareEvent) PaymentValidationResult {
	// Basic business validation first
	if validationResult := s.validateBusinessRules(prepare); !validationResult.Success {
		return validationResult
	}

	// Simulate payment processing failures if enabled
	if s.enableFailures && s.shouldSimulateFailure() {
		return s.generateRandomFailure(prepare)
	}

	// Simulate processing delay (educational - shows async nature)
	processingDelay := time.Duration(rand.Intn(500)+100) * time.Millisecond
	time.Sleep(processingDelay)

	s.logger.Info("payment validation successful",
		slog.String("orderId", prepare.OrderId))

	return PaymentValidationResult{
		Success:     true,
		FailureType: model.FailureTypeNone,
	}
}

// validateBusinessRules performs basic business rule validation
func (s *PaymentService) validateBusinessRules(prepare *events.PrepareEvent) PaymentValidationResult {
	// Rule 1: Check amount limits
	if prepare.TotalValue <= 0 {
		return PaymentValidationResult{
			Success:       false,
			FailureType:   model.FailureTypeNone,
			FailureReason: "Invalid payment amount: must be greater than 0",
		}
	}

	if prepare.TotalValue > 50000 { // $50,000 limit for demo
		return PaymentValidationResult{
			Success:       false,
			FailureType:   model.FailureTypeInsufficientFunds,
			FailureReason: "Payment amount exceeds maximum limit of $50,000",
		}
	}

	// Rule 2: Check user validation
	if prepare.UserId <= 0 {
		return PaymentValidationResult{
			Success:       false,
			FailureType:   model.FailureTypeNone,
			FailureReason: "Invalid user ID",
		}
	}

	return PaymentValidationResult{Success: true}
}

// shouldSimulateFailure determines if a failure should be simulated based on configured rate
func (s *PaymentService) shouldSimulateFailure() bool {
	return rand.Float64() < s.failureRate
}

// generateRandomFailure creates a random payment failure for educational purposes
func (s *PaymentService) generateRandomFailure(prepare *events.PrepareEvent) PaymentValidationResult {
	failures := []struct {
		failureType model.FailureType
		reason      string
		weight      int // Relative probability
	}{
		{model.FailureTypeInsufficientFunds, "Insufficient funds in account", 30},
		{model.FailureTypeCardExpired, "Payment card has expired", 20},
		{model.FailureTypeFraudDetection, "Transaction flagged by fraud detection system", 15},
		{model.FailureTypeNetworkTimeout, "Payment gateway network timeout", 15},
		{model.FailureTypeServiceUnavailable, "Payment service temporarily unavailable", 10},
	}

	// Calculate total weight
	totalWeight := 0
	for _, failure := range failures {
		totalWeight += failure.weight
	}

	// Select random failure based on weights
	randomValue := rand.Intn(totalWeight)
	currentWeight := 0

	for _, failure := range failures {
		currentWeight += failure.weight
		if randomValue < currentWeight {
			s.logger.Warn("simulating payment failure",
				slog.String("orderId", prepare.OrderId),
				slog.String("failureType", string(failure.failureType)),
				slog.String("reason", failure.reason))

			return PaymentValidationResult{
				Success:       false,
				FailureType:   failure.failureType,
				FailureReason: failure.reason,
			}
		}
	}

	// Fallback (should not reach here)
	return PaymentValidationResult{
		Success:       false,
		FailureType:   model.FailureTypeServiceUnavailable,
		FailureReason: "Unknown payment service error",
	}
}

// selectPaymentMethod selects an appropriate payment method based on amount and user
func (s *PaymentService) selectPaymentMethod(prepare *events.PrepareEvent) string {
	// Educational logic for payment method selection
	if prepare.TotalValue > 1000 {
		return string(model.PaymentMethodBankTransfer) // Large amounts use bank transfer
	} else if prepare.UserId%2 == 0 {
		return string(model.PaymentMethodCreditCard) // Even user IDs use credit card
	} else {
		return string(model.PaymentMethodDebitCard) // Odd user IDs use debit card
	}
}

// GetFailureStatistics returns current failure simulation statistics
func (s *PaymentService) GetFailureStatistics() map[string]interface{} {
	return map[string]interface{}{
		"enabled":     s.enableFailures,
		"failureRate": s.failureRate,
	}
}

// UpdateFailureSettings allows runtime configuration of failure simulation
func (s *PaymentService) UpdateFailureSettings(enabled bool, failureRate float64) {
	s.enableFailures = enabled
	if failureRate >= 0 && failureRate <= 1.0 {
		s.failureRate = failureRate
	}

	s.logger.Info("payment failure settings updated",
		slog.Bool("enabled", s.enableFailures),
		slog.Float64("failureRate", s.failureRate))
}
