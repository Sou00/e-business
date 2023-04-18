package main

import (
	"GoShop/controllers"
	"GoShop/models"
	"github.com/gin-gonic/gin"
)

func main() {
	router := gin.Default()

	models.ConnectDatabase() // new!

	router.POST("/products", controllers.CreateProduct)
	router.GET("/products", controllers.GetProducts)
	router.GET("/products/:id", controllers.GetProduct)
	router.PUT("/products/:id", controllers.UpdateProduct)
	router.DELETE("/products/:id", controllers.DeleteProduct)

	router.POST("/categories", controllers.CreateCategory)
	router.GET("/categories", controllers.GetCategories)
	router.GET("/categories/:id", controllers.GetCategory)
	router.PUT("/categories/:id", controllers.UpdateCategory)
	router.DELETE("/categories/:id", controllers.DeleteCategory)

	router.POST("/carts", controllers.CreateCart)
	router.GET("/carts", controllers.GetCarts)
	router.GET("/carts/:id", controllers.GetCart)
	router.PUT("/carts/:id", controllers.UpdateCart)
	router.DELETE("/carts/:id/:productId", controllers.DeleteCartItem)

	router.Run("localhost:8080")
}
