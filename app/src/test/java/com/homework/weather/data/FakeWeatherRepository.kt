package com.homework.weather.data

import com.homework.weather.domain.model.City
import com.homework.weather.domain.model.Coord
import com.homework.weather.domain.model.WeatherModel
import com.homework.weather.domain.repository.WeatherRepository

class FakeWeatherRepository: WeatherRepository {
    override suspend fun getWeather(lat: Double, lon: Double): WeatherModel {
        return WeatherModel(
            cod = "200",
            message = 0,
            cnt = 1,
            city = City(
                id = 0,
                name = "Test",
                coord = Coord(
                    lat = 0.0,
                    lon = 0.0
                ),
                country = "Test",
                population = 0,
                timezone = 0,
                sunrise = 0,
                sunset = 0
            ),
            list = emptyList()
        )
    }
}