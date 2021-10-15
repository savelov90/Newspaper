package com.example.newspaper.data.db_first.entity

data class NewsData(
        val articles: List<Article>,
        val status: String,
        val totalResults: Int
)