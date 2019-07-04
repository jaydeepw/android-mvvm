package com.github.jaydeepw.matchfilter.models.datasource.remote.restapi

import com.github.jaydeepw.matchfilter.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Api {

    companion object {

        private val logLevel = HttpLoggingInterceptor.Level.BODY

        /**
         *
         * @return
         */
        fun getApi(): ApiInterface {
            val retrofit = getRetrofit()
            return retrofit.create(ApiInterface::class.java)
        }

        /**
         * Get [Retrofit] instance.
         *
         * @return
         */
        fun getRetrofit(): Retrofit {
            return getRetrofitForUrl(BuildConfig.SERVER_URL)
        }

        /**
         * Get [Retrofit] instance.
         *
         * @return
         */
        fun getRetrofitForUrl(baseUrl: String): Retrofit {
            val client = getHttpClient()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        private fun getHttpClient(): OkHttpClient {
            val interceptorLogging = HttpLoggingInterceptor()
            interceptorLogging.level = logLevel

            return OkHttpClient.Builder()
                .addInterceptor(interceptorLogging)
                .connectTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES)
                .build()
        }

    }
}