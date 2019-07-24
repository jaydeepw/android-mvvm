package com.github.jaydeepw.matchfilter.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.github.jaydeepw.matchfilter.models.datasource.repositories.MatchesRepository
import com.github.jaydeepw.matchfilter.models.entities.MatchResponse
import retrofit2.Response

class MainViewModel(var app: Application,
                    var repository: MatchesRepository) : AndroidViewModel(app) {

    fun getMatches(map: HashMap<String, String>?): LiveData<Response<MatchResponse>> {
        return repository.getMatches(map)
    }

}