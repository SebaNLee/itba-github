package postgres

import (
	"fmt"
	"os"
	"time"

	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/payment/pkg/model"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/schemas"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
)

type PaymentPostgresRepository struct {
	db *gorm.DB
}

// getDatabaseConnection initializes a new database connection using GORM with PostgreSQL.
func getDatabaseConnection() (*gorm.DB, error) {
	host := getEnvOrDefault("DB_HOST", "localhost")
	port := getEnvOrDefault("DB_PORT", "5432")
	user := getEnvOrDefault("DB_USER", "postgres")
	password := getEnvOrDefault("DB_PASSWORD", "password")
	dbname := getEnvOrDefault("DB_NAME", "payment_db")
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

func NewPaymentDatabaseRepository() *PaymentPostgresRepository {
	db, err := getDatabaseConnection()
	if err != nil {
		panic("Failed to connect to database: " + err.Error())
	}

	if err := db.AutoMigrate(&model.Payment{}, &model.ParticipantTransaction{}); err != nil {
		panic("Failed to migrate database: " + err.Error())
	}

	return &PaymentPostgresRepository{db: db}
}

func (r *PaymentPostgresRepository) Save(payment model.Payment) error {
	result := r.db.Create(&payment)
	return result.Error
}

func (r *PaymentPostgresRepository) ReservePayment(orderId string, payment *model.Payment) error {
	// Start a database transaction for atomicity
	return r.db.Transaction(func(tx *gorm.DB) error {
		// Create participant transaction record
		participantTx := &model.ParticipantTransaction{
			OrderId:       payment.OrderId,
			State:         schemas.ParticipantStatePrepared,
			Amount:        payment.Amount,
			UserID:        payment.UserID,
			PaymentMethod: payment.PaymentMethod,
		}

		if err := tx.Create(participantTx).Error; err != nil {
			return err
		}

		// Create payment with reserved status
		payment.Status = "reserved"
		if err := tx.Create(payment).Error; err != nil {
			return err
		}

		return nil
	})
}

func (r *PaymentPostgresRepository) CommitPayment(orderId string) error {
	return r.db.Transaction(func(tx *gorm.DB) error {
		// Update participant state
		if err := tx.Model(&model.ParticipantTransaction{}).
			Where("order_id = ?", orderId).
			Update("state", schemas.ParticipantStateCommitted).Error; err != nil {
			return err
		}

		// Update payment status to committed and set processed time
		now := time.Now()
		if err := tx.Model(&model.Payment{}).
			Where("order_id = ?", orderId).
			Updates(map[string]interface{}{
				"status":       "committed",
				"processed_at": now,
			}).Error; err != nil {
			return err
		}

		return nil
	})
}

func (r *PaymentPostgresRepository) AbortPayment(orderId string) error {
	return r.db.Transaction(func(tx *gorm.DB) error {
		// Update participant state
		if err := tx.Model(&model.ParticipantTransaction{}).
			Where("order_id = ?", orderId).
			Update("state", schemas.ParticipantStateAborted).Error; err != nil {
			return err
		}

		// Update payment status to cancelled (don't delete for audit trail)
		if err := tx.Model(&model.Payment{}).
			Where("order_id = ?", orderId).
			Update("status", "cancelled").Error; err != nil {
			return err
		}

		return nil
	})
}

func (r *PaymentPostgresRepository) MarkPaymentFailed(orderId string, reason string) error {
	return r.db.Transaction(func(tx *gorm.DB) error {
		// Update participant state
		if err := tx.Model(&model.ParticipantTransaction{}).
			Where("order_id = ?", orderId).
			Update("state", schemas.ParticipantStateAborted).Error; err != nil {
			return err
		}

		// Update payment with failure reason
		if err := tx.Model(&model.Payment{}).
			Where("order_id = ?", orderId).
			Updates(map[string]interface{}{
				"status":         "failed",
				"failure_reason": reason,
			}).Error; err != nil {
			return err
		}

		return nil
	})
}

func (r *PaymentPostgresRepository) BeginTransaction() *gorm.DB {
	return r.db.Begin()
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
