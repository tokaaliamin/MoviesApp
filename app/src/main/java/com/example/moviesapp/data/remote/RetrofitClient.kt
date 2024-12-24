package com.example.moviesapp.data.remote;

import com.example.moviesapp.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


object RetrofitClient {
    fun getRetrofitClient(): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(getOkHttpClient())
            .build()
    }

    private fun getOkHttpClient() =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(getInterceptor())
            .addInterceptor(getLoggingInterceptor())
            .build()


    private fun getInterceptor() =
        Interceptor { chain ->
            var request = chain.request()

            request = request.newBuilder().apply {
                addHeader(
                    "Authorization",
                    "Bearer ${BuildConfig.API_KEY}"
                )
            }.build()

            val proceed = chain.proceed(request)
            proceed
        }

    private fun getLoggingInterceptor() =
        HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
}
