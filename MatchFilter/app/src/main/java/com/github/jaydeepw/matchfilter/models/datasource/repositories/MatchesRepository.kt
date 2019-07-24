package com.github.jaydeepw.matchfilter.models.datasource.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.jaydeepw.matchfilter.models.datasource.MatchesDataSource
import com.github.jaydeepw.matchfilter.models.datasource.remote.NetworkMatches
import com.github.jaydeepw.matchfilter.models.entities.MatchResponse
import retrofit2.Response
import javax.inject.Inject

class MatchesRepository @Inject constructor(
    private var networkSource: NetworkMatches
) : MatchesDataSource {

    lateinit var loading: MutableLiveData<Boolean>
    lateinit var errorHandler: MutableLiveData<String>

    fun init() {
        networkSource.loading = loading
        networkSource.errorHandler = errorHandler
    }

    override fun getMatches(map: HashMap<String, String>?): LiveData<Response<MatchResponse>> {
        return networkSource.getMatches(map)
    }
}