package com.github.jaydeepw.matchfilter.models.datasource

interface Loadable {

    fun onLoadingInProgress()
    fun onLoadingComplete()
}