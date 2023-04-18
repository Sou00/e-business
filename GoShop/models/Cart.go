package models

import "gorm.io/gorm"

type Cart struct {
	gorm.Model
	ID       uint
	Products []Product `gorm:"many2many:cart_products;"`
	Total    uint
}
