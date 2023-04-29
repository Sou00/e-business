package com.discordshop.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Payment(val id: Int, val orderId: Int, val total: Float, var paid: Boolean)
object Payments : Table() {
    val id = integer("id").autoIncrement()
    val orderId = integer("orderId")
    val total = float("total")
    var paid = bool("paid")

    override val primaryKey = PrimaryKey(id)
}