package com.discordshop.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class Product(val id: Int, val name: String, val category: String, val price: Float)

object Products : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 128)
    val category = varchar("category", 128)
    val price = float("price")

    override val primaryKey = PrimaryKey(id)
}
