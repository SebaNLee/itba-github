package service

import (
	"context"
	"fmt"
	"log/slog"
	"time"

	"github.com/google/uuid"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/checkout/internal/messaging/kafka"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/checkout/internal/repository/postgres"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/checkout/pkg/model/checkout"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/schemas"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/schemas/events"
)

func NewCheckoutService(repo *postgres.CheckoutPostgresRepository, publisher *kafka.KafkaPublisher) *CheckoutService {
	return &CheckoutService{repository: repo, publisher: publisher, logger: slog.Default()}
}

type CheckoutService struct {
	repository *postgres.CheckoutPostgresRepository
	publisher  *kafka.KafkaPublisher
	logger     *slog.Logger
}

// ParticipantStatus represents the status of a participant in a transaction
type ParticipantStatus struct {
	Service   schemas.ServiceType      `json:"service"`
	State     schemas.ParticipantState `json:"state"`
	Vote      *schemas.Vote            `json:"vote,omitempty"`
	CreatedAt time.Time                `json:"createdAt"`
	UpdatedAt time.Time                `json:"updatedAt"`
}

// CheckoutStatusResponse provides comprehensive status information
type CheckoutStatusResponse struct {
	OrderId      string                   `json:"orderId"`
	State        schemas.TransactionState `json:"state"`
	ProductId    int                      `json:"productId"`
	Quantity     int                      `json:"quantity"`
	UserId       int                      `json:"userId"`
	UnitPrice    float64                  `json:"unitPrice"`
	TotalValue   float64                  `json:"totalValue"`
	CreatedAt    time.Time                `json:"createdAt"`
	ExpiresAt    time.Time                `json:"expiresAt"`
	Participants []ParticipantStatus      `json:"participants"`
}

// CreateTransaction initiates a new distributed transaction using 2PC protocol
func (s *CheckoutService) CreateTransaction(request events.TransactionRequest) (*checkout.Checkout, error) {
	ctx := context.Background()

	orderId := uuid.New().String()

	checkoutRecord := &checkout.Checkout{
		OrderId:    orderId,
		State:      schemas.TransactionStateInitiated,
		ExpiresAt:  time.Now().Add(30 * time.Second),
		ProductId:  request.ProductId,
		Quantity:   request.Quantity,
		UserId:     request.UserId,
		UnitPrice:  request.UnitPrice,
		TotalValue: request.TotalValue,
	}

	dbTx := s.repository.BeginTransaction()
	txRepo := s.repository.WithTransaction(dbTx)

	if err := txRepo.CreateCheckout(checkoutRecord); err != nil {
		dbTx.Rollback()
		return nil, fmt.Errorf("failed to create checkout: %w", err)
	}

	participants := []schemas.ServiceType{
		schemas.ServiceSale,
		schemas.ServicePayment,
		schemas.ServiceInventory,
	}

	for _, service := range participants {
		participant := &checkout.Participant{
			CheckoutID: checkoutRecord.ID,
			OrderId:    orderId,
			Service:    service,
			State:      schemas.ParticipantStateIdle,
		}
		if err := txRepo.CreateParticipant(participant); err != nil {
			dbTx.Rollback()
			return nil, fmt.Errorf("failed to create participant %s: %w", service, err)
		}
	}

	if err := dbTx.Commit().Error; err != nil {
		return nil, fmt.Errorf("failed to commit transaction: %w", err)
	}

	s.logger.Info("transaction created",
		slog.String("orderId", orderId))

	go s.startPreparePhase(ctx, checkoutRecord)

	return checkoutRecord, nil
}

func (s *CheckoutService) startPreparePhase(ctx context.Context, checkout *checkout.Checkout) {
	s.logger.Info("starting prepare phase",
		slog.String("orderId", checkout.OrderId))

	if err := s.repository.UpdateCheckoutState(checkout.OrderId, schemas.TransactionStatePreparing); err != nil {
		s.logger.Error("failed to update checkout state", slog.String("error", err.Error()))
		return
	}

	prepareEvent := events.PrepareEvent{
		OrderId:    checkout.OrderId,
		ProductId:  checkout.ProductId,
		Quantity:   checkout.Quantity,
		UserId:     checkout.UserId,
		UnitPrice:  checkout.UnitPrice,
		TotalValue: checkout.TotalValue,
		Timestamp:  time.Now(),
	}

	if err := s.publishPrepareEvent(ctx, prepareEvent); err != nil {
		s.logger.Error("failed to publish prepare event", slog.String("error", err.Error()))
		// Abort transaction on prepare failure
		s.abortTransaction(ctx, checkout.OrderId)
		return
	}

	go s.monitorTransactionTimeout(ctx, checkout.OrderId, checkout.ExpiresAt)
}

