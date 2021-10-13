package com.example.newspaper.data.entity

data class NewsData(
        val articles: List<Article>,
        val status: String,
        val totalResults: Int
)