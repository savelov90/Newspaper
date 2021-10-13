package com.example.newspaper.data

import com.example.newspaper.data.entity.NewsData
import retrofit2.Call
import retrofit2.http.GET


interface NewsApi {
    @GET("v2/everything?q=Apple&from=2021-09-13&sortBy=popularity&apiKey=c17983dc3961448588a71f963734ff32")
    fun getNews(): Call<NewsData>
}