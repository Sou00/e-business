package controllers

import (
	"GoShop/models"
	"github.com/gin-gonic/gin"
	"net/http"
	"strconv"
)

const errorNotFound = "record not found"

func CreateCart(c *gin.Context) {

	cart := models.Cart{Total: 0}
	models.DB.Create(&cart)

	c.JSON(http.StatusOK, gin.H{"data": cart})
}

func GetCart(c *gin.Context) {
	var cart models.Cart

	if err := models.DB.Preload("Products.Category").Where(queryId, c.Param("id")).First(&cart).Error; err != nil {
		c.AbortWithStatusJSON(http.StatusNotFound, gin.H{"error": err.Error()})
		return
	}

	c.JSON(http.StatusOK, gin.H{"data": cart})
}

func GetCarts(c *gin.Context) {
	var carts []models.Cart
	models.DB.Preload("Products.Category").Find(&carts)
	c.JSON(http.StatusOK, gin.H{"data": carts})
}

type UpdateCartInput struct {
	ProductId uint `json:"ProductId"`
}

func UpdateCart(c *gin.Context) {
	var cart models.Cart
	if err := models.DB.Where(queryId, c.Param("id")).First(&cart).Error; err != nil {
		c.AbortWithStatusJSON(http.StatusNotFound, gin.H{"error": errorNotFound})
		return
	}

	var input UpdateCartInput
	if err := c.ShouldBindJSON(&input); err != nil {
		c.AbortWithStatusJSON(http.StatusBadRequest, gin.H{"error": err.Error()})
		return
	}
	if err := models.DB.Where(queryId, input.ProductId).First(&models.Product{}).Error; err != nil {
		c.AbortWithStatusJSON(http.StatusNotFound, gin.H{"error": err.Error()})
		return
	}
	err := models.DB.Model(&cart).Association("Products").Append([]*models.Product{{ID: input.ProductId}})
	if err != nil {
		c.AbortWithStatusJSON(http.StatusNotFound, gin.H{"error": err.Error()})
		return
	}
	c.JSON(http.StatusOK, gin.H{"data": cart})
}

func DeleteCart(c *gin.Context) {
	var cart models.Cart
	if err := models.DB.Where(queryId, c.Param("id")).First(&cart).Error; err != nil {
		c.AbortWithStatusJSON(http.StatusNotFound, gin.H{"error": errorNotFound})
		return
	}

	models.DB.Delete(&cart)
	c.JSON(http.StatusOK, gin.H{"data": "success"})
}
func DeleteCartItem(c *gin.Context) {
	var cart models.Cart
	if err := models.DB.Where(queryId, c.Param("id")).First(&cart).Error; err != nil {
		c.AbortWithStatusJSON(http.StatusNotFound, gin.H{"error": errorNotFound})
		return
	}
	if err := models.DB.Where(queryId, c.Param("productId")).First(&models.Product{}).Error; err != nil {
		c.AbortWithStatusJSON(http.StatusNotFound, gin.H{"error": err.Error()})
		return
	}
	i, err := strconv.ParseUint(c.Param("productId"), 10, 64)
	if err != nil {
		c.AbortWithStatusJSON(http.StatusNotAcceptable, gin.H{"error": err.Error()})
		return
	}
	err = models.DB.Model(&cart).Association("Products").Delete([]*models.Product{{ID: uint(i)}})
	if err != nil {
		c.AbortWithStatusJSON(http.StatusNotFound, gin.H{"error": err.Error()})
		return
	}
	c.JSON(http.StatusOK, gin.H{"data": "success"})
}
