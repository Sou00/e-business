package com.discordshop.dao

import com.discordshop.models.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Sequence
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    val seq = Sequence("OrderId", 1, 1)
    fun init() {
        val driverClassName = "org.h2.Driver"
        val jdbcURL = "jdbc:h2:file:./build/db"
        val database = Database.connect(jdbcURL, driverClassName)

        transaction(database) {
            SchemaUtils.create(Categories, Products, Orders, Payments, Users)
            SchemaUtils.createSequence(seq)
        }
    }
    fun Transaction.nextValueOf(sequence: Sequence): Long = exec("SELECT nextval('${sequence.identifier}');") { resultSet ->

        if (resultSet.next().not()) {
            throw Error("Missing nextValue in resultSet of sequence '${sequence.identifier}'")
        } else {
            resultSet.getLong(1)
        }
    } ?: throw Error("Unable to get nextValue of sequence '${sequence.identifier}'")
    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
