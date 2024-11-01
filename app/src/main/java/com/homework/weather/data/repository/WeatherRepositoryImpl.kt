package com.homework.weather.data.repository

import com.homework.weather.data.remote.mapper.WeatherMapper
import com.homework.weather.data.remote.service.WeatherService
import com.homework.weather.domain.model.WeatherModel
import com.homework.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService,
): WeatherRepository {
    override suspend fun getWeather(lat: Double, lon: Double): WeatherModel {
        return WeatherMapper.mapperToWeatherRemote(weatherService.getWeather(lat, lon))
    }
}