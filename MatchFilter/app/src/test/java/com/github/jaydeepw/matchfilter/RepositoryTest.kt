package com.github.jaydeepw.matchfilter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.github.jaydeepw.matchfilter.models.datasource.remote.NetworkMatches
import com.github.jaydeepw.matchfilter.models.datasource.remote.restapi.ApiInterface
import com.github.jaydeepw.matchfilter.models.entities.MatchResponse
import io.reactivex.Observable
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
    fun checkResponseOnApiCall() {
        val matchResponse = MatchResponse()

        val networkMatches = NetworkMatches(errorHandler, api)
        Mockito.`when`(api.getMatches(Mockito.any()))
            .thenReturn(Observable.just(Response.success(matchResponse)))

        networkMatches.getMatches(Mockito.any())
        Assert.assertEquals(matchResponse, networkMatches.list.value?.body())
    }
}