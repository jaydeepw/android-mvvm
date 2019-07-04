package com.github.jaydeepw.matchfilter.models.datasource.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.jaydeepw.matchfilter.models.datasource.Loadable
import com.github.jaydeepw.matchfilter.models.datasource.MatchesDataSource
import com.github.jaydeepw.matchfilter.models.entities.Match
import com.github.jaydeepw.matchfilter.utils.DebugLog
import retrofit2.Response

class NetworkMatches(private val loading: MutableLiveData<Boolean>) : MatchesDataSource, Loadable {

    private val list: MutableLiveData<Response<Match>> by lazy {
        MutableLiveData<Response<Match>>()
    }

    override fun getMatches(): LiveData<Response<Match>> {
        // todo: make an API call here
        DebugLog.d("")
        onLoadingInProgress()
        Thread.sleep(2000)
        onLoadingComplete()
        return list
    }

    override fun onLoadingInProgress() {
        DebugLog.d("")
        loading.value = true
    }

    override fun onLoadingComplete() {
        DebugLog.d("")
        loading.value = false
    }
}