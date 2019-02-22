package com.techhouseandroid.digitalnewspaper.Common

import com.techhouseandroid.digitalnewspaper.Interface.Newservice
import com.techhouseandroid.digitalnewspaper.Remote.RefrofitClient
import java.lang.StringBuilder


object Common {
    val BASE_URL = "https://newsapi.org/"
    val API_KEY = "27a7b8dd6b234dd58f3ddaee9faf3a0f"


    val newsService: Newservice
        get() = RefrofitClient.getClient(BASE_URL).create(Newservice::class.java)


    fun getNewsAPI(source:String):String{
        val apiURL=StringBuilder("https://newsapi.org/v2/top-headlines?sources=")

            .append(source)
            .append("&apiKey=")
            .append(API_KEY)
            .toString()
        return apiURL
    }

}