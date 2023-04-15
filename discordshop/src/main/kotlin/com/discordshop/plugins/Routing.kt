package com.discordshop.plugins

import com.discordshop.Category
import com.discordshop.Global
import com.discordshop.Product
import com.slack.api.Slack
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import java.lang.System.getenv

fun Application.configureRouting() {
    routing {
        post("/category") {
            val category = call.receive<Category>()
            Global.categories.add(category)
            call.respond(category)
        }
        post("/product"){

            val product = call.receive<Product>()
            Global.products.add(product)
            call.respond(product)
        }
        post("/slack/category" ) {
            val token = System.getenv("SLACK_TOKEN")
            val slack = Slack.getInstance()
            val categories = Global.categories
            if (categories.isNotEmpty()) {
                var result = "Categories:\n"
                categories.forEach {
                    result += it.id.toString() + ". " + it.name + "\n"
                }
                val response = slack.methods(token).chatPostMessage {
                    it.channel("#shop-bot")
                        .text(result)
                }
                call.respondText("Response is: $response")
            }
            else{
                val response = slack.methods(token).chatPostMessage {
                    it.channel("#shop-bot")
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
            val products = Global.products.filter { it.category == text }
            if(products.isNotEmpty()) {
                var result = text + "\n"
                products.forEach {
                    result += it.id.toString() + ". " + it.name + "\n"
                }
                val response = slack.methods(token).chatPostMessage {
                    it.channel("#shop-bot")
                        .text(result)
                }
                call.respondText("Response is: $response")
            }
            else{
                val response = slack.methods(token).chatPostMessage {
                    it.channel("#shop-bot")
                        .text("There are no products for that category yet!")
                }
                call.respondText("Response is: $response")
            }
        }
    }
}
