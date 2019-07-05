package com.github.jaydeepw.matchfilter;

import android.content.Context;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import com.github.jaydeepw.matchfilter.models.datasource.remote.NetworkMatches;
import com.github.jaydeepw.matchfilter.models.datasource.repositories.MatchesRepository;
import com.github.jaydeepw.matchfilter.models.entities.MatchResponse;
import kotlin.jvm.JvmField;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import retrofit2.Response;

import java.util.HashMap;

public class NetworkMatchesTest {

    @ClassRule
    @JvmField
    public static RxImmediateSchedulerRule schedulers = new RxImmediateSchedulerRule();

    @ClassRule
    @JvmField
    public static InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    private MatchesRepository matchesRepository;

    @Mock
    private Context mContext;

    @Mock
    MutableLiveData<Boolean> loading;

    @Mock
    MutableLiveData<String> error;

    @Mock
    Response<MatchResponse> response;

    NetworkMatches networkMatches;

    @Before
    public void setupTasksViewModel() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);

        // Get a reference to the class under test
        networkMatches = new NetworkMatches(loading, error);
    }

    @Test
    public void checkResponseOnApiCall() {
        // given
        MutableLiveData<Response<MatchResponse>> liveData = new MutableLiveData<>();
        Observer<Response<MatchResponse>> observer = (Observer<Response<MatchResponse>>) Mockito.mock(Observer.class);
        HashMap<String, String> map = new HashMap<>();
        networkMatches.getMatches(map).observeForever(observer);

        // when
        liveData.setValue(response);

        // then
        Mockito.verify(observer).onChanged(Mockito.any(Response.class));
    }
}
