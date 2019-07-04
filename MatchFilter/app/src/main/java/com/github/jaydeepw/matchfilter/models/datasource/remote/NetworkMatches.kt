package com.github.jaydeepw.matchfilter.models.datasource.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.jaydeepw.matchfilter.models.datasource.MatchesDataSource
import com.github.jaydeepw.matchfilter.models.entities.Match
import retrofit2.Response

class NetworkMatches() : MatchesDataSource {

    private val list: MutableLiveData<Response<Match>> by lazy {
        MutableLiveData<Response<Match>>()
    }

    override fun getMatches(): LiveData<Response<Match>> {
        // todo: make an API call here using network lib
        return list
    }
}