package com.homework.weather.data.remote.mapper

import com.homework.weather.data.remote.dto.CityDto
import com.homework.weather.data.remote.dto.CloudsDto
import com.homework.weather.data.remote.dto.CoordDto
import com.homework.weather.data.remote.dto.MainDto
import com.homework.weather.data.remote.dto.SysDto
import com.homework.weather.data.remote.dto.WeatherDto
import com.homework.weather.data.remote.dto.WeatherInfoDto
import com.homework.weather.data.remote.dto.WeatherRemoteDto
import com.homework.weather.data.remote.dto.WindDto
import com.homework.weather.domain.model.City
import com.homework.weather.domain.model.Clouds
import com.homework.weather.domain.model.Coord
import com.homework.weather.domain.model.Main
import com.homework.weather.domain.model.Sys
import com.homework.weather.domain.model.Weather
import com.homework.weather.domain.model.WeatherInfo
import com.homework.weather.domain.model.WeatherModel
import com.homework.weather.domain.model.Wind

object WeatherMapper {

    fun mapperToWeatherRemote(weatherRemoteDto: WeatherRemoteDto): WeatherModel {
        return WeatherModel(
            cod = weatherRemoteDto.cod,
            message = weatherRemoteDto.message,
            cnt = weatherRemoteDto.cnt,
            city = mapperToCity(weatherRemoteDto.city),
            list = weatherRemoteDto.list.map { mapperToWeatherInfo(it) }
        )
    }

    fun mapperToCity(cityDto: CityDto): City{
        return City(
            id = cityDto.id,
            name = cityDto.name,
            coord = mapperToCoord(cityDto.coord),
            country = cityDto.country,
            population = cityDto.population,
            timezone = cityDto.timezone,
            sunrise = cityDto.sunrise,
            sunset = cityDto.sunset,
        )
    }

    fun mapperToCoord(coordDto: CoordDto): Coord{
        return Coord(
            lat = coordDto.lat,
            lon = coordDto.lon,
        )
    }

    fun mapperToWeatherInfo(weatherInfoDto: WeatherInfoDto): WeatherInfo{
        return WeatherInfo(
            dt = weatherInfoDto.dt,
            main = mapperToMain(weatherInfoDto.main),
            weather = weatherInfoDto.weather.map { mapperToWeather(it) },
            clouds = mapperToClouds(weatherInfoDto.clouds),
            wind = mapperToWind(weatherInfoDto.wind),
            visibility = weatherInfoDto.visibility,
            pop = weatherInfoDto.pop,
            sys = mapperToSys(weatherInfoDto.sys),
            dtTxt = weatherInfoDto.dtTxt,

        )
    }

    fun mapperToMain(mainDto: MainDto): Main {
        return Main(
            temp = mainDto.temp,
            feelsLike = mainDto.feelsLike,
            tempMin = mainDto.tempMin,
            tempMax = mainDto.tempMax,
            pressure = mainDto.pressure,
            seaLevel = mainDto.seaLevel,
            grndLevel = mainDto.grndLevel,
            humidity = mainDto.humidity,
            tempKf = mainDto.tempKf,
        )
    }

    fun mapperToWeather(weatherDto: WeatherDto): Weather{
        return Weather(
            id = weatherDto.id,
            main = weatherDto.main,
            description = weatherDto.description,
            icon = weatherDto.icon,
        )
    }

    fun mapperToClouds(cloudsDto: CloudsDto): Clouds{
        return Clouds(
            all = cloudsDto.all,
        )
    }

    fun mapperToWind(windDto: WindDto): Wind{
        return Wind(
            speed = windDto.speed,
            deg = windDto.deg,
            gust = windDto.gust,
        )
    }

    fun mapperToSys(sysDto: SysDto): Sys {
        return Sys(
            pod = sysDto.pod,
        )
    }
}