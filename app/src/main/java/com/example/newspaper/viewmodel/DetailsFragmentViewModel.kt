package com.example.newspaper.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModel
import com.example.newspaper.App
import com.example.newspaper.data.db_fav.ArticleFavorite
import com.example.newspaper.interactor.Interactor
import io.reactivex.rxjava3.core.Observable
import java.net.URL
import java.util.concurrent.Executors
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class DetailsFragmentViewModel : ViewModel() {


    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
    }

    fun putFav(articleFavorite: ArticleFavorite) {
        interactor.putNewsToFav(articleFavorite)
    }

    fun delFav(articleFavorite: ArticleFavorite) {
        interactor.deleteNewsFromFav(articleFavorite)
    }

    fun checkFav(search: String) : Observable<ArticleFavorite> = interactor.checkFav(search)

}