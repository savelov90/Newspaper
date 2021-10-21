package com.example.newspaper.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newspaper.App
import com.example.newspaper.data.db_first.entity.Article
import com.example.newspaper.interactor.Interactor
import java.util.concurrent.Executors
import javax.inject.Inject


class HomeFragmentViewModel : ViewModel() {

    val newsListLiveData: LiveData<List<Article>>
    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
        newsListLiveData = interactor.getNewsFromDB()
        getNews()
    }

    fun getNews() {
        interactor.getNewsFromApi(object : ApiCallback {
            override fun onSuccess() {

            }

            override fun onFailure() {

            }
        })
    }



    interface ApiCallback {
        fun onFailure()
        fun onSuccess()
    }
}
