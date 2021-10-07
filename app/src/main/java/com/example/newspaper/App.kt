package com.example.newspaper

import android.app.Application
import com.example.newspaper.data.ApiConstants
import com.example.newspaper.data.MainRepository
import com.example.newspaper.data.NewsApi
import com.example.newspaper.di.AppComponent
import com.example.newspaper.di.DaggerAppComponent
import com.example.newspaper.interactor.Interactor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class App : Application() {

    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        dagger = DaggerAppComponent.create()
    }

    

    companion object {
        lateinit var instance : App
        private set
    }
}