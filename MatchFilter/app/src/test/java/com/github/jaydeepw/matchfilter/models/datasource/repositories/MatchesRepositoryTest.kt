package com.github.jaydeepw.matchfilter.models.datasource.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.github.jaydeepw.matchfilter.RxImmediateSchedulerRule
import com.github.jaydeepw.matchfilter.models.datasource.remote.NetworkMatches
import com.github.jaydeepw.matchfilter.models.datasource.remote.restapi.ApiInterface
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner



@RunWith(MockitoJUnitRunner::class)
class MatchesRepositoryTest {

    @get:Rule
    var rxSchedulersOverrideRule = RxImmediateSchedulerRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var api : ApiInterface

    @Mock
    lateinit var networkSource : NetworkMatches

    @Mock
    lateinit var loading : MutableLiveData<Boolean>

    @Mock
    lateinit var errorHandler : MutableLiveData<String>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getMatches() {
        val repository = MatchesRepository(networkSource)
        repository.getMatches(Mockito.any())
        Mockito.verify(networkSource).getMatches(Mockito.any())
    }

    @Test
    fun init() {
        val networkMatches = NetworkMatches(api)
        val repository = MatchesRepository(networkMatches)
        repository.loading = loading
        repository.errorHandler = errorHandler
        repository.init()

        Assert.assertNotNull(networkMatches.loading)
        Assert.assertNotNull(networkMatches.errorHandler)
    }

}