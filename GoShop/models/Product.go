package models

import "gorm.io/gorm"

type Product struct {
	gorm.Model
	ID         uint
	Name       string
	Price      uint
	CategoryID uint
	Category   Category `gorm:"constraint:OnUpdate:CASCADE,OnDelete:SET NULL;"`
}
