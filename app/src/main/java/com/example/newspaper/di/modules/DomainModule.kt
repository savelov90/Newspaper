package com.example.newspaper.di.modules

import com.example.newspaper.data.MainRepository
import com.example.newspaper.data.NewsApi
import com.example.newspaper.interactor.Interactor
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Singleton
    fun povideInteractor(repository: MainRepository, newsApi: NewsApi) = Interactor(repo = repository, retrofitService = newsApi)
}