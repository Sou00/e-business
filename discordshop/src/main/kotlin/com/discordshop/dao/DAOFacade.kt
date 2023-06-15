package com.discordshop.dao

import com.discordshop.models.*

interface DAOFacade {
    suspend fun allProducts(): List<Product>
    suspend fun product(id: Int): Product?
    suspend fun addProduct(product: Product): Product?
    suspend fun updateProduct(product: Product): Boolean
    suspend fun deleteProduct(id: Int): Boolean

    // Category
    suspend fun allCategories(): List<Category>
    suspend fun category(id: Int): Category?
    suspend fun addCategory(category: Category): Category?
    suspend fun updateCategory(category: Category): Boolean
    suspend fun deleteCategory(id: Int): Boolean

    // Order
    suspend fun allOrders(): List<Order>
    suspend fun order(id: Int): List<Order>
    suspend fun addOrder(order: Order): Order?
    suspend fun addOrders(orders: List<Order>): List<Order>?
    suspend fun updateOrder(order: Order): Boolean
    suspend fun deleteOrder(id: Int): Boolean

    // Payment
    suspend fun allPayments(): List<Payment>
    suspend fun payment(id: Int): Payment?
    suspend fun addPayment(payment: Payment): Payment?
    suspend fun updatePayment(payment: Payment): Boolean
    suspend fun deletePayment(id: Int): Boolean

    // User
    suspend fun allUsers(): List<User>
    suspend fun user(login: String): User?
    suspend fun addUser(user: User): User?
    suspend fun updateUser(user: User): Boolean
    suspend fun deleteUser(id: Int): Boolean
}
