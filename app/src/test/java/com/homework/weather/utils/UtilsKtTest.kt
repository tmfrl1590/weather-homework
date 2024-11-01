package com.homework.weather.utils

import com.homework.weather.domain.model.Clouds
import com.homework.weather.domain.model.Main
import com.homework.weather.domain.model.Sys
import com.homework.weather.domain.model.WeatherInfo
import com.homework.weather.domain.model.Wind
import org.junit.Assert.*
import org.junit.Test

class UtilsKtTest {

    @Test
    fun 켈빈온도를_섭씨온도로_올바르게_변환한다() {
        // Given
        val kelvin = 288.15

        // When
        val result = convertToCelsiusFromKelvin(kelvin)

        // Then
        assertEquals("15.0°", result)
    }

    @Test
    fun 켈빈온도를_섭씨온도로_변환하면_소수첫째자리까지_나와야한다(){
        // Given
        val kelvin = 288.15

        // When
        val result = convertToCelsiusFromKelvin(kelvin)

        // Then
        assertNotEquals("15.00°", result)
        assertNotEquals("15°", result)
        assertEquals("15.0°", result)
    }

    @Test
    fun 켈빈온도를_섭씨온도로_변환하면_온도기호가_표시되어야한다(){

        // Given
        val kelvin = 288.15

        // When
        val result = convertToCelsiusFromKelvin(kelvin)

        // Then
        assertTrue(result.contains("°"))
        assertEquals("15.0°", result)
    }

    @Test
    fun 날씨아이콘을_받아서_이미지url로_변환한다() {
        // Given
        val weatherIcon = "10d"

        // When
        val result = getApiImage(weatherIcon)

        // Then
        assertEquals("https://openweathermap.org/img/wn/10d@2x.png", result)
        assertNotEquals("https://openweathermap.org/img/wn/1d@2x.png", result)
        assertNotEquals("https://openweathermap.org/img/wn/0d@2x.png", result)
        assertNotEquals("https://openweathermap.org/img/wn/@2x.png", result)
    }

    @Test
    fun 켈빈온도의_최소온도와_최고온도를_받아서_텍스트로_변환한다(){
        // Given
        val minTemp = 288.15
        val maxTemp = 290.15

        // When
        val result = convertTempToText(minTemp, maxTemp)

        // Then
        assertEquals("최소 15.0°  |  최대 17.0°", result)
        assertNotEquals("최소 15.0°  |  최대 17.0", result)
        assertNotEquals("최소 15.0  |  최대 17.0°", result)
        assertNotEquals("최소 15.0  |  최대 17.0", result)
    }

    @Test
    fun api를_통해서_날씨정보를_받으면_한글텍스트로_변환해준다(){
        // Given
        val weather1 = "Thunderstorm"
        val weather2 = "Drizzle"
        val weather3 = "Rain"
        val weather4 = "Snow"


        // When
        val result1 = convertToTextFromWeather(weather1)
        val result2 = convertToTextFromWeather(weather2)
        val result3 = convertToTextFromWeather(weather3)
        val result4 = convertToTextFromWeather(weather4)


        // Then
        assertEquals("천둥번개", result1)
        assertNotEquals("Thunderstorm", result1)
        assertEquals("이슬비", result2)
        assertNotEquals("Drizzle", result2)
        assertEquals("비", result3)
        assertNotEquals("Rain", result3)
        assertEquals("눈", result4)
        assertNotEquals("Snow", result4)
    }

    @Test
    fun 날씨정보를_받으면_관련_아이콘_텍스트로_변환한다(){
        // Given
        val weather1 = "Clear"
        val weather2 = "Clouds"
        val weather3 = "Drizzle"
        val weather4 = "Rain"
        val weather5 = "Thunderstorm"
        val weather6 = "Snow"

        // When
        val result1= convertToIconNumFromWeather(weather1)
        val result2= convertToIconNumFromWeather(weather2)
        val result3= convertToIconNumFromWeather(weather3)
        val result4 = convertToIconNumFromWeather(weather4)
        val result5 = convertToIconNumFromWeather(weather5)
        val result6 = convertToIconNumFromWeather(weather6)

        // Then
        assertEquals("01d", result1)
        assertEquals("02d", result2)
        assertEquals("09d", result3)
        assertEquals("10d", result4)
        assertEquals("11d", result5)
        assertEquals("13d", result6)
    }

