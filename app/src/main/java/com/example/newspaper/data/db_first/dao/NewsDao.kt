package com.example.newspaper.data.db_first.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newspaper.data.db_fav.ArticleFavorite
import com.example.newspaper.data.db_first.entity.Article

//Помечаем, что это не просто интерфейс, а Dao-объект
@Dao
interface NewsDao {
    //Запрос на всю таблицу
    @Query("SELECT * FROM cached_news")
    fun getCachedFilms(): List<Article>

    //Кладём списком в БД, в случае конфликта перезаписываем
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Article>)

    //Кладём элемент избранного в БД, в случае конфликта перезаписываем
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFav(articleFavorite: ArticleFavorite)
}