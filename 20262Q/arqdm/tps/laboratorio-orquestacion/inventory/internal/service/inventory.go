package service

import (
	"context"
	"log/slog"
	"time"

	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/inventory/internal/messaging/kafka"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/inventory/internal/repository/postgres"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/schemas"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/schemas/events"
)

type InventoryService struct {
	repository *postgres.InventoryPostgresRepository
	messaging  *kafka.KafkaPublisher
	logger     *slog.Logger
}

func NewInventoryService(repo *postgres.InventoryPostgresRepository, msg *kafka.KafkaPublisher) *InventoryService {
	return &InventoryService{
		repository: repo,
		messaging:  msg,
		logger:     slog.Default(),
	}
}

// 2PC Participant Phase 1: PREPARE - Handle prepare request and vote
func (s *InventoryService) HandlePrepareRequest(prepare events.PrepareEvent) error {
	s.logger.Info("received prepare request",
		slog.String("orderId", prepare.OrderId))

	ctx := context.Background()

	// Business logic: Check if inventory can be reserved
	vote := s.canReserveInventory(prepare)

	if vote == schemas.VoteYes {
		// Reserve the inventory (Phase 1)
		if err := s.repository.ReserveInventory(prepare.OrderId, prepare.ProductId, prepare.Quantity); err != nil {
			s.logger.Error("failed to reserve inventory", slog.String("error", err.Error()))
			vote = schemas.VoteNo
		}
	}

	// Send vote response to coordinator
	voteEvent := events.VoteEvent{
		OrderId:   prepare.OrderId,
		Service:   string(schemas.ServiceInventory),
		Vote:      string(vote),
		Timestamp: time.Now(),
	}

	if vote == schemas.VoteNo {
		voteEvent.Reason = "Insufficient stock or business logic validation failed"
	}

	if err := s.publishVote(ctx, voteEvent); err != nil {
		s.logger.Error("failed to publish vote", slog.String("error", err.Error()))
		return err
	}

	s.logger.Info("vote sent",
		slog.String("orderId", prepare.OrderId),
		slog.String("vote", string(vote)))

	return nil
}

// 2PC Participant Phase 2: Handle commit/abort decision
func (s *InventoryService) HandleCommitDecision(commit events.CommitEvent) error {
	s.logger.Info("received commit decision",
		slog.String("orderId", commit.OrderId),
		slog.String("decision", commit.Decision))

	ctx := context.Background()
	var status string

	if commit.Decision == string(schemas.DecisionCommit) {
		// Commit the reserved inventory (reduce stock permanently)
		if err := s.repository.CommitInventory(commit.OrderId); err != nil {
			s.logger.Error("failed to commit inventory", slog.String("error", err.Error()))
			return err
		}
		status = "COMMITTED"
		s.logger.Info("inventory committed", slog.String("orderId", commit.OrderId))
	} else {
		// Abort/rollback the reserved inventory (release reservation)
		if err := s.repository.AbortInventory(commit.OrderId); err != nil {
			s.logger.Error("failed to abort inventory", slog.String("error", err.Error()))
			return err
		}
		status = "ABORTED"
		s.logger.Info("inventory aborted", slog.String("orderId", commit.OrderId))
	}

	// Send acknowledgment to coordinator
	ackEvent := events.AckEvent{
		OrderId:   commit.OrderId,
		Service:   string(schemas.ServiceInventory),
		Status:    status,
		Timestamp: time.Now(),
	}

	if err := s.publishAck(ctx, ackEvent); err != nil {
		s.logger.Error("failed to publish ack", slog.String("error", err.Error()))
		return err
	}

	return nil
}

// Business logic to determine if inventory can be reserved
func (s *InventoryService) canReserveInventory(prepare events.PrepareEvent) schemas.Vote {
	// Educational business rules for demonstration

	// Rule 1: Check quantity limits
	if prepare.Quantity <= 0 || prepare.Quantity > 50 {
		s.logger.Warn("invalid quantity", slog.Int("quantity", prepare.Quantity))
		return schemas.VoteNo
	}

	// Rule 2: Check product validity
	if prepare.ProductId <= 0 {
		s.logger.Warn("invalid product", slog.Int("productId", prepare.ProductId))
		return schemas.VoteNo
	}

	// Rule 3: Check if we have the product in inventory
	inventory, err := s.repository.GetInventory(prepare.ProductId)
	if err != nil {
		s.logger.Warn("product not found in inventory", slog.Int("productId", prepare.ProductId))
		return schemas.VoteNo
	}

	// Rule 4: Check if we have enough available stock
	if inventory.AvailableStock < prepare.Quantity {
		s.logger.Warn("insufficient stock",
			slog.Int("available", inventory.AvailableStock),
			slog.Int("requested", prepare.Quantity))
		return schemas.VoteNo
	}

	s.logger.Info("inventory validation passed",
		slog.Int("productId", prepare.ProductId),
		slog.Int("available", inventory.AvailableStock),
		slog.Int("requested", prepare.Quantity))
	return schemas.VoteYes
}

// Kafka publishing methods
func (s *InventoryService) publishVote(ctx context.Context, vote events.VoteEvent) error {
	return s.messaging.PublishVote(ctx, vote)
}

func (s *InventoryService) publishAck(ctx context.Context, ack events.AckEvent) error {
	return s.messaging.PublishAck(ctx, ack)
}

// Utility method to get current inventory status
func (s *InventoryService) GetInventoryStatus(productId int) (*InventoryStatus, error) {
	inventory, err := s.repository.GetInventory(productId)
	if err != nil {
		return nil, err
	}

	return &InventoryStatus{
		ProductId:      inventory.ProductId,
		TotalStock:     inventory.TotalStock,
		ReservedStock:  inventory.ReservedStock,
		AvailableStock: inventory.AvailableStock,
	}, nil
}

// InventoryStatus represents the current status of inventory for external use
type InventoryStatus struct {
	ProductId      int `json:"product_id"`
	TotalStock     int `json:"total_stock"`
	ReservedStock  int `json:"reserved_stock"`
	AvailableStock int `json:"available_stock"`
}
