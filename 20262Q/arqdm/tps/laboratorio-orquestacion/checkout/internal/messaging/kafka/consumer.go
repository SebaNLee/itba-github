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
	fmt.Println("Using Kafka brokers for consumer:", brokers)

	return &ConsumerConfig{
		Brokers:       strings.Split(brokers, ","),
		InboundTopic:  getEnvOrDefault("KAFKA_NOTIFICATION_TOPIC", "checkout-notifications"),
		ConsumerGroup: getEnvOrDefault("KAFKA_CONSUMER_GROUP", "checkout-service"),
		StartOffset:   kafka.LastOffset,
	}
}

func (k *KafkaConsumer) ConsumeVoteEvents(ctx context.Context, voteHandler func(events.VoteEvent) error, ackHandler func(events.AckEvent) error) error {
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

			k.logger.Info("consumed message",
				slog.Int("partition", msg.Partition),
				slog.Int64("offset", msg.Offset),
				slog.String("message", string(msg.Value)))

			// Try to unmarshal as VoteEvent
			var voteEvent events.VoteEvent
			if err := json.Unmarshal(msg.Value, &voteEvent); err == nil && voteEvent.OrderId != "" && voteEvent.Vote != "" {
				k.logger.Info("processing vote event",
					slog.String("orderId", voteEvent.OrderId),
					slog.String("service", voteEvent.Service),
					slog.String("vote", voteEvent.Vote))

				if err := voteHandler(voteEvent); err != nil {
					k.logger.Error("vote handler error",
						slog.String("orderId", voteEvent.OrderId),
						slog.String("error", err.Error()))
				}
				continue
			}

			// Try to unmarshal as AckEvent
			var ackEvent events.AckEvent
			if err := json.Unmarshal(msg.Value, &ackEvent); err == nil && ackEvent.OrderId != "" && ackEvent.Status != "" {
				k.logger.Info("processing ack event",
					slog.String("orderId", ackEvent.OrderId),
					slog.String("service", ackEvent.Service),
					slog.String("status", ackEvent.Status))

				if err := ackHandler(ackEvent); err != nil {
					k.logger.Error("ack handler error",
						slog.String("orderId", ackEvent.OrderId),
						slog.String("error", err.Error()))
				}
				continue
			}

			k.logger.Warn("unrecognized message format", slog.String("message", string(msg.Value)))
		}
	}
}

func (k *KafkaConsumer) Close() error {
	if err := k.reader.Close(); err != nil {
		return fmt.Errorf("reader close: %w", err)
	}
	return nil
}
