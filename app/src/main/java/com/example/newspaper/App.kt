package com.example.newspaper

import android.app.Application

import com.example.newspaper.di.AppComponent
import com.example.newspaper.di.DaggerAppComponent
import com.example.newspaper.di.modules.DatabaseModule
import com.example.newspaper.di.modules.DomainModule
import com.example.newspaper.di.modules.RemoteModule


class App : Application() {

    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        dagger = DaggerAppComponent.builder()
            .remoteModule(RemoteModule())
            .databaseModule(DatabaseModule(this))
            .domainModule(DomainModule())
            .build()
    }

    

    companion object {
        lateinit var instance : App
        private set
    }
}