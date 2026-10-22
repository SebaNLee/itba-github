//go:build wireinject
// +build wireinject

package main

import (
	"github.com/google/wire"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/inventory/internal/handler"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/inventory/internal/messaging/kafka"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/inventory/internal/repository/postgres"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/inventory/internal/service"
)

// InitializeApplication wires up all dependencies directly
func InitializeApplication(config *kafka.PublisherConfig) (*handler.Handler, error) {
	wire.Build(
		kafka.NewKafkaPublisher,
		postgres.NewInventoryDatabaseRepository,
		service.NewInventoryService,
		handler.NewInventoryHandler,
	)
	return nil, nil
}