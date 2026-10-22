package model

import (
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/schemas"
	"gorm.io/gorm"
	"time"
)

type Sale struct {
	gorm.Model
	SaleId    string  `gorm:"sale_id;uniqueIndex" json:"sale_id"`
	OrderId   string  `gorm:"order_id;index" json:"order_id"`
	ProductId int     `gorm:"product_id" json:"product_id"`
	Quantity  int     `gorm:"quantity" json:"quantity"`
	Amount    float64 `gorm:"amount" json:"amount"`
	UserID    int     `gorm:"user_id" json:"user_id"`
	Status    string  `gorm:"status" json:"status"` // "reserved", "committed", "cancelled"
}

// Participant state tracking for 2PC
type ParticipantTransaction struct {
	OrderId   string                   `gorm:"primaryKey" json:"order_id"`
	State     schemas.ParticipantState `gorm:"not null" json:"state"`
	ProductId int                      `gorm:"product_id" json:"product_id"`
	Quantity  int                      `gorm:"quantity" json:"quantity"`
	Amount    float64                  `gorm:"amount" json:"amount"`
	UserID    int                      `gorm:"user_id" json:"user_id"`
	CreatedAt time.Time                `gorm:"autoCreateTime" json:"created_at"`
	UpdatedAt time.Time                `gorm:"autoUpdateTime" json:"updated_at"`
}
