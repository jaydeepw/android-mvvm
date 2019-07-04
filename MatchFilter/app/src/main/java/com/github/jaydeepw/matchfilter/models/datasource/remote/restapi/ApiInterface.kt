package com.github.jaydeepw.matchfilter.models.datasource.remote.restapi

import com.github.jaydeepw.matchfilter.models.entities.MatchResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/matches")
    fun getMatches(@Query("hasPhoto") hasPhotos: Boolean?): Observable<Response<MatchResponse>>
}