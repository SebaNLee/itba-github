//go:build wireinject
// +build wireinject

package main

import (
	"github.com/google/wire"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/checkout/internal/handler"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/checkout/internal/messaging/kafka"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/checkout/internal/repository/postgres"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/checkout/internal/service"
)

// InitializeApplication wires up all dependencies directly
func InitializeApplication(publisherConfig *kafka.PublisherConfig, consumerConfig *kafka.ConsumerConfig) (*Application, error) {
	wire.Build(
		kafka.NewKafkaPublisher,
		kafka.NewKafkaConsumer,
		postgres.NewCheckoutDatabaseRepository,
		service.NewCheckoutService,
		handler.NewCheckoutHandler,
		NewApplication,
	)
	return nil, nil
}

type Application struct {
	Handler  *handler.CheckoutHandler
	Consumer *kafka.KafkaConsumer
	Service  *service.CheckoutService
}

func NewApplication(handler *handler.CheckoutHandler, consumer *kafka.KafkaConsumer, service *service.CheckoutService) *Application {
	return &Application{
		Handler:  handler,
		Consumer: consumer,
		Service:  service,
	}
}
