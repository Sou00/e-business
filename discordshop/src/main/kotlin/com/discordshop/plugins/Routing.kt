package com.discordshop.plugins

import com.discordshop.dao.dao
import com.discordshop.models.*
import com.slack.api.Slack
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
const val CHANNEL_NAME = "#shop-bot"
fun Application.configureRouting() {
    routing {
        route("/product") {
            get {
                call.respond(dao.allProducts())
            }
            get("/{id}") {
                val id = call.parameters["id"]!!.toInt()
                val product = dao.product(id)
                if (product != null) {
                    call.respond(product)
                } else {
                    call.respond("There is no product with id: $id")
                }
            }
            post {
                var product = call.receive<Product>()
                product = dao.addProduct(product)!!
                call.respond(product)
            }
            put {
                val product = call.receive<Product>()
                if (dao.updateProduct(product)) {
                    call.respond(product)
                } else {
                    call.respond("Update failed!")
                }
            }
            delete("/{id}") {
                val id = call.parameters["id"]!!.toInt()
                if (dao.deleteProduct(id)) {
                    call.respond("Deleted successfully!")
                } else {
                    call.respond("Deletion failed!")
                }
            }
        }
        route("/category") {
            get {
                call.respond(dao.allCategories())
            }
            get("/{id}") {
                val id = call.parameters["id"]!!.toInt()
                val category = dao.category(id)
                if (category != null) {
                    call.respond(category)
                } else {
                    call.respond("There is no category with id: $id")
                }
            }
            post {
                var category = call.receive<Category>()
                category = dao.addCategory(category)!!
                call.respond(category)
            }
            put {
                val category = call.receive<Category>()
                if (dao.updateCategory(category)) {
                    call.respond(category)
                } else {
                    call.respond("Update failed!")
                }
            }
            delete("/{id}") {
                val id = call.parameters["id"]!!.toInt()
                if (dao.deleteCategory(id)) {
                    call.respond("Deleted successfully!")
                } else {
                    call.respond("Deletion failed!")
                }
            }
        }
        route("/order") {
            get {
                call.respond(dao.allOrders())
            }
            get("/{id}") {
                val id = call.parameters["id"]!!.toInt()
                val order = dao.order(id)
                if (order.isNotEmpty()) {
                    call.respond(order)
                } else {
                    call.respond("There is no order with id: $id")
                }
            }
            post {
                var order = call.receive<Order>()
                order = dao.addOrder(order)!!
                call.respond(order)
            }
            put {
                val order = call.receive<Order>()
                if (dao.updateOrder(order)) {
                    call.respond(order)
                } else {
                    call.respond("Update failed!")
                }
            }
            delete("/{id}") {
                val id = call.parameters["id"]!!.toInt()
                if (dao.deleteOrder(id)) {
                    call.respond("Deleted successfully!")
                } else {
                    call.respond("Deletion failed!")
                }
            }
        }
        route("/payment") {
            get {
                call.respond(dao.allPayments())
            }
            get("/{id}") {
                val id = call.parameters["id"]!!.toInt()
                val payment = dao.payment(id)
                if (payment != null) {
                    call.respond(payment)
                } else {
                    call.respond("There is no payment with id: $id")
                }
            }
            post {
                var payment = call.receive<Payment>()
                payment = dao.addPayment(payment)!!
                call.respond(payment)
            }
            put {
                val payment = call.receive<Payment>()
                if (dao.updatePayment(payment)) {
                    call.respond(payment)
                } else {
                    call.respond("Update failed!")
                }
            }
            delete("/{id}") {
                val id = call.parameters["id"]!!.toInt()
                if (dao.deletePayment(id)) {
                    call.respond("Deleted successfully!")
                } else {
                    call.respond("Deletion failed!")
                }
            }
        }
        route("/user") {
            get {
                call.respond(dao.allUsers())
            }
            get("/{login}") {
                val id = call.parameters["login"]!!
                val user = dao.user(id)
                if (user != null) {
                    call.respond(user)
                } else {
                    call.respond("There is no user with login: $id")
                }
            }
            post {
                var user = call.receive<User>()
                user = dao.addUser(user)!!
                call.respond(user)
            }
        }
        post("/cart") {
            val cart = call.receive<List<Order>>()
            dao.addOrders(cart)
            call.respond(cart)
        }

        // Slack
        post("/slack/category") {
            val token = System.getenv("SLACK_TOKEN")
            val slack = Slack.getInstance()
            val categories = dao.allCategories()
            if (categories.isNotEmpty()) {
                var result = "Categories:\n"
                categories.forEach {
                    result += it.id.toString() + ". " + it.name + "\n"
                }
                val response = slack.methods(token).chatPostMessage {
                    it.channel(CHANNEL_NAME)
                        .text(result)
                }
                call.respondText("Response is: $response")
            } else {
                val response = slack.methods(token).chatPostMessage {
                    it.channel(CHANNEL_NAME)
                        .text("There are no categories yet!")
                }
                call.respondText("Response is: $response")
            }
        }
        post("/slack/product") {
            val formParameters = call.receiveParameters()
            val text = formParameters["text"].toString()
            val token = System.getenv("SLACK_TOKEN")
            val slack = Slack.getInstance()
            val products = dao.allProducts().filter { it.category == text }
            if (products.isNotEmpty()) {
                var result = text + "\n"
                products.forEach {
                    result += it.id.toString() + ". " + it.name + "\n"
                }
                val response = slack.methods(token).chatPostMessage {
                    it.channel(CHANNEL_NAME)
                        .text(result)
                }
                call.respondText("Response is: $response")
            } else {
                val response = slack.methods(token).chatPostMessage {
                    it.channel(CHANNEL_NAME)
                        .text("There are no products for that category yet!")
                }
                call.respondText("Response is: $response")
            }
        }
    }
}
