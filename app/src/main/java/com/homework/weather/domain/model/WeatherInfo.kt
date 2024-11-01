package com.homework.weather.domain.model

data class WeatherInfo(
    val dt: Int,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val sys: Sys,
    val dtTxt: String,
)

data class Main(
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Int,
    val seaLevel: Int,
    val grndLevel: Int,
    val humidity: Int,
    val tempKf: Double,
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String,
)

data class Clouds(
    val all: Int,
)

data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double,
)

data class Sys(
    val pod: String,
)