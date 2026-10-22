//go:build wireinject
// +build wireinject

package main

import (
	"os"
	"strings"

	"github.com/google/wire"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/payment/internal/messaging/kafka"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/payment/internal/repository/postgres"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/payment/internal/service"
)

// PaymentApp contains all the components for the payment service
type PaymentApp struct {
	Service  *service.PaymentService
	Consumer *kafka.KafkaConsumer
}

// InitializeApplication wires up all dependencies for the payment service
func InitializeApplication() (*PaymentApp, error) {
	wire.Build(
		// Repository
		postgres.NewPaymentDatabaseRepository,

		// Messaging
		provideKafkaPublisher,
		provideKafkaConsumer,

		// Service
		service.NewPaymentService,

		// App struct
		wire.Struct(new(PaymentApp), "*"),
	)
	return nil, nil
}

// provideKafkaPublisher creates a Kafka publisher for vote and ack responses
func provideKafkaPublisher() *kafka.KafkaPublisher {
	brokers := os.Getenv("KAFKA_BROKERS")
	if brokers == "" {
		brokers = "kafka:29092"
	}

	config := &kafka.PublisherConfig{
		Brokers:           strings.Split(brokers, ","),
		NotificationTopic: "checkout-notifications",
	}
	return kafka.NewKafkaPublisher(config)
}

// provideKafkaConsumer creates a Kafka consumer for 2PC events
func provideKafkaConsumer() *kafka.KafkaConsumer {
	brokers := os.Getenv("KAFKA_BROKERS")
	if brokers == "" {
		brokers = "kafka:29092"
	}

	config := &kafka.ConsumerConfig{
		Brokers:       strings.Split(brokers, ","),
		InboundTopic:  "checkout-events", // Listen to all 2PC events from coordinator
		ConsumerGroup: "payment-service",
		StartOffset:   -1, // Start from latest
	}
	return kafka.NewKafkaConsumer(config)
}
