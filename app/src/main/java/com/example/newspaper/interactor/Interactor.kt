package com.example.newspaper.interactor

import androidx.lifecycle.LiveData
import com.example.newspaper.data.MainRepository
import com.example.newspaper.data.NewsApi
import com.example.newspaper.data.db_fav.ArticleFavorite
import com.example.newspaper.data.db_first.entity.Article
import com.example.newspaper.data.db_first.entity.NewsData
import com.example.newspaper.viewmodel.HomeFragmentViewModel
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Interactor(private val repo: MainRepository, private val retrofitService: NewsApi) {
    //В конструктор мы будем передавать коллбэк из вью модели, чтобы реагировать на то, когда фильмы будут получены
    //и страницу, которую нужно загрузить (это для пагинации)
    fun getNewsFromApi() {
        retrofitService.getNews().enqueue(object : Callback<NewsData> {
            override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {
                //При успехе мы вызываем метод передаем onSuccess и в этот коллбэк список фильмов
               val list = response.body()?.articles
                //Кладем фильмы в бд
                //В случае успешного ответа кладем фильмы в БД и выключаем ProgressBar
                Completable.fromSingle<List<Article>> {
                    if (list != null) {
                        repo.putToDb(list)
                    }
                }
                        .subscribeOn(Schedulers.io())
                        .subscribe()
            }

            override fun onFailure(call: Call<NewsData>, t: Throwable) {
                //В случае провала вызываем другой метод коллбека

            }
        })
    }

    fun getNewsFromDB(): Observable<List<Article>> = repo.getAllFromDB()

    fun getNewsFromFav(): List<ArticleFavorite> = repo.getAllFromFav()
    fun putNewsToFav(articleFavorite: ArticleFavorite) = repo.putToFav(articleFavorite)
    fun deleteNewsFromFav(articleFavorite: ArticleFavorite) = repo.deleteFromFav(articleFavorite)
}