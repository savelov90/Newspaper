package com.example.newspaper.viewmodel


import androidx.lifecycle.ViewModel
import com.example.newspaper.App
import com.example.newspaper.data.db_first.entity.Article
import com.example.newspaper.interactor.Interactor
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject


class HomeFragmentViewModel : ViewModel() {

    val newsListData: Observable<List<Article>>
    //Инициализируем интерактор
    @Inject
    lateinit var interactor: Interactor

    init {
        App.instance.dagger.inject(this)
        getNews()
        newsListData = interactor.getNewsFromDB()
    }

    fun getNews() {
        interactor.getNewsFromApi()

    }



    interface ApiCallback {
        fun onFailure()
        fun onSuccess()
    }
}
