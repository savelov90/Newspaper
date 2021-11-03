package com.example.newspaper.data

import com.example.newspaper.data.db_first.entity.NewsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApi {
    @GET("v2/top-headlines")
    fun getNews(
            @Query("country") language: String,
            @Query("apiKey") apiKey: String
    ): Call<NewsData>
}