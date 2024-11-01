package com.homework.weather.domain.model

data class WeatherModel(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val city: City,
    val list: List<WeatherInfo>,
)
