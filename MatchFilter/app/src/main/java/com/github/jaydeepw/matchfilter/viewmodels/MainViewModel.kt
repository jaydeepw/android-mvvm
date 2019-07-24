package com.github.jaydeepw.matchfilter.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.jaydeepw.matchfilter.models.datasource.repositories.MatchesRepository
import com.github.jaydeepw.matchfilter.models.entities.MatchResponse
import retrofit2.Response

class MainViewModel(var app: Application) : AndroidViewModel(app) {

    lateinit var repository: MatchesRepository
    lateinit var loading: MutableLiveData<Boolean>
    lateinit var errorHandler: MutableLiveData<String>

    fun init(repo: MatchesRepository) {
        this.repository = repo
        repository.loading = loading
        repository.errorHandler = errorHandler
        repository.init()
    }

    fun getMatches(map: HashMap<String, String>?): LiveData<Response<MatchResponse>> {
        return repository.getMatches(map)
    }

}