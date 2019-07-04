package com.github.jaydeepw.matchfilter.models.datasource

import androidx.lifecycle.LiveData
import com.github.jaydeepw.matchfilter.models.entities.MatchResponse
import retrofit2.Response

interface MatchesDataSource {
    fun getMatches(map: HashMap<String, String>?): LiveData<Response<MatchResponse>>
}