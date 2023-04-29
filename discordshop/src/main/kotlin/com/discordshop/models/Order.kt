package com.discordshop.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Order(val id: Int, val productId: Int, val quantity: Int)
object Orders : Table() {
    val id = integer("id").autoIncrement()
    val productId = integer("productId") references Products.id
    val quantity = integer("quantity")

    override val primaryKey = PrimaryKey(id, productId)
}