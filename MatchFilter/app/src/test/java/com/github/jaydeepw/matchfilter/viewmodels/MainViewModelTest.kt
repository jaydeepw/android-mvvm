package com.github.jaydeepw.matchfilter.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.github.jaydeepw.matchfilter.MyApp
import com.github.jaydeepw.matchfilter.RxImmediateSchedulerRule
import com.github.jaydeepw.matchfilter.models.datasource.repositories.MatchesRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainViewModelTest {

    @get:Rule
    var rxSchedulersOverrideRule = RxImmediateSchedulerRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var app : MyApp

    @Mock
    lateinit var repository : MatchesRepository

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
        val viewModel = MainViewModel(app)
        viewModel.loading = loading
        viewModel.errorHandler = errorHandler
        viewModel.init(repository)

        viewModel.getMatches(Mockito.any())

        Mockito.verify(repository).getMatches(Mockito.any())
    }

    @Test
    fun checkInit() {
        val viewModel = MainViewModel(app)
        viewModel.loading = loading
        viewModel.errorHandler = errorHandler

        viewModel.init(repository)

        Mockito.verify(repository).init()
    }
}