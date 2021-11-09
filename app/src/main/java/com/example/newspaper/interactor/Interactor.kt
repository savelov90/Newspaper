package com.example.newspaper.interactor


import com.example.newspaper.data.ApiConstants
import com.example.newspaper.data.MainRepository
import com.example.newspaper.data.NewsApi
import com.example.newspaper.data.Preference.PreferenceProvider
import com.example.newspaper.data.db_fav.ArticleFavorite
import com.example.newspaper.data.db_first.entity.Article
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.kotlin.subscribeBy


class Interactor(private val repo: MainRepository, private val retrofitService: NewsApi, private val preferences: PreferenceProvider) {
    //В конструктор мы будем передавать коллбэк из вью модели, чтобы реагировать на то, когда фильмы будут получены
    //и страницу, которую нужно загрузить (это для пагинации)
    fun getNewsFromApi() {
        retrofitService.getNews(getDefaultLangFromPreferences(), ApiConstants.API_KEY)
                .subscribeOn(Schedulers.io())
                .map {
                    it.articles }
                .subscribeBy(
                        onError = {

                        },
                        onNext = {
                            repo.putToDb(it)
                        }
                )

    }

    fun getNewsFromDB(): Observable<List<Article>> = repo.getAllFromDB()

    fun getNewsFromFav(): Observable<List<ArticleFavorite>> = repo.getAllFromFav()
    fun putNewsToFav(articleFavorite: ArticleFavorite) = repo.putToFav(articleFavorite)
    fun deleteNewsFromFav(articleFavorite: ArticleFavorite) = repo.deleteFromFav(articleFavorite)
    fun checkFav(search: String) : Observable<ArticleFavorite> = repo.checkFav(search)

    //Метод для сохранения настроек
    fun saveDefaultLangToPreferences(language: String) {
        preferences.saveDefaultLang(language)
    }
    //Метод для получения настроек
    fun getDefaultLangFromPreferences() = preferences.getDefaultLang()
}