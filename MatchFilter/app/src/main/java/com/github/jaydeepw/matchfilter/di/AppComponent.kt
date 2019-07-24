package com.github.jaydeepw.matchfilter.di

import com.github.jaydeepw.matchfilter.models.datasource.repositories.MatchesRepository
import dagger.Component

@Component(modules = [NetworkModule::class, ObjectModule::class])
interface AppComponent {
    fun inject(repo: MatchesRepository)
}