    @Test
    fun 날짜데이터를_UI에_표시할_형식으로_변환한다(){
        // Given
        val date1 = "2024-10-02 15:00:00"
        val date2 = "2024-10-02 18:00:00"
        val date3 = "2024-10-02 21:00:00"

        // When
        val result1 = convertToKoreanTime(date1)
        val result2 = convertToKoreanTime(date2)
        val result3 = convertToKoreanTime(date3)

        // Then
        assertEquals("오후 3시", result1)
        assertEquals("오후 6시", result2)
        assertEquals("오후 9시", result3)
    }

    @Test
    fun 서버에서_받아온_데이터_중에서_현재_시간보다_이후의_데이터만_필터링(){
        // Given
        val list = listOf(
            weatherInfo1,
            weatherInfo2,
            weatherInfo3,
            weatherInfo4
        )

        // When
        val result = filterPastWeatherData(list)

        // Then
        assertEquals(1, result.size)
        assertEquals("2024-11-2 21:00:00", result[0].dtTxt)
    }
}


val weatherInfo1 = WeatherInfo(
    dt = 1633154400,
    main = Main(
        temp = 288.15,
        feelsLike = 288.15,
        tempMin = 288.15,
        tempMax = 288.15,
        pressure = 1013,
        seaLevel = 1013,
        grndLevel = 1013,
        humidity = 100,
        tempKf = 288.15
    ),
    weather = emptyList(),
    clouds = Clouds(all = 100),
    wind = Wind(
        speed = 1.03,
        deg = 0,
        gust = 1.03
    ),
    visibility = 10000,
    pop = 0.1,
    sys = Sys(pod = "d"),
    dtTxt = "2024-10-20 15:00:00"
)

val weatherInfo2 = WeatherInfo(
    dt = 1633154400,
    main = Main(
        temp = 288.15,
        feelsLike = 288.15,
        tempMin = 288.15,
        tempMax = 288.15,
        pressure = 1013,
        seaLevel = 1013,
        grndLevel = 1013,
        humidity = 100,
        tempKf = 288.15
    ),
    weather = emptyList(),
    clouds = Clouds(all = 100),
    wind = Wind(
        speed = 1.03,
        deg = 0,
        gust = 1.03
    ),
    visibility = 10000,
    pop = 0.1,
    sys = Sys(pod = "d"),
    dtTxt = "2024-10-20 18:00:00"
)

val weatherInfo3 = WeatherInfo(
    dt = 1633154400,
    main = Main(
        temp = 288.15,
        feelsLike = 288.15,
        tempMin = 288.15,
        tempMax = 288.15,
        pressure = 1013,
        seaLevel = 1013,
        grndLevel = 1013,
        humidity = 100,
        tempKf = 288.15
    ),
    weather = emptyList(),
    clouds = Clouds(all = 100),
    wind = Wind(
        speed = 1.03,
        deg = 0,
        gust = 1.03
    ),
    visibility = 10000,
    pop = 0.1,
    sys = Sys(pod = "d"),
    dtTxt = "2024-10-20 18:00:00"
)

val weatherInfo4 = WeatherInfo(
    dt = 1633154400,
    main = Main(
        temp = 288.15,
        feelsLike = 288.15,
        tempMin = 288.15,
        tempMax = 288.15,
        pressure = 1013,
        seaLevel = 1013,
        grndLevel = 1013,
        humidity = 100,
        tempKf = 288.15
    ),
    weather = emptyList(),
    clouds = Clouds(all = 100),
    wind = Wind(
        speed = 1.03,
        deg = 0,
        gust = 1.03
    ),
    visibility = 10000,
    pop = 0.1,
    sys = Sys(pod = "d"),
    dtTxt = "2024-11-2 21:00:00"
)