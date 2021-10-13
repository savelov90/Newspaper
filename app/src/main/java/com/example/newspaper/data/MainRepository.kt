package com.example.newspaper.data

import com.example.newspaper.data.dao.NewsDao
import com.example.newspaper.data.entity.Article
import java.util.concurrent.Executors

class MainRepository(private val newsDao: NewsDao) {

    fun putToDb(article: List<Article>) {
        //Запросы в БД должны быть в отдельном потоке
        Executors.newSingleThreadExecutor().execute {
            newsDao.insertAll(article)
        }
    }

    fun getAllFromDB(): List<Article> {
        return newsDao.getCachedFilms()
    }

}