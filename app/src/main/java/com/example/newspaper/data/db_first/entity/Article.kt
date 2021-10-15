package com.example.newspaper.data.db_first.entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.RawValue
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "cached_news", indices = [Index(value = ["title"], unique = true)])
data class Article(
    //val author: String,
   // val content: String,

        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        @ColumnInfo(name = "date") val publishedAt: String,
        @ColumnInfo(name = "desc") val description: String,
        @Embedded val source: @RawValue Source,
        @ColumnInfo(name = "title") val title: String,
    //val url: String,
        @ColumnInfo(name = "picture_path") val urlToImage: String,
        var isInFavorites: Boolean = false
) : Parcelable

