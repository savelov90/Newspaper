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
    private lateinit var art: List<Article>

    fun getNewsFromApi() {

        retrofitService.getNews(getDefaultLangFromPreferences(), ApiConstants.API_KEY)
                .subscribeOn(Schedulers.io())
                .map {
                    it.articles
                }
                .subscribeBy(
                        onError = {

                        },
                        onNext = {
                            repo.deleteAll()
                            repo.putToDb(it)
                            art = it

                            getAllFav()
                                    .subscribeOn(Schedulers.io())
                                    .map {
                                        convertToArticle(it)
                                    }
                                    .subscribeBy(
                                            onError = {

                                            },
                                            onNext = {
                                                println(art.size)
                                                val a = it
                                                a.forEach {
                                                    val b = it
                                                    art.forEach {
                                                        if (it.title == b.title) {
                                                            repo.putToDb(b)
                                                        }
                                                    }
                                                }
                                            }
                                    )
                        }
                )
    }

    private fun convertToArticle(list: List<ArticleFavorite>?): List<Article> {
        val result = mutableListOf<Article>()
        list?.forEach {
            result.add(
                    Article(
                            id = it.id,
                            publishedAt = it.publishedAt,
                            description = it.description,
                            title = it.title,
                            urlToImage = it.urlToImage,
                            isInFavorites = it.isInFavorites,
                            author = it.author,
                            url = it.url
                    )
            )
        }
        return result
    }

    fun getNewsFromDB(): Observable<List<Article>> = repo.getAllFromDB()

    fun deleteAll() = repo.deleteAll()

    fun getNewsFromFav(): Observable<List<ArticleFavorite>> = repo.getAllFromFav()
    fun putNewsToFav(articleFavorite: ArticleFavorite) = repo.putToFav(articleFavorite)
    fun deleteNewsFromFav(articleFavorite: ArticleFavorite) = repo.deleteFromFav(articleFavorite)
    fun checkFav(search: String): Observable<ArticleFavorite> = repo.checkFav(search)

    //Метод для сохранения настроек
    fun saveDefaultLangToPreferences(language: String) {
        preferences.saveDefaultLang(language)
    }

    //Метод для получения настроек
    fun getDefaultLangFromPreferences() = preferences.getDefaultLang()

    fun getAllFav(): Observable<List<ArticleFavorite>> = repo.getAllFav()
}