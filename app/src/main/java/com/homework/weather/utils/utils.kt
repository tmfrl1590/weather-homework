package com.homework.weather.utils

import com.homework.weather.domain.model.WeatherInfo
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

// 서버에서 받아온 데이터 중 현재 시간 이후의 시간대의 데이터만 보여줌 (이전 시간은 필터링)
fun filterPastWeatherData(list: List<WeatherInfo>): List<WeatherInfo> {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val currentDate = Date()

    // 현재 시간을 기준으로 이후 데이터만
    return list.filter { data ->
        val dataDate = dateFormat.parse(data.dtTxt)
        dataDate != null && dataDate.after(currentDate)
    }
}

fun convertToKoreanTime(dateStr: String): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val hourFormat = SimpleDateFormat("HH", Locale.getDefault()) // 시간 추출 포맷
    val inputDate = dateFormat.parse(dateStr) ?: return ""
    val currentDate = Date()

    // 현재 시각의 시간을 가져와 3시간 단위로 반올림
    val currentHour = hourFormat.format(currentDate).toInt()
    val roundedHour = (currentHour / 3) * 3 + if (currentHour % 3 >= 1) 3 else 0

    // inputDate의 시간도 추출
    val inputHour = hourFormat.format(inputDate).toInt()

    // 반올림된 현재 시각과 inputDate의 시간이 같으면 "지금"으로 표시
    if (inputHour == roundedHour) {
        return "지금"
    }

    // 오전/오후 및 12시간 형식 변환
    val period = if (inputHour < 12) "오전" else "오후"
    val formattedHour = if (inputHour % 12 == 0) 12 else inputHour % 12

    return "$period ${formattedHour}시"
}

data class WeatherData(
    val date: String,
    val maxTemp: Double,
    val minTemp: Double,
    val weather: String // 대표 날씨 추가
)

fun groupDailyWeather(dataList: List<WeatherInfo>): List<WeatherData> {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val dayFormat = SimpleDateFormat("E", Locale.KOREAN) // 요일 형식
    val groupedData = HashMap<String, Pair<Double, Double>>() // 날짜별 최고/최저 온도 저장
    val weatherCount = HashMap<String, HashMap<String, Int>>() // 날짜별 날씨 카운트 저장

    dataList.forEach { data ->
        val dtTxt = data.dtTxt
        val main = data.main
        val tempMax = main.tempMax
        val tempMin = main.tempMin
        val weather = data.weather.firstOrNull()?.main ?: "Clear" // 첫 번째 날씨 정보 사용 (예: clear, cloud 등)

        // 날짜 추출
        val date = dateFormat.parse(dtTxt) ?: return@forEach
        val day = dayFormat.format(date)

        // 그룹화하여 최고/최저 온도 업데이트
        val currentTemps = groupedData.getOrDefault(day, Pair(tempMax, tempMin))
        groupedData[day] = Pair(
            maxOf(currentTemps.first, tempMax),
            minOf(currentTemps.second, tempMin)
        )

        // 날씨 카운트 업데이트
        val dayWeatherCount = weatherCount.getOrPut(day) { HashMap() }
        dayWeatherCount[weather] = dayWeatherCount.getOrDefault(weather, 0) + 1
    }

    // 오늘의 요일과 요일 순서 설정
    val daysOfWeek = listOf("일", "월", "화", "수", "목", "금", "토")
    val today = dayFormat.format(Date())
    val startIndex = daysOfWeek.indexOf(today)

    // 오늘부터 요일 순서로 정렬하여 반환
    val sortedDays = if (startIndex != -1) {
        daysOfWeek.subList(startIndex, daysOfWeek.size) + daysOfWeek.subList(0, startIndex)
    } else {
        daysOfWeek // 오늘이 리스트에 없는 경우 기본 순서 유지
    }

    return sortedDays.mapNotNull { day ->
        groupedData[day]?.let { temps ->
            val mostCommonWeather = weatherCount[day]?.maxByOrNull { it.value }?.key ?: "Clear"
            WeatherData(
                date = if (day == today) "오늘" else day,
                maxTemp = temps.first,
                minTemp = temps.second,
                weather = mostCommonWeather,
            )
        }
    }
}