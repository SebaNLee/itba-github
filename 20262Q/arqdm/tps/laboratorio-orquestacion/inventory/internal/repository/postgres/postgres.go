package postgres

import (
	"fmt"
	"os"

	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/inventory/pkg/model"
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/schemas"
	"gorm.io/driver/postgres"
	"gorm.io/gorm"
	"gorm.io/gorm/logger"
)

type InventoryPostgresRepository struct {
	db *gorm.DB
}

// getDatabaseConnection initializes a new database connection using GORM with PostgreSQL.
func getDatabaseConnection() (*gorm.DB, error) {
	host := getEnvOrDefault("DB_HOST", "localhost")
	port := getEnvOrDefault("DB_PORT", "5435")
	user := getEnvOrDefault("DB_USER", "postgres")
	password := getEnvOrDefault("DB_PASSWORD", "password")
	dbname := getEnvOrDefault("DB_NAME", "inventory_db")
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

func NewInventoryDatabaseRepository() *InventoryPostgresRepository {
	db, err := getDatabaseConnection()
	if err != nil {
		panic("Failed to connect to database: " + err.Error())
	}

	if err := db.AutoMigrate(&model.Inventory{}, &model.InventoryReservation{}, &model.ParticipantTransaction{}); err != nil {
		panic("Failed to migrate database: " + err.Error())
	}

	repo := &InventoryPostgresRepository{db: db}

	// Initialize inventory with 100 pieces for product ID 1
	repo.initializeInventory()

	return repo
}

func (r *InventoryPostgresRepository) initializeInventory() {
	var inventory model.Inventory
	result := r.db.Where("product_id = ?", 1).First(&inventory)

	if result.Error == gorm.ErrRecordNotFound {
		// Create initial inventory record
		initialInventory := model.Inventory{
			ProductId:      1,
			TotalStock:     100,
			ReservedStock:  0,
			AvailableStock: 100,
		}
		r.db.Create(&initialInventory)
	}
}

func (r *InventoryPostgresRepository) GetInventory(productId int) (*model.Inventory, error) {
	var inventory model.Inventory
	err := r.db.First(&inventory, "product_id = ?", productId).Error
	if err != nil {
		return nil, err
	}
	return &inventory, nil
}

// 2PC Participant Methods
func (r *InventoryPostgresRepository) ReserveInventory(orderId string, productId, quantity int) error {
	// Start a database transaction for atomicity
	return r.db.Transaction(func(tx *gorm.DB) error {
		// Check if we have enough available stock
		var inventory model.Inventory
		if err := tx.First(&inventory, "product_id = ?", productId).Error; err != nil {
			return err
		}

		if inventory.AvailableStock < quantity {
			return fmt.Errorf("insufficient stock: available=%d, requested=%d", inventory.AvailableStock, quantity)
		}

		// Create participant transaction record
		participantTx := &model.ParticipantTransaction{
			OrderId:   orderId,
			State:     schemas.ParticipantStatePrepared,
			ProductId: productId,
			Quantity:  quantity,
		}

		if err := tx.Create(participantTx).Error; err != nil {
			return err
		}

		// Create inventory reservation
		reservation := &model.InventoryReservation{
			ReservationId: fmt.Sprintf("%s-%d", orderId, productId),
			OrderId:       orderId,
			ProductId:     productId,
			Quantity:      quantity,
			Status:        "reserved",
		}

		if err := tx.Create(reservation).Error; err != nil {
			return err
		}

		// Update inventory to reserve stock
		if err := tx.Model(&model.Inventory{}).
			Where("product_id = ?", productId).
			Updates(map[string]interface{}{
				"reserved_stock":  gorm.Expr("reserved_stock + ?", quantity),
				"available_stock": gorm.Expr("available_stock - ?", quantity),
			}).Error; err != nil {
			return err
		}

		return nil
	})
}

func (r *InventoryPostgresRepository) CommitInventory(orderId string) error {
	return r.db.Transaction(func(tx *gorm.DB) error {
		// Update participant state
		if err := tx.Model(&model.ParticipantTransaction{}).
			Where("order_id = ?", orderId).
			Update("state", schemas.ParticipantStateCommitted).Error; err != nil {
			return err
		}

		// Get the reservation to get quantity and product info
		var reservation model.InventoryReservation
		if err := tx.First(&reservation, "order_id = ?", orderId).Error; err != nil {
			return err
		}

		// Update reservation status to committed
		if err := tx.Model(&model.InventoryReservation{}).
			Where("order_id = ?", orderId).
			Update("status", "committed").Error; err != nil {
			return err
		}

		// Reduce total stock permanently (reserved stock becomes consumed)
		if err := tx.Model(&model.Inventory{}).
			Where("product_id = ?", reservation.ProductId).
			Updates(map[string]interface{}{
				"total_stock":    gorm.Expr("total_stock - ?", reservation.Quantity),
				"reserved_stock": gorm.Expr("reserved_stock - ?", reservation.Quantity),
			}).Error; err != nil {
			return err
		}

		return nil
	})
}

func (r *InventoryPostgresRepository) AbortInventory(orderId string) error {
	return r.db.Transaction(func(tx *gorm.DB) error {
		// Update participant state
		if err := tx.Model(&model.ParticipantTransaction{}).
			Where("order_id = ?", orderId).
			Update("state", schemas.ParticipantStateAborted).Error; err != nil {
			return err
		}

		// Get the reservation to get quantity and product info
		var reservation model.InventoryReservation
		if err := tx.First(&reservation, "order_id = ?", orderId).Error; err != nil {
			return err
		}

		// Delete the reservation (rollback)
		if err := tx.Where("order_id = ?", orderId).Delete(&model.InventoryReservation{}).Error; err != nil {
			return err
		}

		// Release reserved stock back to available stock
		if err := tx.Model(&model.Inventory{}).
			Where("product_id = ?", reservation.ProductId).
			Updates(map[string]interface{}{
				"reserved_stock":  gorm.Expr("reserved_stock - ?", reservation.Quantity),
				"available_stock": gorm.Expr("available_stock + ?", reservation.Quantity),
			}).Error; err != nil {
			return err
		}

		return nil
	})
}

func (r *InventoryPostgresRepository) BeginTransaction() *gorm.DB {
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
