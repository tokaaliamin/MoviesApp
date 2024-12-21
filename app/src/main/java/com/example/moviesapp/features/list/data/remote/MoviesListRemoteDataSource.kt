package com.example.moviesapp.features.list.data.remote

import com.example.moviesapp.features.list.data.remote.models.MoviesListResponse
import com.example.moviesapp.features.list.data.remote.services.MoviesListService
import com.example.moviesapp.features.common.data.remote.RetrofitClient

class MoviesListRemoteDataSource() {
    private val moviesListService by lazy { RetrofitClient.getRetrofitClient().create(MoviesListService::class.java) }

    /*    suspend fun fetchDailyWeather(weatherApi: WeatherApi,lat:Double,lng:Double): Result<WeatherData> {
        return kotlin.runCatching {
            val response = weatherApi.fetchDailyWeather(lat, lng)
            if (response.isSuccessful)
                 response.body()!!
            else
                throw RuntimeException(response.errorBody().toString())
        }
    }

    interface WeatherApi{
        @GET("onecall")
        suspend fun fetchDailyWeather(
            @Query("lat") lat: Double,
            @Query("lon") lon: Double,
            @Query("appid") apiKey: String = "4371b3f05365b382eebfa4e8f78f9336"
        ): Response<WeatherData>
    }*/

    suspend fun discoverMovies(
        pageNumber: Int
    ): MoviesListResponse {
        return moviesListService.discoverMovies(pageNumber)
    }

}