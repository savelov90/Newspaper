package com.example.newspaper.data.db_first.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newspaper.data.db_fav.ArticleFavorite
import com.example.newspaper.data.db_first.entity.Article

//Помечаем, что это не просто интерфейс, а Dao-объект
@Dao
interface NewsDao {

    //RETROFIT и HomeFragment - сохранение последних новостей на случай медленного интернета
    //Запрос на всю таблицу
    @Query("SELECT * FROM cached_news")
    fun getCachedNews(): LiveData<List<Article>>
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
    fun getFav(): List<ArticleFavorite>

}