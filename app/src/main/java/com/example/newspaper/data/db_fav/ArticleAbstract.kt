package com.example.newspaper.data.db_fav

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.example.newspaper.data.db_first.entity.Source
import kotlinx.android.parcel.RawValue

abstract class ArticleAbstract : Parcelable {
    abstract var id: Int
    abstract var publishedAt: String
    abstract var description: String
    abstract var source: Source
    abstract var title: String
    //val url: String,
    abstract var urlToImage: String
    abstract var isInFavorites: Boolean

}