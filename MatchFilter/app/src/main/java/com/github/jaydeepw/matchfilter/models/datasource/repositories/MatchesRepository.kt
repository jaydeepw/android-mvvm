package com.github.jaydeepw.matchfilter.models.datasource.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.jaydeepw.matchfilter.models.datasource.MatchesDataSource
import com.github.jaydeepw.matchfilter.models.datasource.remote.NetworkMatches
import com.github.jaydeepw.matchfilter.models.entities.MatchResponse
import retrofit2.Response

class MatchesRepository(private val app: Application) : MatchesDataSource {

    private var networkSource : NetworkMatches? = null

    val loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val errorHandler: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    init {
        networkSource = NetworkMatches(loading, errorHandler)
    }

    override fun getMatches(): LiveData<Response<MatchResponse>> {
        return networkSource?.getMatches()!!
    }
}