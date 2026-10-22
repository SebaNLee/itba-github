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

type KafkaConsumer struct {
	reader *kafka.Reader
	logger *slog.Logger
}

type ConsumerConfig struct {
	Brokers       []string
	InboundTopic  string
	ConsumerGroup string
	StartOffset   int64
}

func NewKafkaConsumer(config *ConsumerConfig) *KafkaConsumer {
	if config == nil {
		config = getDefaultConsumerConfig()
	}

	reader := kafka.NewReader(kafka.ReaderConfig{
		Brokers:        config.Brokers,
		Topic:          config.InboundTopic,
		GroupID:        config.ConsumerGroup,
		StartOffset:    config.StartOffset,
		MinBytes:       10e3, // 10KB
		MaxBytes:       10e6, // 10MB
		MaxWait:        1 * time.Second,
		CommitInterval: 1 * time.Second,
		Partition:      0,
	})

	return &KafkaConsumer{
		reader: reader,
		logger: slog.Default(),
	}
}

func getDefaultConsumerConfig() *ConsumerConfig {
	brokers := os.Getenv("KAFKA_BROKERS")
	if brokers == "" {
		brokers = "localhost:9092"
	}
	fmt.Println("Using Kafka brokers for payment consumer:", brokers)

	return &ConsumerConfig{
		Brokers:       strings.Split(brokers, ","),
		InboundTopic:  getEnvOrDefault("KAFKA_INBOUND_TOPIC", "checkout-events"),
		ConsumerGroup: getEnvOrDefault("KAFKA_CONSUMER_GROUP", "payment-service"),
		StartOffset:   kafka.LastOffset,
	}
}

func (k *KafkaConsumer) Consume2PCEvents(ctx context.Context, prepareHandler func(*events.PrepareEvent) error, commitHandler func(*events.CommitEvent) error) error {
	for {
		select {
		case <-ctx.Done():
			return ctx.Err()
		default:
			msg, err := k.reader.ReadMessage(ctx)
			if err != nil {
				if ctx.Err() != nil {
					return ctx.Err()
				}
				k.logger.Error("consume error", slog.String("error", err.Error()))
				time.Sleep(100 * time.Millisecond)
				continue
			}

			k.logger.Info("raw message received",
				slog.String("value", string(msg.Value)),
				slog.Int("partition", msg.Partition),
				slog.Int64("offset", msg.Offset))

			// Try to determine message type
			var rawMessage map[string]interface{}
			if err := json.Unmarshal(msg.Value, &rawMessage); err != nil {
				k.logger.Error("failed to unmarshal raw message", slog.String("error", err.Error()))
				continue
			}

			// Check if it's a CommitEvent (has "decision" field)
			if _, hasDecision := rawMessage["decision"]; hasDecision {
				var commitEvent events.CommitEvent
				if err := json.Unmarshal(msg.Value, &commitEvent); err != nil {
					k.logger.Error("failed to unmarshal commit event", slog.String("error", err.Error()))
					continue
				}

				k.logger.Info("consumed commit event",
					slog.String("orderId", commitEvent.OrderId),
					slog.String("decision", commitEvent.Decision),
					slog.Int("partition", msg.Partition),
					slog.Int64("offset", msg.Offset))

				if err := commitHandler(&commitEvent); err != nil {
					k.logger.Error("commit handler error",
						slog.String("orderId", commitEvent.OrderId),
						slog.String("error", err.Error()))
				}
				continue
			}

			// Otherwise, try as PrepareEvent
			var prepareEvent events.PrepareEvent
			if err := json.Unmarshal(msg.Value, &prepareEvent); err != nil {
				k.logger.Error("failed to unmarshal prepare event", slog.String("error", err.Error()))
				continue
			}

			// Check if this is a valid PrepareEvent
			if prepareEvent.OrderId == "" {
				k.logger.Warn("ignoring invalid message - no order ID")
				continue
			}

			k.logger.Info("received PrepareEvent",
				slog.String("orderId", prepareEvent.OrderId),
				slog.Float64("totalValue", prepareEvent.TotalValue))

			k.logger.Info("consumed prepare event",
				slog.String("orderId", prepareEvent.OrderId),
				slog.Int("partition", msg.Partition),
				slog.Int64("offset", msg.Offset))

			if err := prepareHandler(&prepareEvent); err != nil {
				k.logger.Error("handler error",
					slog.String("orderId", prepareEvent.OrderId),
					slog.String("error", err.Error()))
			}
		}
	}
}

func (k *KafkaConsumer) Close() error {
	if err := k.reader.Close(); err != nil {
		return fmt.Errorf("reader close: %w", err)
	}
	return nil
}
