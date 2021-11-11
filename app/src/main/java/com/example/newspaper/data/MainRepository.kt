package com.example.newspaper.data

import androidx.lifecycle.LiveData
import com.example.newspaper.data.db_fav.ArticleFavorite
import com.example.newspaper.data.db_first.dao.NewsDao
import com.example.newspaper.data.db_first.entity.Article
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.Executors

class MainRepository(private val newsDao: NewsDao) {

    fun putToDb(article: List<Article>) {
        //Запросы в БД должны быть в отдельном потоке
        Executors.newSingleThreadExecutor().execute {
            newsDao.insertAll(article)
        }
    }

    fun getAllFromDB(): Observable<List<Article>> = newsDao.getCachedNews()

    fun deleteAll(): Int = newsDao.deleteAll()


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

    fun getAllFromFav(): Observable<List<ArticleFavorite>> = newsDao.getFav()

    fun checkFav(search: String): Observable<ArticleFavorite> = newsDao.checkFav(search)
}