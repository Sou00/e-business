package controllers

import (
	"GoShop/models"
	"github.com/gin-gonic/gin"
	"net/http"
)

type CreateProductInput struct {
	Name       string `json:"Name" binding:"required"`
	Price      uint   `json:"Price" binding:"required"`
	CategoryID uint   `json:"CategoryId" binding:"required"`
}

const queryId = "id = ?"

func CreateProduct(c *gin.Context) {
	var input CreateProductInput
	if err := c.ShouldBindJSON(&input); err != nil {
		c.AbortWithStatusJSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	product := models.Product{Name: input.Name, Price: input.Price, CategoryID: input.CategoryID}
	if err := models.DB.Where(queryId, input.CategoryID).First(&models.Category{}).Error; err != nil {
		c.AbortWithStatusJSON(http.StatusNotFound, gin.H{"error": err.Error()})
		return
	}
	models.DB.Create(&product)

	c.JSON(http.StatusOK, gin.H{"data": product})
}

func GetProduct(c *gin.Context) {
	var product models.Product

	if err := models.DB.Preload("Category").Where(queryId, c.Param("id")).First(&product).Error; err != nil {
		c.AbortWithStatusJSON(http.StatusNotFound, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"data": product})
}

func GetProducts(c *gin.Context) {
	var products []models.Product
	models.DB.Preload("Category").Find(&products)
	c.JSON(http.StatusOK, gin.H{"data": products})
}

type UpdateProductInput struct {
	Name       string `json:"name"`
	Price      uint   `json:"price"`
	CategoryId uint   `json:"CategoryId"`
}

func UpdateProduct(c *gin.Context) {
	var product models.Product
	if err := models.DB.Where(queryId, c.Param("id")).First(&product).Error; err != nil {
		c.AbortWithStatusJSON(http.StatusNotFound, gin.H{"error": "record not found"})
		return
	}

	var input UpdateProductInput

	if err := c.ShouldBindJSON(&input); err != nil {
		c.AbortWithStatusJSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	updatedproduct := models.Product{Name: input.Name, Price: input.Price, CategoryID: input.CategoryId}

	models.DB.Model(&product).Updates(&updatedproduct)
	c.JSON(http.StatusOK, gin.H{"data": product})
}

func DeleteProduct(c *gin.Context) {
	var product models.Product
	if err := models.DB.Where(queryId, c.Param("id")).First(&product).Error; err != nil {
		c.AbortWithStatusJSON(http.StatusNotFound, gin.H{"error": "record not found"})
		return
	}

	models.DB.Delete(&product)
	c.JSON(http.StatusOK, gin.H{"data": "success"})
}
