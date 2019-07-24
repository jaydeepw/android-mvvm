package com.github.jaydeepw.matchfilter.di

import com.github.jaydeepw.matchfilter.models.datasource.remote.restapi.Api
import com.github.jaydeepw.matchfilter.models.datasource.remote.restapi.ApiInterface
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {

    @Provides
    fun providesApi(): ApiInterface {
        return Api.getApi()
    }
}