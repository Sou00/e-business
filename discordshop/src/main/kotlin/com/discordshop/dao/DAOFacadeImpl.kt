package com.discordshop.dao

import com.discordshop.dao.DatabaseFactory.dbQuery
import com.discordshop.dao.DatabaseFactory.nextValueOf
import com.discordshop.dao.DatabaseFactory.seq
import com.discordshop.models.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
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

    override suspend fun product(id: Int): Product? = dbQuery {
        Products
            .select { Products.id eq id }
            .map(::resultRowToProduct)
            .singleOrNull()
    }

    override suspend fun addProduct(product: Product): Product? = dbQuery {
        val insertStatement = Products.insert {
            it[name] = product.name
            it[category] = product.category
            it[price] = product.price
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToProduct)
    }

    override suspend fun updateProduct(product: Product): Boolean = dbQuery {
        Products.update({ Products.id eq product.id }) {
            it[name] = product.name
            it[category] = product.category
            it[price] = product.price
        } > 0
    }

    override suspend fun deleteProduct(id: Int): Boolean = dbQuery {
        Products.deleteWhere { Products.id eq id } > 0
    }

    override suspend fun addOrders(orders: List<Order>): List<Order> = dbQuery {
        val res = mutableListOf<Order>()
        var sum = 0.0
        var seqId = 0
        transaction {
            seqId = nextValueOf(seq).toInt()
        }
        orders.forEach { order ->
            val insertStatement = Orders.insert {
                it[id] = seqId
                it[productId] = order.productId
                it[quantity] = order.quantity
            }
            sum += product(order.productId)?.price!! * order.quantity
            insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToOrder)?.let { res.add(it) }
        }
        addPayment(Payment(1, seqId, sum.toFloat(), false))
        res
    }

    override suspend fun updateOrder(order: Order): Boolean = dbQuery {
        Orders.update({ Orders.id eq order.id }) {
            it[productId] = order.productId
            it[quantity] = order.quantity
        } > 0
    }

    override suspend fun deleteOrder(id: Int): Boolean = dbQuery {
        Orders.deleteWhere { Orders.id eq id } > 0
    }

    override suspend fun addPayment(payment: Payment): Payment? = dbQuery {
        val insertStatement = Payments.insert {
            it[orderId] = payment.orderId
            it[total] = payment.total
            it[paid] = payment.paid
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToPayment)
    }
    override suspend fun addCategory(category: Category): Category? = dbQuery {
        val insertStatement = Categories.insert {
            it[name] = category.name
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToCategory)
    }

    override suspend fun updateCategory(category: Category): Boolean = dbQuery {
        Categories.update({ Categories.id eq category.id }) {
            it[name] = category.name
        } > 0
    }

    override suspend fun deleteCategory(id: Int): Boolean = dbQuery {
        Categories.deleteWhere { Categories.id eq id } > 0
    }

    override suspend fun allOrders(): List<Order> = dbQuery {
        Orders.selectAll().map(::resultRowToOrder)
    }

    override suspend fun order(id: Int): List<Order> = dbQuery {
        Orders
            .select { Orders.id eq id }
            .map(::resultRowToOrder)
    }

    override suspend fun addOrder(order: Order): Order? = dbQuery {
        var seqId = 0
        transaction {
            seqId = nextValueOf(seq).toInt()
        }
        val insertStatement = Orders.insert {
            it[id] = seqId
            it[productId] = order.productId
            it[quantity] = order.quantity
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToOrder)
    }

    override suspend fun allCategories(): List<Category> = dbQuery {
        Categories.selectAll().map(::resultRowToCategory)
    }

    override suspend fun category(id: Int): Category? = dbQuery {
        Categories
            .select { Categories.id eq id }
            .map(::resultRowToCategory)
            .singleOrNull()
    }

    override suspend fun allPayments(): List<Payment> = dbQuery {
        Payments.selectAll().map(::resultRowToPayment)
    }

    override suspend fun payment(id: Int): Payment? = dbQuery {
        Payments
            .select { Payments.id eq id }
            .map(::resultRowToPayment)
            .singleOrNull()
    }

    override suspend fun updatePayment(payment: Payment): Boolean = dbQuery {
        Payments.update({ Payments.id eq payment.id }) {
            it[paid] = payment.paid
        } > 0
    }

    override suspend fun deletePayment(id: Int): Boolean = dbQuery {
        Payments.deleteWhere { Payments.id eq id } > 0
    }
}

val dao: DAOFacade = DAOFacadeImpl()
