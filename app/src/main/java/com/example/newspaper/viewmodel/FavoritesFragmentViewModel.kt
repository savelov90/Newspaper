package com.example.newspaper.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newspaper.App
import com.example.newspaper.data.db_fav.ArticleFavorite
import com.example.newspaper.interactor.Interactor
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.Executors
import javax.inject.Inject


class FavoritesFragmentViewModel : ViewModel() {

    val newsListData: Observable<List<ArticleFavorite>>
    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
        getNews()
        newsListData = interactor.getNewsFromFav()
    }

    fun getNews() {
           interactor.getNewsFromFav()
    }
}