package main

import (
	"GoShop/controllers"
	"GoShop/models"
	"github.com/gin-gonic/gin"
)

const ProductsId = "/products/:id"
const CategoriesId = "/categories/:id"

func main() {
	router := gin.Default()

	models.ConnectDatabase() // new!

	router.POST("/products", controllers.CreateProduct)
	router.GET("/products", controllers.GetProducts)
	router.GET(ProductsId, controllers.GetProduct)
	router.PUT(ProductsId, controllers.UpdateProduct)
	router.DELETE(ProductsId, controllers.DeleteProduct)

	router.POST("/categories", controllers.CreateCategory)
	router.GET("/categories", controllers.GetCategories)
	router.GET(CategoriesId, controllers.GetCategory)
	router.PUT(CategoriesId, controllers.UpdateCategory)
	router.DELETE(CategoriesId, controllers.DeleteCategory)

	router.POST("/carts", controllers.CreateCart)
	router.GET("/carts", controllers.GetCarts)
	router.GET("/carts/:id", controllers.GetCart)
	router.PUT("/carts/:id", controllers.UpdateCart)
	router.DELETE("/carts/:id/:productId", controllers.DeleteCartItem)

	router.Run("localhost:8080")
}
