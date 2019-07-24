package com.github.jaydeepw.matchfilter.models.datasource.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.jaydeepw.matchfilter.models.datasource.MatchesDataSource
import com.github.jaydeepw.matchfilter.models.datasource.remote.NetworkMatches
import com.github.jaydeepw.matchfilter.models.entities.MatchResponse
import com.github.jaydeepw.matchfilter.utils.DebugLog
import retrofit2.Response
import javax.inject.Inject

class MatchesRepository(var loading: MutableLiveData<Boolean>) : MatchesDataSource {

    @Inject
    lateinit var networkSource: NetworkMatches

    val errorHandler: MutableLiveData<String> = MutableLiveData()

    fun init() {
        networkSource.loading = loading
    }

    override fun getMatches(map: HashMap<String, String>?): LiveData<Response<MatchResponse>> {
        var list = networkSource.getMatches(map)
        DebugLog.i("--> list: $list")
        return list
    }
}