package com.example.newspaper.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newspaper.App
import com.example.newspaper.data.db_first.entity.Article
import com.example.newspaper.interactor.Interactor
import java.util.concurrent.Executors
import javax.inject.Inject


class HomeFragmentViewModel : ViewModel() {

    val newsListLiveData = MutableLiveData<List<Article>>()
    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
        getNews()
    }

    fun getNews() {
        interactor.getNewsFromApi(object : ApiCallback {
            override fun onSuccess(article: List<Article>) {
                newsListLiveData.postValue(article)
            }

            override fun onFailure() {
                Executors.newSingleThreadExecutor().execute {
                    println("Hello Home View Model")
                    newsListLiveData.postValue(interactor.getNewsFromDB())
                }
            }
        })
    }



    interface ApiCallback {
        fun onSuccess(article: List<Article>)
        fun onFailure()
        }
}
