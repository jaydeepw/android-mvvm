package com.github.jaydeepw.matchfilter.models.datasource.remote.restapi

import com.github.jaydeepw.matchfilter.models.entities.MatchResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiInterface {
    @GET("/matches")
    fun getMatches(@QueryMap params: HashMap<String, String>?): Observable<Response<MatchResponse>>
}