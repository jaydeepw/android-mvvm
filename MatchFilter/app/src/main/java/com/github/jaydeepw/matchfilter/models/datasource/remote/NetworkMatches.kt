package com.github.jaydeepw.matchfilter.models.datasource.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.jaydeepw.matchfilter.models.datasource.Loadable
import com.github.jaydeepw.matchfilter.models.datasource.MatchesDataSource
import com.github.jaydeepw.matchfilter.models.datasource.remote.restapi.Api
import com.github.jaydeepw.matchfilter.models.entities.MatchResponse
import com.github.jaydeepw.matchfilter.utils.DebugLog
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class NetworkMatches(private val loading: MutableLiveData<Boolean>) : MatchesDataSource, Loadable {

    private lateinit var subscription: Disposable

    private val list: MutableLiveData<Response<MatchResponse>> by lazy {
        MutableLiveData<Response<MatchResponse>>()
    }

    override fun getMatches(): LiveData<Response<MatchResponse>> {
        val observable = Api.getApi().getMatches(false)
        subscription = observable.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe { onLoadingInProgress() }
            .doOnTerminate { onLoadingComplete() }
            .subscribe({ handleResponse(it) }, { handleError(it) })
        return list
    }

    override fun onLoadingInProgress() {
        loading.value = true
    }

    override fun onLoadingComplete() {
        loading.value = false
    }

    private fun handleError(it: Throwable) {
        DebugLog.e("error: " + it.localizedMessage)
    }

    private fun handleResponse(it: Response<MatchResponse>) {
        list.value = it
    }
}