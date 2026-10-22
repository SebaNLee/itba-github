package service

import (
	"context"
	"log/slog"
	"time"

	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/sale/internal/messaging/kafka"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/sale/internal/repository/postgres"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/sale/pkg/model"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/schemas"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/schemas/events"
)

type SaleService struct {
	repository *postgres.SalePostgresRepository
	messaging  *kafka.KafkaPublisher
	logger     *slog.Logger
}

func NewSaleService(repo *postgres.SalePostgresRepository, msg *kafka.KafkaPublisher) *SaleService {
	return &SaleService{
		repository: repo,
		messaging:  msg,
		logger:     slog.Default(),
	}
}

// 2PC Participant Phase 1: PREPARE - Handle prepare request and vote
func (s *SaleService) HandlePrepareRequest(prepare events.PrepareEvent) error {
	s.logger.Info("received prepare request",
		slog.String("orderId", prepare.OrderId))

	ctx := context.Background()

	// Business logic: Check if sale can be processed
	vote := s.canProcessSale(prepare)

	if vote == schemas.VoteYes {
		// Create sale record in reserved state
		sale := &model.Sale{
			SaleId:    prepare.OrderId,
			OrderId:   prepare.OrderId,
			ProductId: prepare.ProductId,
			Quantity:  prepare.Quantity,
			Amount:    prepare.TotalValue,
			UserID:    prepare.UserId,
		}

		// Reserve the sale (Phase 1)
		if err := s.repository.ReserveSale(prepare.OrderId, sale); err != nil {
			s.logger.Error("failed to reserve sale", slog.String("error", err.Error()))
			vote = schemas.VoteNo
		}
	}

	// Send vote response to coordinator
	voteEvent := events.VoteEvent{
		OrderId:   prepare.OrderId,
		Service:   string(schemas.ServiceSale),
		Vote:      string(vote),
		Timestamp: time.Now(),
	}

	if vote == schemas.VoteNo {
		voteEvent.Reason = "Business logic validation failed or resource reservation failed"
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
func (s *SaleService) HandleCommitDecision(commit events.CommitEvent) error {
	s.logger.Info("received commit decision",
		slog.String("orderId", commit.OrderId),
		slog.String("decision", commit.Decision))

	ctx := context.Background()
	var status string

	if commit.Decision == string(schemas.DecisionCommit) {
		// Commit the reserved sale
		if err := s.repository.CommitSale(commit.OrderId); err != nil {
			s.logger.Error("failed to commit sale", slog.String("error", err.Error()))
			return err
		}
		status = "COMMITTED"
		s.logger.Info("sale committed", slog.String("orderId", commit.OrderId))
	} else {
		// Abort/rollback the reserved sale
		if err := s.repository.AbortSale(commit.OrderId); err != nil {
			s.logger.Error("failed to abort sale", slog.String("error", err.Error()))
			return err
		}
		status = "ABORTED"
		s.logger.Info("sale aborted", slog.String("orderId", commit.OrderId))
	}

	// Send acknowledgment to coordinator
	ackEvent := events.AckEvent{
		OrderId:   commit.OrderId,
		Service:   string(schemas.ServiceSale),
		Status:    status,
		Timestamp: time.Now(),
	}

	if err := s.publishAck(ctx, ackEvent); err != nil {
		s.logger.Error("failed to publish ack", slog.String("error", err.Error()))
		return err
	}

	return nil
}

// Business logic to determine if sale can be processed
func (s *SaleService) canProcessSale(prepare events.PrepareEvent) schemas.Vote {
	// Educational business rules for demonstration

	// Rule 1: Check quantity limits
	if prepare.Quantity <= 0 || prepare.Quantity > 100 {
		s.logger.Warn("invalid quantity", slog.Int("quantity", prepare.Quantity))
		return schemas.VoteNo
	}

	// Rule 2: Check amount limits
	if prepare.TotalValue <= 0 || prepare.TotalValue > 10000 {
		s.logger.Warn("invalid amount", slog.Float64("amount", prepare.TotalValue))
		return schemas.VoteNo
	}

	// Rule 3: Check user limits (simplified)
	if prepare.UserId <= 0 {
		s.logger.Warn("invalid user", slog.Int("userId", prepare.UserId))
		return schemas.VoteNo
	}

	// Rule 4: Check product availability (simplified - always available for demo)
	if prepare.ProductId <= 0 {
		s.logger.Warn("invalid product", slog.Int("productId", prepare.ProductId))
		return schemas.VoteNo
	}

	s.logger.Info("sale validation passed")
	return schemas.VoteYes
}

// Kafka publishing methods
func (s *SaleService) publishVote(ctx context.Context, vote events.VoteEvent) error {
	return s.messaging.PublishVote(ctx, vote)
}

func (s *SaleService) publishAck(ctx context.Context, ack events.AckEvent) error {
	return s.messaging.PublishAck(ctx, ack)
}
