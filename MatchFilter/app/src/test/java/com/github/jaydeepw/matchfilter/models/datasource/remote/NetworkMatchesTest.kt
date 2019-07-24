package com.github.jaydeepw.matchfilter.models.datasource.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.github.jaydeepw.matchfilter.RxImmediateSchedulerRule
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
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

class NetworkMatchesTest {

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
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this)

        // Get a reference to the class under test
        // networkMatches = new NetworkMatches(loading, error);
    }

    @Test
    fun getMatches() {
        // given
        val observer = Mockito.mock(Observer::class.java) as Observer<Response<MatchResponse>>
        val networkMatches = NetworkMatches(api)
        networkMatches.loading = loading
        networkMatches.errorHandler = errorHandler

        // when
        val matchResponse = MatchResponse()
        val response = Response.success(matchResponse)
        `when`(api.getMatches(Mockito.any()))
            .thenReturn(Observable.just(response))
        val liveData = networkMatches.getMatches(Mockito.any())
        liveData.observeForever(observer)

        // then
        Mockito.verify(observer).onChanged(response)
    }

    @Test
    fun checkResponseSuccess() {
        val matchResponse = MatchResponse()

        val networkMatches = NetworkMatches(api)
        networkMatches.loading = loading
        networkMatches.errorHandler = errorHandler

        `when`(api.getMatches(Mockito.any()))
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
        val responseBody = ResponseBody.create(
            MediaType.parse("application/json" + "; charset=utf-8"),
            "")

        Mockito.`when`(api.getMatches(Mockito.any()))
            .thenReturn(Observable.just(Response.error(400, responseBody)))

        networkMatches.getMatches(Mockito.any())
        Assert.assertNull(networkMatches.list.value?.body())
        Assert.assertNotNull(networkMatches.list.value?.errorBody())
        Assert.assertEquals(400, networkMatches.list.value?.code())
    }

    @Test
    fun checkLoadingBehavior() {
        val networkMatches = NetworkMatches(api)
        networkMatches.loading = loading
        networkMatches.errorHandler = errorHandler

        Mockito.`when`(api.getMatches(Mockito.any()))
            .thenReturn(Observable.just(Response.success(MatchResponse())))

        networkMatches.getMatches(Mockito.any())

        val inOrder = Mockito.inOrder(loading)
        inOrder.verify(loading).value = true
        inOrder.verify(loading).value = false
    }

    @Test
    fun checkErrorBehavior() {
        val networkMatches = NetworkMatches(api)
        networkMatches.loading = loading
        networkMatches.errorHandler = errorHandler

        val exception = Exception("Failure")
        Mockito.`when`(api.getMatches(Mockito.any()))
            .thenReturn(Observable.error<Response<MatchResponse>>(exception))

        networkMatches.getMatches(Mockito.any())

        Mockito.verify(errorHandler).value = "Failure"
    }
}