package model

import (
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/schemas"
	"gorm.io/gorm"
	"time"
)

type Payment struct {
	gorm.Model
	PaymentId     string     `gorm:"payment_id;uniqueIndex" json:"payment_id"`
	OrderId       string     `gorm:"order_id;index" json:"order_id"`
	Amount        float64    `gorm:"amount" json:"amount"`
	UserID        int        `gorm:"user_id" json:"user_id"`
	PaymentMethod string     `gorm:"payment_method" json:"payment_method"` // "credit_card", "debit_card", "bank_transfer"
	Status        string     `gorm:"status" json:"status"`                 // "reserved", "committed", "failed", "cancelled"
	FailureReason string     `gorm:"failure_reason" json:"failure_reason,omitempty"`
	ProcessedAt   *time.Time `gorm:"processed_at" json:"processed_at,omitempty"`
}

// Participant state tracking for 2PC
type ParticipantTransaction struct {
	OrderId         string                   `gorm:"primaryKey" json:"order_id"`
	State           schemas.ParticipantState `gorm:"not null" json:"state"`
	Amount          float64                  `gorm:"amount" json:"amount"`
	UserID          int                      `gorm:"user_id" json:"user_id"`
	PaymentMethod   string                   `gorm:"payment_method" json:"payment_method"`
	SimulateFailure bool                     `gorm:"simulate_failure" json:"simulate_failure"` // For educational failure simulation
	CreatedAt       time.Time                `gorm:"autoCreateTime" json:"created_at"`
	UpdatedAt       time.Time                `gorm:"autoUpdateTime" json:"updated_at"`
}

// Payment failure simulation types for educational purposes
type FailureType string

const (
	FailureTypeNone               FailureType = "none"
	FailureTypeInsufficientFunds  FailureType = "insufficient_funds"
	FailureTypeCardExpired        FailureType = "card_expired"
	FailureTypeNetworkTimeout     FailureType = "network_timeout"
	FailureTypeFraudDetection     FailureType = "fraud_detection"
	FailureTypeServiceUnavailable FailureType = "service_unavailable"
)

// Payment methods for simulation
type PaymentMethod string

const (
	PaymentMethodCreditCard   PaymentMethod = "credit_card"
	PaymentMethodDebitCard    PaymentMethod = "debit_card"
	PaymentMethodBankTransfer PaymentMethod = "bank_transfer"
)
