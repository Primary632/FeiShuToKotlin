package com.example.plugins.entites

import com.google.gson.Gson
import kotlinx.serialization.Serializable

/**
 * @program  KtorAndFeiShu
 * @description 飞书的数据转换
 * @author 空城
 * @date 2021-12-30 14:33
 **/
@Serializable
data class FeiShuMessageData(
        val msg_type:String,
        val content:String,
        val receive_id:String
    ){
        companion object{
            fun fromBodyData(image:String,userId:String,title1:String,content: String,msgType:String): FeiShuMessageData {
               val content=Interactive(
                   elements = listOf(Interactive.Element(
                       extra = Interactive.Element.Extra(
                           alt = Interactive.Element.Extra.Alt(content = "", tag = ""),
                           img_key = image
                       ),
                       text = Interactive.Element.Text(content = content))
                   ),
                   header= Interactive.Header(
                       title =Interactive.Header.Title(content = title1)
                   ))
              return  FeiShuMessageData(msgType,Gson().toJson(content),userId)
            }
        }
    }




//发送的卡片信息封装
@Serializable
private data class Interactive(
    val elements: List<Element>,
    val header: Header,
) {

    data class Element(
        val extra: Extra,
        val tag: String = "div",
        val text: Text,

        ) {
        data class Extra(
            val alt: Alt,
            val img_key: String,
            val tag: String = "img",
        ) {
            data class Alt(
                val content: String ,
                val tag: String = "plain_text",
            )
        }
        data class Text(
            val content: String,
            val tag: String = "plain_text",
        )
    }

    data class Header(
        val template: String="orange",
        val title: Title,
    ) {
        data class Title(
            val content: String,
            val tag: String = "plain_text",
        )
    }
}