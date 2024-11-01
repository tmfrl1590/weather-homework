package com.homework.weather.domain.model

data class CityModel(
    val id: Int,
    val name: String,
    val country: String,
    val coord: CoordModel
)

data class CoordModel(
    val lon: Double,
    val lat: Double
)
