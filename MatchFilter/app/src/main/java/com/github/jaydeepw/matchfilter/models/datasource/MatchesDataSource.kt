package com.github.jaydeepw.matchfilter.models.datasource

import androidx.lifecycle.LiveData
import com.github.jaydeepw.matchfilter.models.entities.Match
import retrofit2.Response

interface MatchesDataSource {
    fun getMatches(): LiveData<Response<Match>>
}