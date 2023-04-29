package com.discordshop.dao

import com.discordshop.models.*
import org.jetbrains.exposed.sql.*
import com.discordshop.dao.DatabaseFactory.dbQuery
import com.discordshop.dao.DatabaseFactory.nextValueOf
import com.discordshop.dao.DatabaseFactory.seq
import org.jetbrains.exposed.sql.transactions.transaction

class DAOFacadeImpl : DAOFacade {
    private fun resultRowToProduct(row: ResultRow) = Product(
        id = row[Products.id],
        name = row[Products.name],
        category = row[Products.category],
        price = row[Products.price]
    )
    private fun resultRowToCategory(row: ResultRow) = Category(
        id = row[Categories.id],
        name = row[Categories.name]
    )
    private fun resultRowToOrder(row: ResultRow) = Order(
        id = row[Orders.id],
        productId = row[Orders.productId],
        quantity = row[Orders.quantity]
    )
    private fun resultRowToPayment(row: ResultRow) = Payment(
        id = row[Payments.id],
        orderId = row[Payments.orderId],
        total = row[Payments.total],
        paid = row[Payments.paid]
    )
    override suspend fun allProducts(): List<Product> = dbQuery {
        Products.selectAll().map(::resultRowToProduct)

    }

    override suspend fun product(id: Int): Product? = dbQuery{
        Products
            .select { Products.id eq id }
            .map(::resultRowToProduct)
            .singleOrNull()
    }

    override suspend fun addProduct(product: Product): Product? = dbQuery{
        val insertStatement = Products.insert {
            it[name] = product.name
            it[category] = product.category
            it[price] = product.price
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToProduct)
    }

    override suspend fun updateProduct(product: Product): Boolean = dbQuery{
        TODO("Not yet implemented")
    }

    override suspend fun deleteProduct(id: Int): Boolean = dbQuery{
        TODO("Not yet implemented")
    }

    override suspend fun addOrders(orders: List<Order>): List<Order>? = dbQuery{
        val res = mutableListOf<Order>()
        var sum = 0.0
        var seqId = 0
        transaction {
            seqId=nextValueOf(seq).toInt()
        }
        orders.forEach { order->
            val insertStatement = Orders.insert {
                it[id] = seqId
                it[productId] = order.productId
                it[quantity] = order.quantity
            }
            sum+= product(order.productId)?.price!! *order.quantity
            insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToOrder)?.let { res.add(it) }
        }
        addPayment(Payment(1,seqId, sum.toFloat(),false))
        res
    }

    override suspend fun addPayment(payment: Payment): Payment? = dbQuery{
        val insertStatement = Payments.insert {
            it[orderId] = payment.orderId
            it[total] = payment.total
            it[paid] = payment.paid
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToPayment)
    }
    override suspend fun addCategory(category: Category): Category? = dbQuery{
        val insertStatement = Categories.insert {
            it[name] = category.name
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToCategory)
    }

    override suspend fun allOrders(): List<Order> = dbQuery{
        Orders.selectAll().map(::resultRowToOrder)
    }
    override suspend fun allCategories(): List<Category> = dbQuery{
        Categories.selectAll().map(::resultRowToCategory)
    }

    override suspend fun allPayments(): List<Payment> = dbQuery{
        Payments.selectAll().map(::resultRowToPayment)
    }

    override suspend fun updatePayment(payment: Payment): Boolean = dbQuery{
        Payments.update({Payments.id eq payment.id}) {
            it[paid] = payment.paid
        }> 0
    }

}

val dao: DAOFacade = DAOFacadeImpl()