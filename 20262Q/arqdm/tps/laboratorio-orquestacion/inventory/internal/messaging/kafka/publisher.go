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
	Brokers           []string
	NotificationTopic string
}

func NewKafkaPublisher(config *PublisherConfig) *KafkaPublisher {
	if config == nil {
		config = getDefaultPublisherConfig()
	}

	writer := &kafka.Writer{
		Addr:         kafka.TCP(config.Brokers...),
		Topic:        config.NotificationTopic,
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
	fmt.Println("Using Kafka brokers for inventory publisher:", brokers)

	return &PublisherConfig{
		Brokers:           strings.Split(brokers, ","),
		NotificationTopic: getEnvOrDefault("KAFKA_NOTIFICATION_TOPIC", "checkout-notifications"),
	}
}

func getEnvOrDefault(key, defaultValue string) string {
	if value := os.Getenv(key); value != "" {
		return value
	}
	return defaultValue
}

func (k *KafkaPublisher) PublishVote(ctx context.Context, vote events.VoteEvent) error {
	message, err := json.Marshal(vote)
	if err != nil {
		return fmt.Errorf("failed to marshal vote: %w", err)
	}

	kafkaMsg := kafka.Message{
		Key:   []byte(vote.OrderId),
		Value: message,
		Headers: []kafka.Header{
			{Key: "event_type", Value: []byte("inventory.vote")},
			{Key: "service", Value: []byte("inventory")},
			{Key: "timestamp", Value: []byte(time.Now().Format(time.RFC3339))},
		},
	}

	if err := k.writer.WriteMessages(ctx, kafkaMsg); err != nil {
		return fmt.Errorf("failed to write vote message: %w", err)
	}

	k.logger.Info("published inventory vote",
		slog.String("orderId", vote.OrderId),
		slog.String("vote", vote.Vote),
		slog.String("topic", k.writer.Topic))

	return nil
}

func (k *KafkaPublisher) PublishAck(ctx context.Context, ack events.AckEvent) error {
	message, err := json.Marshal(ack)
	if err != nil {
		return fmt.Errorf("failed to marshal ack: %w", err)
	}

	kafkaMsg := kafka.Message{
		Key:   []byte(ack.OrderId),
		Value: message,
		Headers: []kafka.Header{
			{Key: "event_type", Value: []byte("inventory.ack")},
			{Key: "service", Value: []byte("inventory")},
			{Key: "timestamp", Value: []byte(time.Now().Format(time.RFC3339))},
		},
	}

	if err := k.writer.WriteMessages(ctx, kafkaMsg); err != nil {
		return fmt.Errorf("failed to write ack message: %w", err)
	}

	k.logger.Info("published inventory ack",
		slog.String("orderId", ack.OrderId),
		slog.String("status", ack.Status),
		slog.String("topic", k.writer.Topic))

	return nil
}

func (k *KafkaPublisher) Close() error {
	if err := k.writer.Close(); err != nil {
		return fmt.Errorf("writer close: %w", err)
	}
	return nil
}
