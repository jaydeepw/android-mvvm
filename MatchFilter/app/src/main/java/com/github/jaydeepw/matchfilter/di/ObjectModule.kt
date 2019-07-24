package com.github.jaydeepw.matchfilter.di

import androidx.lifecycle.MutableLiveData
import dagger.Module
import dagger.Provides

@Module
class ObjectModule {

    @Provides
    fun providesMutableStringLiveData(): MutableLiveData<String> {
        return MutableLiveData()
    }

    @Provides
    fun providesMutableBooleanLiveData(): MutableLiveData<Boolean> {
        return MutableLiveData()
    }
}