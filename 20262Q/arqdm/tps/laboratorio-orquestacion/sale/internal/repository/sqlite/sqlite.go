package sqlite

import (
	"gitlab.com/itba-73-40-microservicios/profesores/laboratorio-orquestacion/sale/pkg/model"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

type SaleSqliteRepository struct {
	db *gorm.DB
}

// getDatabaseConnection initializes a new database connection using GORM with SQLite.
func getDatabaseConnection() (*gorm.DB, error) {
	db, err := gorm.Open(sqlite.Open("gorm.db"), &gorm.Config{})
	if err != nil {
		return nil, err
	}

	return db, nil
}

func NewSaleDatabaseRepository() *SaleSqliteRepository {
	db, err := getDatabaseConnection()
	if err != nil {
		panic("Failed to connect to database: " + err.Error())
	}

	if err := db.AutoMigrate(&model.Sale{}); err != nil {
		panic("Failed to migrate database: " + err.Error())
	}

	return &SaleSqliteRepository{db: db}
}

func (r *SaleSqliteRepository) Save(sale model.Sale) error {
	result := r.db.Create(&sale)
	return result.Error
}

func (r *SaleSqliteRepository) UpdateStatus(orderId string, status string) error {
	result := r.db.Model(&model.Sale{}).Where("order_id = ?", orderId).Update("status", status)
	return result.Error
}
