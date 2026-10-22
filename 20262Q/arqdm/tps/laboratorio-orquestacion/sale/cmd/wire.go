//go:build wireinject
// +build wireinject

package main

import (
	"github.com/google/wire"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/sale/internal/handler"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/sale/internal/messaging/kafka"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/sale/internal/repository/postgres"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/sale/internal/service"
)

// InitializeApplication wires up all dependencies directly
func InitializeApplication(config *kafka.PublisherConfig) (*handler.Handler, error) {
	wire.Build(
		kafka.NewKafkaPublisher,
		postgres.NewSaleDatabaseRepository,
		service.NewSaleService,
		handler.NewSaleHandler,
	)
	return nil, nil
}
