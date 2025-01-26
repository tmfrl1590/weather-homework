package com.homework.weather.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherRemoteDto(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val city: CityDto,
    val list: List<WeatherInfoDto>,
)

@Serializable
data class CityDto(
    val id: Int,
    val name: String,
    val coord: CoordDto,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Int,
    val sunset: Int
)

@Serializable
data class CoordDto(
    val lat: Double,
    val lon: Double
)

@Serializable
data class WeatherInfoDto(
    val dt: Int,
    val main: MainDto,
    val weather: List<WeatherDto>,
    val clouds: CloudsDto,
    val wind: WindDto,
    val visibility: Int? = 0,
    val pop: Double,
    val sys: SysDto,
    @SerialName("dt_txt")
    val dtTxt: String,
)

@Serializable
data class MainDto(
    val temp: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    @SerialName("temp_min")
    val tempMin: Double,
    @SerialName("temp_max")
    val tempMax: Double,
    val pressure: Int,
    @SerialName("sea_level")
    val seaLevel: Int,
    @SerialName("grnd_level")
    val grndLevel: Int,
    val humidity: Int,
    @SerialName("temp_kf")
    val tempKf: Double,
)

@Serializable
data class WeatherDto(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String,
)

@Serializable
data class CloudsDto(
    val all: Int,
)

@Serializable
data class WindDto(
    val speed: Double,
    val deg: Int,
    val gust: Double,
)

@Serializable
data class SysDto(
    val pod: String,
)