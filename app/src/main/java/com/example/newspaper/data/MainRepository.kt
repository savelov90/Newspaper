package com.example.newspaper.data

import com.example.newspaper.data.db_fav.ArticleFavorite
import com.example.newspaper.data.db_first.dao.NewsDao
import com.example.newspaper.data.db_first.entity.Article
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

    fun putToFav(articleFavorite: ArticleFavorite) {
        Executors.newSingleThreadExecutor().execute {
            newsDao.insertFav(articleFavorite)
        }
    }

    fun deleteFromFav(articleFavorite: ArticleFavorite) {
        Executors.newSingleThreadExecutor().execute {
            newsDao.deleteFav(articleFavorite)
        }
    }

    fun getAllFromFav(): List<ArticleFavorite> {
        return newsDao.getFav()
    }

}