package com.github.jaydeepw.matchfilter.models.datasource.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.github.jaydeepw.matchfilter.models.datasource.MatchesDataSource
import com.github.jaydeepw.matchfilter.models.datasource.remote.NetworkMatches
import com.github.jaydeepw.matchfilter.models.entities.Match
import retrofit2.Response

class MatchesRepository(private val app: Application) : MatchesDataSource {

    private var networkSource : NetworkMatches? = null

    init {
        networkSource = NetworkMatches()
    }

    override fun getMatches(): LiveData<Response<Match>> {
        return networkSource?.getMatches()!!
    }
}