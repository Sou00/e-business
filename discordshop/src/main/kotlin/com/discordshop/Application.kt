package com.discordshop

import com.discordshop.dao.DatabaseFactory
import com.discordshop.plugins.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import kotlinx.serialization.json.Json

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT").toInt(), host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
            }
        )
    }
    install(CORS) {
        anyHost()
        allowMethod(HttpMethod.Put)
        allowHeader(HttpHeaders.ContentType)
    }
    DatabaseFactory.init()
    configureRouting()
}
