package model

import (
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/schemas"
	"gorm.io/gorm"
	"time"
)

type Inventory struct {
	gorm.Model
	ProductId      int `gorm:"product_id;uniqueIndex" json:"product_id"`
	TotalStock     int `gorm:"total_stock" json:"total_stock"`
	ReservedStock  int `gorm:"reserved_stock" json:"reserved_stock"`
	AvailableStock int `gorm:"available_stock" json:"available_stock"`
}

type InventoryReservation struct {
	gorm.Model
	ReservationId string `gorm:"reservation_id;uniqueIndex" json:"reservation_id"`
	OrderId       string `gorm:"order_id;index" json:"order_id"`
	ProductId     int    `gorm:"product_id" json:"product_id"`
	Quantity      int    `gorm:"quantity" json:"quantity"`
	Status        string `gorm:"status" json:"status"` // "reserved", "committed", "cancelled"
}

// Participant state tracking for 2PC
type ParticipantTransaction struct {
	OrderId   string                   `gorm:"primaryKey" json:"order_id"`
	State     schemas.ParticipantState `gorm:"not null" json:"state"`
	ProductId int                      `gorm:"product_id" json:"product_id"`
	Quantity  int                      `gorm:"quantity" json:"quantity"`
	CreatedAt time.Time                `gorm:"autoCreateTime" json:"created_at"`
	UpdatedAt time.Time                `gorm:"autoUpdateTime" json:"updated_at"`
}
