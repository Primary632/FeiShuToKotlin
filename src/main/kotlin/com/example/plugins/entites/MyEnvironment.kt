package com.example.plugins.entites

import io.ktor.application.*
import io.ktor.config.*
import io.ktor.server.engine.*

/**
 * @program  KtorAndFeiShu
 * @description
 * @author 空城
 * @date 2021-12-30 15:43
 **/
lateinit var myEnvironment: ApplicationEngineEnvironment
fun Application.myApplicationEnvironment(){
    myEnvironment =environment as ApplicationEngineEnvironment
}
fun ApplicationConfig.getProperties(keyName:String): String {
    return this.property(keyName).getString()
}