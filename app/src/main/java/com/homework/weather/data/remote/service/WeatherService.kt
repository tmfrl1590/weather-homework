package com.homework.weather.data.remote.service

import com.homework.weather.BuildConfig
import com.homework.weather.data.remote.dto.WeatherRemoteDto
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherService {
    companion object {
        private const val WEATHER_END_POINT = "data/2.5/forecast"
    }

    @GET(WEATHER_END_POINT)
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appid: String = BuildConfig.API_KEY
    ): WeatherRemoteDto
}