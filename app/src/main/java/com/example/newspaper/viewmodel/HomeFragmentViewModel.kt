package com.example.newspaper.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newspaper.App
import com.example.newspaper.data.Article
import com.example.newspaper.interactor.Interactor
import javax.inject.Inject


class HomeFragmentViewModel : ViewModel() {

    val newsListLiveData = MutableLiveData<List<Article>>()
    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)

        interactor.getNewsFromApi(object : ApiCallback {
            override fun onSuccess(article: List<Article>) {
                newsListLiveData.postValue(article)
            }

            override fun onFailure() {
            }
        })
    }

    interface ApiCallback {
        fun onSuccess(article: List<Article>)
        fun onFailure()
        }
}
