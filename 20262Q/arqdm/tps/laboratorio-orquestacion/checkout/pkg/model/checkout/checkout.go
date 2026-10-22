package checkout

import (
	"time"

	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/schemas"
	"gorm.io/gorm"
)

type Checkout struct {
	gorm.Model
	OrderId    string                   `gorm:"uniqueIndex" json:"orderId"`
	State      schemas.TransactionState `gorm:"not null" json:"state"`
	ProductId  int                      `gorm:"product_id" json:"productId"`
	Quantity   int                      `gorm:"quantity" json:"quantity"`
	UserId     int                      `gorm:"user_id" json:"userId"`
	UnitPrice  float64                  `gorm:"unit_price" json:"unitPrice"`
	TotalValue float64                  `gorm:"total_value" json:"totalValue"`
	ExpiresAt  time.Time                `gorm:"not null" json:"expiresAt"`

	// Relationships
	Participants []Participant `gorm:"foreignKey:CheckoutID" json:"participants"`
}

// Participant tracking for each service in the transaction
type Participant struct {
	ID         uint                     `gorm:"primaryKey" json:"id"`
	CheckoutID uint                     `gorm:"not null;index" json:"checkoutId"`
	OrderId    string                   `gorm:"not null;index" json:"orderId"`
	Service    schemas.ServiceType      `gorm:"not null" json:"service"`
	State      schemas.ParticipantState `gorm:"not null" json:"state"`
	Vote       *schemas.Vote            `gorm:"type:varchar(3)" json:"vote,omitempty"`
	CreatedAt  time.Time                `gorm:"autoCreateTime" json:"createdAt"`
	UpdatedAt  time.Time                `gorm:"autoUpdateTime" json:"updatedAt"`
}
