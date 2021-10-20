package com.example.newspaper.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newspaper.App
import com.example.newspaper.data.db_fav.ArticleFavorite
import com.example.newspaper.interactor.Interactor
import java.util.concurrent.Executors
import javax.inject.Inject


class FavoritesFragmentViewModel : ViewModel() {

    val newsListLiveData = MutableLiveData<List<ArticleFavorite>>()
    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
        getNews()
    }

    fun getNews() {
        Executors.newSingleThreadExecutor().execute {
            newsListLiveData.postValue(interactor.getNewsFromFav())
        }
    }
}