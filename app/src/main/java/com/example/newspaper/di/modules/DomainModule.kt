package com.example.newspaper.di.modules

import android.content.Context
import com.example.newspaper.data.MainRepository
import com.example.newspaper.data.NewsApi
import com.example.newspaper.data.Preference.PreferenceProvider
import com.example.newspaper.interactor.Interactor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule(val context: Context)  {

    @Provides
    @Singleton
    fun provideContext() = context

    @Singleton
    @Provides
    //Создаем экземпляр SharedPreferences
    fun providePreferences(context: Context) = PreferenceProvider(context)

    @Provides
    @Singleton
    fun provideInteractor(repository: MainRepository, newsApi: NewsApi, preferenceProvider: PreferenceProvider) =
                Interactor(repo = repository, retrofitService = newsApi, preferences = preferenceProvider)
}