package com.github.jaydeepw.matchfilter.models.datasource.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.jaydeepw.matchfilter.models.datasource.MatchesDataSource
import com.github.jaydeepw.matchfilter.models.datasource.remote.NetworkMatches
import com.github.jaydeepw.matchfilter.models.entities.MatchResponse
import retrofit2.Response
import javax.inject.Inject

class MatchesRepository(app: Application) : MatchesDataSource {
    // todo: dont pass in platform param here. Inject it.
    @Inject
    lateinit var networkSource: NetworkMatches

    val loading: MutableLiveData<Boolean> = MutableLiveData()

    val errorHandler: MutableLiveData<String> = MutableLiveData()

    init {
        // (app as MyApp).appComponent.inject(this)
    }

    override fun getMatches(map: HashMap<String, String>?): LiveData<Response<MatchResponse>> {
        return networkSource.getMatches(map)
    }
}