package com.github.jaydeepw.matchfilter

import android.app.Application
import com.github.jaydeepw.matchfilter.di.AppComponent
import com.github.jaydeepw.matchfilter.di.DaggerAppComponent
import com.github.jaydeepw.matchfilter.di.NetworkModule

class MyApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .build()
    }
}