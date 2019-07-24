package com.github.jaydeepw.matchfilter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.github.jaydeepw.matchfilter.models.datasource.remote.NetworkMatches
import com.github.jaydeepw.matchfilter.models.datasource.remote.restapi.ApiInterface
import com.github.jaydeepw.matchfilter.models.entities.MatchResponse
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class RepositoryTest {

    @get:Rule
    var rxSchedulersOverrideRule = RxImmediateSchedulerRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var api : ApiInterface

    @Mock
    lateinit var loading : MutableLiveData<Boolean>

    @Mock
    lateinit var errorHandler : MutableLiveData<String>

    @Before
    fun setupTasksViewModel() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun checkResponseSuccess() {
        val matchResponse = MatchResponse()

        val networkMatches = NetworkMatches(api)
        networkMatches.loading = loading
        networkMatches.errorHandler = errorHandler

        Mockito.`when`(api.getMatches(Mockito.any()))
            .thenReturn(Observable.just(Response.success(matchResponse)))

        networkMatches.getMatches(Mockito.any())
        Assert.assertEquals(matchResponse, networkMatches.list.value?.body())
    }

    @Test
    fun checkResponseError500() {
        val networkMatches = NetworkMatches(api)
        networkMatches.loading = loading
        networkMatches.errorHandler = errorHandler
        val responseBody = ResponseBody.create(MediaType.parse("application/json" + "; charset=utf-8"), "")

        Mockito.`when`(api.getMatches(Mockito.any()))
            .thenReturn(Observable.just(Response.error(500, responseBody)))

        networkMatches.getMatches(Mockito.any())
        Assert.assertNull(networkMatches.list.value?.body())
        Assert.assertNotNull(networkMatches.list.value?.errorBody())
        Assert.assertEquals(500, networkMatches.list.value?.code())
    }

    @Test
    fun checkResponseError400() {
        val networkMatches = NetworkMatches(api)
        networkMatches.loading = loading
        networkMatches.errorHandler = errorHandler
        val responseBody = ResponseBody.create(MediaType.parse("application/json" + "; charset=utf-8"),
            "")

        Mockito.`when`(api.getMatches(Mockito.any()))
            .thenReturn(Observable.just(Response.error(400, responseBody)))

        networkMatches.getMatches(Mockito.any())
        Assert.assertNull(networkMatches.list.value?.body())
        Assert.assertNotNull(networkMatches.list.value?.errorBody())
        Assert.assertEquals(400, networkMatches.list.value?.code())
    }

}