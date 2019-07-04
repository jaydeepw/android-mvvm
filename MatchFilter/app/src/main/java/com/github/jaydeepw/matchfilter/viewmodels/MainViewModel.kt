package com.github.jaydeepw.matchfilter.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.github.jaydeepw.matchfilter.models.datasource.repositories.MatchesRepository
import com.github.jaydeepw.matchfilter.models.entities.Match
import retrofit2.Response

class MainViewModel(app: Application) : AndroidViewModel(app) {

    var repository: MatchesRepository? = null

    init {
        repository = MatchesRepository(app)
    }

    fun getNotifications(): LiveData<Response<Match>> {
        return repository?.getMatches()!!
    }

}