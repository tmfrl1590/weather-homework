package com.homework.weather.domain.repository

import com.homework.weather.domain.model.WeatherModel

interface WeatherRepository {

    suspend fun getWeather(lat: Double, lon: Double): WeatherModel
}