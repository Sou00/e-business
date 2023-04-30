package com.discordshop.dao

import com.discordshop.models.*

interface DAOFacade {
    suspend fun allProducts(): List<Product>
    suspend fun product(id: Int): Product?
    suspend fun addProduct(product: Product): Product?
    suspend fun updateProduct(product: Product): Boolean
    suspend fun deleteProduct(id: Int): Boolean

    suspend fun addOrders(orders: List<Order>): List<Order>?

    suspend fun addPayment(payment: Payment): Payment?
    suspend fun addCategory(category: Category): Category?
    suspend fun allOrders(): List<Order>
    suspend fun allPayments(): List<Payment>
    suspend fun updatePayment(payment: Payment): Boolean
    suspend fun allCategories(): List<Category>
}
