package controllers

import (
	"GoShop/models"
	"github.com/gin-gonic/gin"
	"net/http"
)

type CreateCategoryInput struct {
	Name string `json:"Name" binding:"required"`
}

func CreateCategory(c *gin.Context) {
	var input CreateCategoryInput
	if err := c.ShouldBindJSON(&input); err != nil {
		c.AbortWithStatusJSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	category := models.Category{Name: input.Name}
	models.DB.Create(&category)

	c.JSON(http.StatusOK, gin.H{"data": category})
}

func GetCategory(c *gin.Context) {
	var category models.Category

	if err := models.DB.Where(queryId, c.Param("id")).First(&category).Error; err != nil {
		c.AbortWithStatusJSON(http.StatusNotFound, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"data": category})
}

func GetCategories(c *gin.Context) {
	var categories []models.Category
	models.DB.Find(&categories)

	c.JSON(http.StatusOK, gin.H{"data": categories})
}

type UpdateCategoryInput struct {
	Name  string `json:"name"`
	Price uint   `json:"price"`
}

func UpdateCategory(c *gin.Context) {
	var category models.Category
	if err := models.DB.Where(queryId, c.Param("id")).First(&category).Error; err != nil {
		c.AbortWithStatusJSON(http.StatusNotFound, gin.H{"error": errorNotFound})
		return
	}

	var input UpdateCategoryInput

	if err := c.ShouldBindJSON(&input); err != nil {
		c.AbortWithStatusJSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}

	updatedcategory := models.Category{Name: input.Name}

	models.DB.Model(&category).Updates(&updatedcategory)
	c.JSON(http.StatusOK, gin.H{"data": category})
}

func DeleteCategory(c *gin.Context) {
	var category models.Category
	if err := models.DB.Where(queryId, c.Param("id")).First(&category).Error; err != nil {
		c.AbortWithStatusJSON(http.StatusNotFound, gin.H{"error": errorNotFound})
		return
	}

	models.DB.Delete(&category)
	c.JSON(http.StatusOK, gin.H{"data": "success"})
}
