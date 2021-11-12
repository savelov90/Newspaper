package com.example.newspaper.data.db_first.entity

import android.os.Parcelable
import androidx.room.*
import com.example.newspaper.data.db_fav.ArticleAbstract
import com.example.newspaper.R
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
@Entity(tableName = "cach_news", indices = [Index(value = ["title"], unique = true)])
data class Article(
        @PrimaryKey(autoGenerate = true) override var id: Int = 0,
        @ColumnInfo(name = "author", defaultValue = "неизвестен") override var author: String,
        @ColumnInfo(name = "date") override var publishedAt: String,
        @ColumnInfo(name = "desc", defaultValue = "...") override var description: String,
        @Embedded override var source: @RawValue Source,
        @ColumnInfo(name = "url") override var url: String,
        @ColumnInfo(name = "title") override var title: String,
        @ColumnInfo(name = "picture_path", defaultValue = R.string.advert.toString()) override var urlToImage: String,
        @ColumnInfo(name = "favorite") override var isInFavorites: Boolean = false
) : ArticleAbstract(), Parcelable

