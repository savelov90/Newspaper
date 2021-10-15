package com.example.newspaper.data.db_first.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newspaper.data.db_fav.ArticleFavorite
import com.example.newspaper.data.db_first.dao.NewsDao
import com.example.newspaper.data.db_first.entity.Article

@Database(entities = [Article::class, ArticleFavorite::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao() : NewsDao
}