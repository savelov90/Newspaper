package com.example.newspaper.data

data class NewsData(
        val articles: List<Article>,
        val status: String,
        val totalResults: Int
)