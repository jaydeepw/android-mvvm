package com.github.jaydeepw.matchfilter.models.datasource.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.jaydeepw.matchfilter.models.datasource.MatchesDataSource
import com.github.jaydeepw.matchfilter.models.datasource.remote.NetworkMatches
import com.github.jaydeepw.matchfilter.models.entities.Match
import com.github.jaydeepw.matchfilter.utils.DebugLog
import retrofit2.Response

class MatchesRepository(private val app: Application) : MatchesDataSource {

    private var networkSource : NetworkMatches? = null

    val loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    init {
        networkSource = NetworkMatches(loading)
    }

    override fun getMatches(): LiveData<Response<Match>> {
        DebugLog.d("")
        return networkSource?.getMatches()!!
    }
}