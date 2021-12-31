package com.example

import com.example.plugins.entites.myApplicationEnvironment
import com.example.plugins.routes.getFeiShu
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.response.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation){
        gson()

    }

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
        getFeiShu()
        myApplicationEnvironment()
    }
}


