package com.github.jaydeepw.matchfilter.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.jaydeepw.matchfilter.MyApp
import com.github.jaydeepw.matchfilter.models.datasource.repositories.MatchesRepository
import com.github.jaydeepw.matchfilter.models.entities.MatchResponse
import com.github.jaydeepw.matchfilter.utils.DebugLog
import retrofit2.Response

class MainViewModel(var app: Application) : AndroidViewModel(app) {

    var repository: MatchesRepository? = null
    lateinit var loading: MutableLiveData<Boolean>

    fun init() {
        repository = MatchesRepository(loading)
        (app as MyApp).appComponent.inject(repository!!)
        repository?.init()
    }

    fun getMatches(map: HashMap<String, String>?): LiveData<Response<MatchResponse>> {
        DebugLog.i("--> repository: $repository")
        val list = repository?.getMatches(map)
        DebugLog.i("--> list: $list")
        return list!!
    }

}