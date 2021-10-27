package com.example.newspaper.data.db_first.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newspaper.data.db_fav.ArticleFavorite
import com.example.newspaper.data.db_first.entity.Article
import io.reactivex.rxjava3.core.Observable

//Помечаем, что это не просто интерфейс, а Dao-объект
@Dao
interface NewsDao {

    //RETROFIT и HomeFragment - сохранение последних новостей на случай медленного интернета
    //Запрос на всю таблицу
    @Query("SELECT * FROM cach_news")
    fun getCachedNews(): Observable<List<Article>>
    //Кладём списком в БД, в случае конфликта перезаписываем
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Article>)


    //ИЗБРАННОЕ - управление БД избранного
    //Кладём элемент избранного в БД, в случае конфликта перезаписываем
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFav(articleFavorite: ArticleFavorite)
    //Удаление новости из избранного
    @Delete
    fun deleteFav(articleFavorite: ArticleFavorite)
    @Query("SELECT * FROM fav_news")
    fun getFav(): Observable<List<ArticleFavorite>>

    @Query("SELECT * FROM fav_news WHERE title LIKE :search")
    fun checkFav(search: String): ArticleFavorite
}