package events

import "time"

// Transaction Request - Initiates 2PC
type TransactionRequest struct {
	OrderId    string    `json:"orderId"`
	ProductId  int       `json:"productId"`
	Quantity   int       `json:"quantity"`
	UserId     int       `json:"userId"`
	UnitPrice  float64   `json:"unitPrice"`
	TotalValue float64   `json:"totalValue"`
	Timestamp  time.Time `json:"timestamp"`
}

// Phase 1: PREPARE Events
type PrepareEvent struct {
	OrderId    string    `json:"orderId"`
	ProductId  int       `json:"productId"`
	Quantity   int       `json:"quantity"`
	UserId     int       `json:"userId"`
	UnitPrice  float64   `json:"unitPrice"`
	TotalValue float64   `json:"totalValue"`
	Timestamp  time.Time `json:"timestamp"`
}

type VoteEvent struct {
	OrderId   string    `json:"orderId"`
	Service   string    `json:"service"` // "sale", "payment", "inventory"
	Vote      string    `json:"vote"`    // "YES", "NO"
	Reason    string    `json:"reason,omitempty"`
	Timestamp time.Time `json:"timestamp"`
}

// Phase 2: COMMIT/ABORT Events
type CommitEvent struct {
	OrderId   string    `json:"orderId"`
	Decision  string    `json:"decision"` // "COMMIT", "ABORT"
	Timestamp time.Time `json:"timestamp"`
}

type AckEvent struct {
	OrderId   string    `json:"orderId"`
	Service   string    `json:"service"`
	Status    string    `json:"status"` // "COMMITTED", "ABORTED"
	Timestamp time.Time `json:"timestamp"`
}
