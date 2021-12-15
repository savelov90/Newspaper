package com.example.newspaper.data.db_first.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Source(
    //val id: String,
    val name: String
) : Parcelable