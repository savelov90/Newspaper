package com.example.newspaper.data.db_fav

import android.os.Parcelable
import androidx.room.*
import com.example.newspaper.data.db_first.entity.Source
import kotlinx.android.parcel.RawValue
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "fav_news", indices = [Index(value = ["title"], unique = true)])

data class ArticleFavorite (
            @PrimaryKey(autoGenerate = true) val id: Int = 0,
            @ColumnInfo(name = "date") val publishedAt: String,
            @ColumnInfo(name = "desc") val description: String,
            @Embedded val source: @RawValue Source,
            @ColumnInfo(name = "title") val title: String,
            //val url: String,
            @ColumnInfo(name = "picture_path") val urlToImage: String,
            var isInFavorites: Boolean = false
    ) : Parcelable
