package postgres

import (
	"fmt"
	"os"

	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/checkout/pkg/model/checkout"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/schemas"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
)

type CheckoutPostgresRepository struct {
	db *gorm.DB
}

// getDatabaseConnection initializes a new database connection using GORM with PostgreSQL.
func getDatabaseConnection() (*gorm.DB, error) {
	host := getEnvOrDefault("DB_HOST", "localhost")
	port := getEnvOrDefault("DB_PORT", "5432")
	user := getEnvOrDefault("DB_USER", "postgres")
	password := getEnvOrDefault("DB_PASSWORD", "password")
	dbname := getEnvOrDefault("DB_NAME", "checkout_db")
	sslmode := getEnvOrDefault("DB_SSLMODE", "disable")

	dsn := fmt.Sprintf("host=%s user=%s password=%s dbname=%s port=%s sslmode=%s TimeZone=UTC",
		host, user, password, dbname, port, sslmode)

	config := &gorm.Config{
		Logger: logger.Default.LogMode(getLogLevel()),
	}

	db, err := gorm.Open(postgres.Open(dsn), config)
	if err != nil {
		return nil, fmt.Errorf("failed to connect to PostgreSQL: %w", err)
	}

	// Configure connection pool
	sqlDB, err := db.DB()
	if err != nil {
		return nil, fmt.Errorf("failed to get underlying sql.DB: %w", err)
	}

	sqlDB.SetMaxIdleConns(10)
	sqlDB.SetMaxOpenConns(100)
	sqlDB.SetConnMaxLifetime(0) // No limit, connections reused indefinitely

	return db, nil
}

func NewCheckoutDatabaseRepository() *CheckoutPostgresRepository {
	db, err := getDatabaseConnection()
	if err != nil {
		panic("Failed to connect to database: " + err.Error())
	}

	if err := db.AutoMigrate(&checkout.Checkout{}, &checkout.Participant{}); err != nil {
		panic("Failed to migrate database: " + err.Error())
	}

	return &CheckoutPostgresRepository{db: db}
}

func (r *CheckoutPostgresRepository) CreateCheckout(checkout *checkout.Checkout) error {
	result := r.db.Create(checkout)
	return result.Error
}

func (r *CheckoutPostgresRepository) GetCheckoutByOrderId(orderId string) (*checkout.Checkout, error) {
	var checkoutRecord checkout.Checkout
	err := r.db.First(&checkoutRecord, "order_id = ?", orderId).Error
	if err != nil {
		return nil, err
	}
	return &checkoutRecord, nil
}

func (r *CheckoutPostgresRepository) UpdateCheckoutState(orderId string, state schemas.TransactionState) error {
	result := r.db.Model(&checkout.Checkout{}).Where("order_id = ?", orderId).Update("state", state)
	if result.Error != nil {
		return result.Error
	}

	// Verificar que se actualizó al menos un registro
	if result.RowsAffected == 0 {
		return fmt.Errorf("no checkout found with order_id: %s", orderId)
	}

	return nil
}

// Checkout with Participants Methods

func (r *CheckoutPostgresRepository) GetCheckoutWithParticipants(orderId string) (*checkout.Checkout, error) {
	var checkoutRecord checkout.Checkout
	err := r.db.Preload("Participants").First(&checkoutRecord, "order_id = ?", orderId).Error
	if err != nil {
		return nil, err
	}
	return &checkoutRecord, nil
}

func (r *CheckoutPostgresRepository) CreateParticipant(participant *checkout.Participant) error {
	return r.db.Create(participant).Error
}

func (r *CheckoutPostgresRepository) UpdateParticipantVote(orderId string, service schemas.ServiceType, vote schemas.Vote, state schemas.ParticipantState) error {
	result := r.db.Model(&checkout.Participant{}).
		Where("order_id = ? AND service = ?", orderId, service).
		Updates(map[string]interface{}{
			"vote":  vote,
			"state": state,
		})
	return result.Error
}

func (r *CheckoutPostgresRepository) UpdateParticipantState(orderId string, service schemas.ServiceType, state schemas.ParticipantState) error {
	result := r.db.Model(&checkout.Participant{}).
		Where("order_id = ? AND service = ?", orderId, service).
		Update("state", state)
	return result.Error
}

func (r *CheckoutPostgresRepository) BeginTransaction() *gorm.DB {
	return r.db.Begin()
}

func (r *CheckoutPostgresRepository) WithTransaction(tx *gorm.DB) *CheckoutPostgresRepository {
	return &CheckoutPostgresRepository{db: tx}
}

// Helper functions
func getEnvOrDefault(key, defaultValue string) string {
	if value := os.Getenv(key); value != "" {
		return value
	}
	return defaultValue
}

func getLogLevel() logger.LogLevel {
	switch os.Getenv("DB_LOG_LEVEL") {
	case "silent":
		return logger.Silent
	case "error":
		return logger.Error
	case "warn":
		return logger.Warn
	case "info":
		return logger.Info
	default:
		return logger.Warn
	}
}
