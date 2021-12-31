package com.example.plugins.service

import com.example.plugins.entites.FeiShuMessageData
import com.example.plugins.entites.myEnvironment
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.request.*
import kotlinx.serialization.Serializable

/**
 * @program  KtorAndFeiShu
 * @description
 * @author 空城
 * @date 2021-12-29 11:20
 **/
class FeiShuService {
    val gson=Gson()
    val client=HttpClient(CIO){
        install(JsonFeature){
            serializer = GsonSerializer()
        }
    }


    //获取飞书的Token
    suspend fun getToken(): FeiShuTokenResponse {
        //从application.conf中获取参数内容
        val appId= myEnvironment.config.property("feishu.app_id").getString()
        val appSecret= myEnvironment.config.property("feishu.app_secret").getString()
        val tokenUrl=myEnvironment.config.property("feishu.token_url").getString()
        val idAndSecret= IdWithSecret(appId, appSecret)
        //====end====
        val response: HttpResponse = client.request(tokenUrl){
            method = HttpMethod.Post
            body = gson.toJson(idAndSecret)
        }
       val body= response.receive<FeiShuTokenResponse>()
        print(body.tenant_access_token)
        return body
    }

    suspend fun sendMessage(call: ApplicationCall): FeiShuResultMsg {
        val image = myEnvironment.config.property("feishu.image").getString()
        val sendUrl = myEnvironment.config.property("feishu.sendMessageUrl").getString()
        val message: FeiShuVo = call.receive();
        val feishu_token: String = getToken().tenant_access_token
        val bodyData = FeiShuMessageData.fromBodyData(
            image = image,
            userId = message.receive_id,
            title1 = message.message_title,
            content = message.message_content,
            msgType = message.msg_type
        )

        val request1: HttpResponse = client.post("$sendUrl?receive_id_type = ${message.receive_id_type}") {
            body = gson.toJson(bodyData)
           header("Authorization","Bearer $feishu_token")
        }
        return  request1.receive<FeiShuResultMsg>()
    }

data class FeiShuResultMsg(val code: Int,val data:String, val msg: String)


data class FeiShuTokenResponse(
        val code: Int,
        val expire: Int,
        val msg: String,
        val tenant_access_token: String,
    )

    private data class IdWithSecret(val app_id: String, val app_secret: String)

}

private data class FeiShuVo(
    val receive_id_type:String,
    val receive_id:String,
    val msg_type:String,
    val message_title:String,
    val message_content:String
)

