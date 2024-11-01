package com.homework.weather.domain.use_cases

import com.homework.weather.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherInfoUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, lon: Double) = weatherRepository.getWeather(lat = lat, lon = lon)
}