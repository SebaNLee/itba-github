package postgres

import (
	"fmt"
	"os"

	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/sale/pkg/model"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/schemas"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
)

type SalePostgresRepository struct {
	db *gorm.DB
}

// getDatabaseConnection initializes a new database connection using GORM with PostgreSQL.
func getDatabaseConnection() (*gorm.DB, error) {
	host := getEnvOrDefault("DB_HOST", "localhost")
	port := getEnvOrDefault("DB_PORT", "5432")
	user := getEnvOrDefault("DB_USER", "postgres")
	password := getEnvOrDefault("DB_PASSWORD", "password")
	dbname := getEnvOrDefault("DB_NAME", "sales_db")
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

func NewSaleDatabaseRepository() *SalePostgresRepository {
	db, err := getDatabaseConnection()
	if err != nil {
		panic("Failed to connect to database: " + err.Error())
	}

	if err := db.AutoMigrate(&model.Sale{}, &model.ParticipantTransaction{}); err != nil {
		panic("Failed to migrate database: " + err.Error())
	}

	return &SalePostgresRepository{db: db}
}

func (r *SalePostgresRepository) Save(sale model.Sale) error {
	result := r.db.Create(&sale)
	return result.Error
}

func (r *SalePostgresRepository) UpdateStatus(orderId string, status string) error {
	result := r.db.Model(&model.Sale{}).Where("sale_id = ?", orderId).Update("status", status)
	if result.Error != nil {
		return result.Error
	}

	// Verificar que se actualizó al menos un registro
	if result.RowsAffected == 0 {
		return fmt.Errorf("no sale found with sale_id: %s", orderId)
	}

	return nil
}

// 2PC Participant Methods

func (r *SalePostgresRepository) CreateParticipantTransaction(tx *model.ParticipantTransaction) error {
	return r.db.Create(tx).Error
}

func (r *SalePostgresRepository) GetParticipantTransaction(orderId string) (*model.ParticipantTransaction, error) {
	var tx model.ParticipantTransaction
	err := r.db.First(&tx, "order_id = ?", orderId).Error
	if err != nil {
		return nil, err
	}
	return &tx, nil
}

func (r *SalePostgresRepository) UpdateParticipantState(orderId string, state schemas.ParticipantState) error {
	result := r.db.Model(&model.ParticipantTransaction{}).
		Where("order_id = ?", orderId).
		Update("state", state)
	return result.Error
}

func (r *SalePostgresRepository) ReserveSale(orderId string, sale *model.Sale) error {
	// Start a database transaction for atomicity
	return r.db.Transaction(func(tx *gorm.DB) error {
		// Create participant transaction record
		participantTx := &model.ParticipantTransaction{
			OrderId:   orderId,
			State:     schemas.ParticipantStatePrepared,
			ProductId: sale.ProductId,
			Quantity:  sale.Quantity,
			Amount:    sale.Amount,
			UserID:    sale.UserID,
		}

		if err := tx.Create(participantTx).Error; err != nil {
			return err
		}

		// Create sale with reserved status
		sale.Status = "reserved"
		if err := tx.Create(sale).Error; err != nil {
			return err
		}

		return nil
	})
}

func (r *SalePostgresRepository) CommitSale(orderId string) error {
	return r.db.Transaction(func(tx *gorm.DB) error {
		// Update participant state
		if err := tx.Model(&model.ParticipantTransaction{}).
			Where("order_id = ?", orderId).
			Update("state", schemas.ParticipantStateCommitted).Error; err != nil {
			return err
		}

		// Update sale status to committed
		if err := tx.Model(&model.Sale{}).
			Where("order_id = ?", orderId).
			Update("status", "committed").Error; err != nil {
			return err
		}

		return nil
	})
}

func (r *SalePostgresRepository) AbortSale(orderId string) error {
	return r.db.Transaction(func(tx *gorm.DB) error {
		// Update participant state
		if err := tx.Model(&model.ParticipantTransaction{}).
			Where("order_id = ?", orderId).
			Update("state", schemas.ParticipantStateAborted).Error; err != nil {
			return err
		}

		// Delete the reserved sale (rollback)
		if err := tx.Where("order_id = ?", orderId).Delete(&model.Sale{}).Error; err != nil {
			return err
		}

		return nil
	})
}

func (r *SalePostgresRepository) BeginTransaction() *gorm.DB {
	return r.db.Begin()
}

func (r *SalePostgresRepository) WithTransaction(tx *gorm.DB) *SalePostgresRepository {
	return &SalePostgresRepository{db: tx}
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
