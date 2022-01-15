package com.hesham.moviedbtask.domain.gateways.remote

import com.google.gson.GsonBuilder
import com.hesham.moviedbtask.BuildConfig
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun getHeaderInterceptor(): Interceptor {
    return Interceptor { chain ->
        val request =
            chain.request().newBuilder()
                .header("Authorization", "Bearer ${BuildConfig.MY_MOVIEDB_TOKEN}")
                .build()
        chain.proceed(request)
    }
}

fun createOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .apply {
            addInterceptor(getHeaderInterceptor())
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            readTimeout(120, TimeUnit.SECONDS)
            connectTimeout(120, TimeUnit.SECONDS)
            writeTimeout(120, TimeUnit.SECONDS)
        }
        .build()
}

fun retrofitBuilder() = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .baseUrl(BuildConfig.BASE_API_URL)
    .client(createOkHttpClient())
    .build()
