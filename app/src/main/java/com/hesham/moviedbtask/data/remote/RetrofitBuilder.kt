package com.hesham.moviedbtask.data.remote

import com.google.gson.GsonBuilder
import com.hesham.moviedbtask.data.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJjZGFkYjViOTcwODQ1MTJmM2Q0ODc0OWU2M2M2ZWU3MyIsInN1YiI6IjU3YWRhOGMxYzNhMzY4MjA3NTAwNTI1NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.GbHnOG69HdBPabpON-okVdYcKT8LPHRLWDZaLEVcdpw"
fun getHeaderInterceptor(): Interceptor {
    return Interceptor { chain ->
        val request =
            chain.request().newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        chain.proceed(request)
    }
}

private fun createOkHttpClient(): OkHttpClient {
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
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(Constants.BASE_URL)
    .client(createOkHttpClient())
    .build()
