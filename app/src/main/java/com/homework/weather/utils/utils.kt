package com.homework.weather.utils

// 켈빈 -> 섭씨 변환, 소수점 첫째 자리
fun convertToCelsiusFromKelvin(kelvin: Double): String {
    val celsius = kelvin - 273.15
    return "%.1f°".format(celsius)
}

// weatherIcon 을 받아서 이미지 url로 변환
fun getApiImage(weatherIcon: String): String{
    // weatherIcon 에 n 이 있으면 d로 변경 (아이콘을 주간 아이콘으로 바꾸기 위해)
    val changedWeatherIcon = if(weatherIcon.contains("n")) weatherIcon.replace("n", "d") else weatherIcon
    return "https://openweathermap.org/img/wn/${changedWeatherIcon}@2x.png"
}

fun convertTempToText(minTemp: Double, maxTemp: Double): String{
    return "최소 ${convertToCelsiusFromKelvin(minTemp)}  |  최대 ${convertToCelsiusFromKelvin(maxTemp)}"
}

fun convertToMinTempAndMaxTemp(minTemp: Double, maxTemp: Double): String{
    return "최소 ${convertToCelsiusFromKelvin(minTemp)}  최대 ${convertToCelsiusFromKelvin(maxTemp)}"
}

fun convertToTextFromWeather(weather: String): String{
    when(weather){
        "Thunderstorm" -> return WeatherEnum.THUNDERSTORM.weather
        "Drizzle" -> return WeatherEnum.DRIZZLE.weather
        "Rain" -> return WeatherEnum.RAIN.weather
        "Snow" -> return WeatherEnum.SNOW.weather
        "Atmosphere" -> return WeatherEnum.ATMOSPHERE.weather
        "Clear" -> return WeatherEnum.CLEAR.weather
        "Clouds" -> return WeatherEnum.CLOUDS.weather
        "Mist" -> return WeatherEnum.MIST.weather
        "Smoke" -> return WeatherEnum.SMOKE.weather
        "Haze" -> return WeatherEnum.HAZE.weather
        "Dust" -> return WeatherEnum.DUST.weather
        "Fog" -> return WeatherEnum.FOG.weather
        "Sand" -> return WeatherEnum.SAND.weather
        "Ash" -> return WeatherEnum.ASH.weather
        "Squall" -> return WeatherEnum.SQUALL.weather
        "Tornado" -> return WeatherEnum.TORNADO.weather
        else -> return WeatherEnum.UNDEFINED.weather
    }
}

enum class WeatherEnum(val weather: String){
    THUNDERSTORM("천둥번개"),
    DRIZZLE("이슬비"),
    RAIN("비"),
    SNOW("눈"),
    ATMOSPHERE("대기"),
    CLEAR("맑음"),
    CLOUDS("구름"),
    MIST("안개"),
    SMOKE("연기"),
    HAZE("연무"),
    DUST("먼지"),
    FOG("안개"),
    SAND("모래"),
    ASH("재"),
    SQUALL("돌풍"),
    TORNADO("토네이도"),
    UNDEFINED("알 수 없음")
}


fun convertToIconNumFromWeather(weather: String): String{
    return when(weather){
        "Clear" -> "01d"
        "Clouds" -> "02d"
        "Drizzle" -> "09d"
        "Rain" -> "10d"
        "Thunderstorm" -> "11d"
        "Snow" -> "13d"
        else -> "50d"
    }
}