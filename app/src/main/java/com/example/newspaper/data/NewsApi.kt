package com.example.newspaper.data

import com.example.newspaper.data.db_first.entity.NewsData
import retrofit2.Call
import retrofit2.http.GET


interface NewsApi {
    @GET("v2/top-headlines?country=us&apiKey=c17983dc3961448588a71f963734ff32")
    fun getNews(): Call<NewsData>
}