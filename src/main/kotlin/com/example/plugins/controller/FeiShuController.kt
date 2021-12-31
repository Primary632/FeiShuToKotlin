package com.example.plugins.controller

import com.example.plugins.service.FeiShuService
import io.ktor.application.*
import io.ktor.response.*

class FeiShuController {
    private val feiShuService: FeiShuService =FeiShuService()
    suspend fun sendMessageToFeiShu(call: ApplicationCall) {
       val body  =  feiShuService.sendMessage(call)
        call.respond(body)
    }

}

