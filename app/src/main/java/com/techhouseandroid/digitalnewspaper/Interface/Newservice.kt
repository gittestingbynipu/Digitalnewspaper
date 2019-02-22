package com.techhouseandroid.digitalnewspaper.Interface

import com.techhouseandroid.digitalnewspaper.Model.News
import com.techhouseandroid.digitalnewspaper.Model.Website
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface Newservice {

    @get:GET("v2/sources?apiKey=27a7b8dd6b234dd58f3ddaee9faf3a0f")
    val sources: Call<Website>


    @GET
    fun getNewsFromSource(@Url url: String): Call<News>
}