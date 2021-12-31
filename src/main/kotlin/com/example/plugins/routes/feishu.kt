package com.example.plugins.routes

import com.example.plugins.controller.FeiShuController
import io.ktor.application.*
import io.ktor.routing.*

/**
 * @program  KtorAndFeiShu
 * @description
 * @author 空城
 * @date 2021-12-29 10:42
 **/
 val feishuController : FeiShuController = FeiShuController()
 fun Application.getFeiShu(){
     routing {
         route("feishu"){
             getFeiShuToken()
         }
     }
 }
fun Route.getFeiShuToken(){

    route("/getToken"){
        post {
            feishuController.sendMessageToFeiShu(call)
        }
    }

}