func (s *CheckoutService) HandleVote(vote events.VoteEvent) error {
	s.logger.Info("received vote",
		slog.String("orderId", vote.OrderId),
		slog.String("service", vote.Service),
		slog.String("vote", vote.Vote))

	_, err := s.repository.GetCheckoutByOrderId(vote.OrderId)
	if err != nil {
		return fmt.Errorf("failed to get checkout by order ID %s: %w", vote.OrderId, err)
	}

	// Update participant vote
	serviceType := schemas.ServiceType(vote.Service)
	voteType := schemas.Vote(vote.Vote)

	if err := s.repository.UpdateParticipantVote(
		vote.OrderId,
		serviceType,
		voteType,
		schemas.ParticipantStatePrepared,
	); err != nil {
		return fmt.Errorf("failed to update participant vote: %w", err)
	}

	updatedCheckout, err := s.repository.GetCheckoutWithParticipants(vote.OrderId)
	if err != nil {
		return fmt.Errorf("failed to refetch checkout after vote update: %w", err)
	}

	allVoted := true
	allYes := true
	for _, participant := range updatedCheckout.Participants {
		if participant.Vote == nil {
			allVoted = false
			break
		}
		if *participant.Vote == schemas.VoteNo {
			allYes = false
		}
	}

	if allVoted {
		if allYes {
			s.commitTransaction(context.Background(), vote.OrderId)
		} else {
			s.abortTransaction(context.Background(), vote.OrderId)
		}
	}

	return nil
}

// HandleAck processes acknowledgment messages from participants
func (s *CheckoutService) HandleAck(ack events.AckEvent) error {
	s.logger.Info("received ack",
		slog.String("orderId", ack.OrderId),
		slog.String("service", ack.Service),
		slog.String("status", ack.Status))

	serviceType := schemas.ServiceType(ack.Service)

	s.logger.Info("participant acknowledged transaction completion",
		slog.String("orderId", ack.OrderId),
		slog.String("service", string(serviceType)),
		slog.String("status", ack.Status))

	return nil
}

func (s *CheckoutService) commitTransaction(ctx context.Context, orderId string) {
	s.logger.Info("committing transaction", slog.String("orderId", orderId))

	if err := s.repository.UpdateCheckoutState(orderId, schemas.TransactionStateCommitted); err != nil {
		s.logger.Error("failed to update checkout state", slog.String("error", err.Error()))
		return
	}

	commitEvent := events.CommitEvent{
		OrderId:   orderId,
		Decision:  string(schemas.DecisionCommit),
		Timestamp: time.Now(),
	}

	if err := s.publishCommitEvent(ctx, commitEvent); err != nil {
		s.logger.Error("failed to publish commit event", slog.String("error", err.Error()))
	}
}

func (s *CheckoutService) abortTransaction(ctx context.Context, orderId string) {
	s.logger.Info("aborting transaction", slog.String("orderId", orderId))

	if err := s.repository.UpdateCheckoutState(orderId, schemas.TransactionStateAborted); err != nil {
		s.logger.Error("failed to update checkout state", slog.String("error", err.Error()))
		return
	}

	commitEvent := events.CommitEvent{
		OrderId:   orderId,
		Decision:  string(schemas.DecisionAbort),
		Timestamp: time.Now(),
	}

	if err := s.publishCommitEvent(ctx, commitEvent); err != nil {
		s.logger.Error("failed to publish abort event", slog.String("error", err.Error()))
	}
}

// Monitor transaction timeout
func (s *CheckoutService) monitorTransactionTimeout(ctx context.Context, orderId string, expiresAt time.Time) {
	time.Sleep(time.Until(expiresAt))

	// Check if transaction is still in progress
	checkout, err := s.repository.GetCheckoutByOrderId(orderId)
	if err != nil {
		return
	}

	if checkout.State == schemas.TransactionStatePreparing {
		s.logger.Warn("transaction timeout", slog.String("orderId", orderId))
		s.repository.UpdateCheckoutState(orderId, schemas.TransactionStateTimeout)
		s.abortTransaction(ctx, orderId)
	}
}

// Kafka publishing methods
func (s *CheckoutService) publishPrepareEvent(ctx context.Context, event events.PrepareEvent) error {
	// Publish proper PrepareEvent instead of converting to CheckoutCreatedEvent
	return s.publisher.PublishPrepareEvent(ctx, event)
}

func (s *CheckoutService) publishCommitEvent(ctx context.Context, event events.CommitEvent) error {
	// Publish proper CommitEvent for 2PC Phase 2
	return s.publisher.PublishCommitEvent(ctx, event)
}

// GetCheckoutStatus retrieves comprehensive status information for a checkout
func (s *CheckoutService) GetCheckoutStatus(orderId string) (*CheckoutStatusResponse, error) {
	s.logger.Info("retrieving checkout status", slog.String("orderId", orderId))

	// Get checkout with participants
	checkout, err := s.repository.GetCheckoutWithParticipants(orderId)
	if err != nil {
		return nil, fmt.Errorf("failed to get checkout: %w", err)
	}

	// Convert participants
	participants := make([]ParticipantStatus, len(checkout.Participants))
	for i, p := range checkout.Participants {
		participants[i] = ParticipantStatus{
			Service:   p.Service,
			State:     p.State,
			Vote:      p.Vote,
			CreatedAt: p.CreatedAt,
			UpdatedAt: p.UpdatedAt,
		}
	}

	response := &CheckoutStatusResponse{
		OrderId:      checkout.OrderId,
		State:        checkout.State,
		ProductId:    checkout.ProductId,
		Quantity:     checkout.Quantity,
		UserId:       checkout.UserId,
		UnitPrice:    checkout.UnitPrice,
		TotalValue:   checkout.TotalValue,
		CreatedAt:    checkout.CreatedAt,
		ExpiresAt:    checkout.ExpiresAt,
		Participants: participants,
	}

	return response, nil
}
