package com.example.newspaper.di

import com.example.newspaper.di.modules.DatabaseModule
import com.example.newspaper.di.modules.DomainModule
import com.example.newspaper.di.modules.RemoteModule
import com.example.newspaper.viewmodel.HomeFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    //Внедряем все модули, нужные для этого компонента
    modules = [
        RemoteModule::class,
        DatabaseModule::class,
        DomainModule::class
    ]
)

interface AppComponent {
    //метод для того, чтобы появилась внедрять зависимости в HomeFragmentViewModel
    fun inject(homeFragmentViewModel: HomeFragmentViewModel)
}