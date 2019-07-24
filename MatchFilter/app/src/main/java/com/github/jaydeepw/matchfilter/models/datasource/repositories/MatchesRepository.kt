package com.github.jaydeepw.matchfilter.models.datasource.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.jaydeepw.matchfilter.models.datasource.MatchesDataSource
import com.github.jaydeepw.matchfilter.models.datasource.remote.NetworkMatches
import com.github.jaydeepw.matchfilter.models.entities.MatchResponse
import com.github.jaydeepw.matchfilter.utils.DebugLog
import retrofit2.Response
import javax.inject.Inject

class MatchesRepository(
    var loading: MutableLiveData<Boolean>,
    var errorHandler: MutableLiveData<String>
) : MatchesDataSource {

    @Inject
    lateinit var networkSource: NetworkMatches

    fun init() {
        networkSource.loading = loading
        networkSource.errorHandler = errorHandler
    }

    override fun getMatches(map: HashMap<String, String>?): LiveData<Response<MatchResponse>> {
        var list = networkSource.getMatches(map)
        DebugLog.i("--> list: $list")
        return list
    }
}