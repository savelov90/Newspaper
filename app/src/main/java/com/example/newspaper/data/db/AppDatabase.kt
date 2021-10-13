package com.example.newspaper.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.newspaper.data.dao.NewsDao
import com.example.newspaper.data.entity.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao() : NewsDao
}