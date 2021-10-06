package com.example.newspaper

import android.app.Application
import com.example.newspaper.data.ApiConstants
import com.example.newspaper.data.MainRepository
import com.example.newspaper.data.NewsApi
import com.example.newspaper.interactor.Interactor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class App : Application() {

    lateinit var interactor: Interactor
    lateinit var repo: MainRepository
    lateinit var retrofitService: NewsApi

    override fun onCreate() {
        super.onCreate()

        instance = this
        repo = MainRepository()

        //Создаём кастомный клиент
        val okHttpClient = OkHttpClient.Builder()
                //Настраиваем таймауты для медленного интернета
                .callTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                //Добавляем логгер
                .addInterceptor(HttpLoggingInterceptor().apply {
                    if (BuildConfig.DEBUG) {
                        level = HttpLoggingInterceptor.Level.BASIC
                    }
                })
                .build()
        //Создаем Ретрофит
        val retrofit = Retrofit.Builder()
                //Указываем базовый URL из констант
                .baseUrl(ApiConstants.BASE_URL)
                //Добавляем конвертер
                .addConverterFactory(GsonConverterFactory.create())
                //Добавляем кастомный клиент
                .client(okHttpClient)
                .build()
//Создаем сам сервис с методами для запросов
        retrofitService = retrofit.create(NewsApi::class.java)
//Инициализируем интерактор
        interactor = Interactor(repo, retrofitService)

    }

    companion object {
        lateinit var instance : App
        private set
    }


}