package com.github.jaydeepw.matchfilter.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.github.jaydeepw.matchfilter.MyApp
import com.github.jaydeepw.matchfilter.models.datasource.repositories.MatchesRepository
import com.github.jaydeepw.matchfilter.models.entities.MatchResponse
import retrofit2.Response

class MainViewModel(app: Application) : AndroidViewModel(app) {

    val repository: MatchesRepository = MatchesRepository(app)

    init {
        (app as MyApp).appComponent.inject(repository)
    }

    fun getMatches(map: HashMap<String, String>?): LiveData<Response<MatchResponse>> {
        return repository?.getMatches(map)!!
    }

}