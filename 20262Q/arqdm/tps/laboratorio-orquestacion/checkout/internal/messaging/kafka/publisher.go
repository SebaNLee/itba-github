package kafka

import (
	"context"
	"encoding/json"
	"fmt"
	"log/slog"
	"os"
	"strings"
	"time"

	kafka "github.com/segmentio/kafka-go"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/schemas/events"
)

type KafkaPublisher struct {
	writer *kafka.Writer
	logger *slog.Logger
}

type PublisherConfig struct {
	Brokers []string
	Topic   string
}

func NewKafkaPublisher(config *PublisherConfig) *KafkaPublisher {
	if config == nil {
		config = getDefaultPublisherConfig()
	}

	writer := &kafka.Writer{
		Addr:         kafka.TCP(config.Brokers...),
		Topic:        config.Topic,
		Balancer:     &kafka.Hash{},
		RequiredAcks: kafka.RequireAll,
		Async:        false,
		BatchTimeout: 10 * time.Millisecond,
	}

	return &KafkaPublisher{
		writer: writer,
		logger: slog.Default(),
	}
}

func getDefaultPublisherConfig() *PublisherConfig {
	brokers := os.Getenv("KAFKA_BROKERS")
	if brokers == "" {
		brokers = "localhost:9092"
	}
	fmt.Println("Using Kafka brokers for publisher:", brokers)

	return &PublisherConfig{
		Brokers: strings.Split(brokers, ","),
		Topic:   getEnvOrDefault("KAFKA_TOPIC", "checkout-events"),
	}
}

func (k *KafkaPublisher) PublishPrepareEvent(ctx context.Context, prepare events.PrepareEvent) error {
	message, err := json.Marshal(prepare)
	if err != nil {
		return fmt.Errorf("failed to marshal prepare event: %w", err)
	}

	k.logger.Info("publishing prepare event",
		slog.String("orderId", prepare.OrderId),
		slog.Float64("totalValue", prepare.TotalValue),
		slog.String("topic", k.writer.Topic))

	kafkaMsg := kafka.Message{
		Key:   []byte(prepare.OrderId),
		Value: message,
		Headers: []kafka.Header{
			{Key: "event_type", Value: []byte("prepare")},
			{Key: "timestamp", Value: []byte(time.Now().Format(time.RFC3339))},
		},
	}

	if err := k.writer.WriteMessages(ctx, kafkaMsg); err != nil {
		return fmt.Errorf("failed to write prepare message: %w", err)
	}

	k.logger.Info("published prepare event",
		slog.String("orderId", prepare.OrderId),
		slog.String("topic", k.writer.Topic))

	return nil
}

func (k *KafkaPublisher) PublishCommitEvent(ctx context.Context, commit events.CommitEvent) error {
	message, err := json.Marshal(commit)
	if err != nil {
		return fmt.Errorf("failed to marshal commit event: %w", err)
	}

	k.logger.Info("publishing commit event",
		slog.String("orderId", commit.OrderId),
		slog.String("decision", commit.Decision),
		slog.String("json", string(message)),
		slog.String("topic", k.writer.Topic))

	kafkaMsg := kafka.Message{
		Key:   []byte(commit.OrderId),
		Value: message,
		Headers: []kafka.Header{
			{Key: "event_type", Value: []byte("commit")},
			{Key: "decision", Value: []byte(commit.Decision)},
			{Key: "timestamp", Value: []byte(time.Now().Format(time.RFC3339))},
		},
	}

	if err := k.writer.WriteMessages(ctx, kafkaMsg); err != nil {
		return fmt.Errorf("failed to write commit message: %w", err)
	}

	k.logger.Info("published commit event",
		slog.String("orderId", commit.OrderId),
		slog.String("decision", commit.Decision),
		slog.String("topic", k.writer.Topic))

	return nil
}
