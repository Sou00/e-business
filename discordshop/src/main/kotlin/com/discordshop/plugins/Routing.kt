package com.discordshop.plugins

import com.discordshop.dao.dao
import com.discordshop.models.*
import com.slack.api.Slack
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*
const val CHANNEL_NAME = "#shop-bot"
fun Application.configureRouting() {

    routing {
        post("/category") {
            val category = call.receive<Category>()
            dao.addCategory(category)
            call.respond(category)
        }
        post("/product"){

            val product = call.receive<Product>()
            dao.addProduct(product)
            call.respond(product)
        }
        get("/product"){
            call.respond(dao.allProducts())
        }

        get("/order"){
            call.respond(dao.allOrders())

        }
        route("/payment"){
            get {              call.respond(dao.allPayments())
            }
            post { val payment = call.receive<Payment>()
                dao.addPayment(payment)
                call.respond(payment) }
            put {val payment = call.receive<Payment>()
                dao.updatePayment(payment)
                call.respond(payment)  }
        }
        post("/cart"){
            val cart = call.receive<List<Order>>()
            dao.addOrders(cart)
            call.respond(cart)
        }

        post("/slack/category" ) {
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
            }
            else{
                val response = slack.methods(token).chatPostMessage {
                    it.channel(CHANNEL_NAME)
                        .text("There are no categories yet!")
                }
                call.respondText("Response is: $response")
            }
        }
        post("/slack/product" ) {
            val formParameters = call.receiveParameters()
            val text = formParameters["text"].toString()
            val token = System.getenv("SLACK_TOKEN")
            val slack = Slack.getInstance()
            val products = dao.allProducts().filter { it.category == text }
            if(products.isNotEmpty()) {
                var result = text + "\n"
                products.forEach {
                    result += it.id.toString() + ". " + it.name + "\n"
                }
                val response = slack.methods(token).chatPostMessage {
                    it.channel(CHANNEL_NAME)
                        .text(result)
                }
                call.respondText("Response is: $response")
            }
            else{
                val response = slack.methods(token).chatPostMessage {
                    it.channel(CHANNEL_NAME)
                        .text("There are no products for that category yet!")
                }
                call.respondText("Response is: $response")
            }
        }
    }
}
