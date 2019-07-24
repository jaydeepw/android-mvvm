package com.github.jaydeepw.matchfilter.viewmodels

import android.app.Application
import com.github.jaydeepw.matchfilter.models.datasource.repositories.MatchesRepository

class ViewModelFactory {

    companion object {
        fun create(
            app: Application,
            repository: MatchesRepository): MainViewModel {

            try {
                return MainViewModel::class.java.getConstructor(
                    Application::class.java,
                    MatchesRepository::class.java
                ).newInstance(app, repository)
            } catch (e: InstantiationException) {
                throw RuntimeException("Cannot create an instance of class", e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException("Cannot create an instance of class", e)
            }

        }

    }


}