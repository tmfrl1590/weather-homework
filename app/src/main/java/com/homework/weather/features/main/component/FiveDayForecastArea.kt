package com.homework.weather.features.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.homework.weather.domain.model.WeatherInfo
import com.homework.weather.features.shared.CommonText
import com.homework.weather.ui.theme.COMPONENT_BACKGROUND
import com.homework.weather.utils.convertToMinTempAndMaxTemp
import com.homework.weather.utils.convertToTextFromWeather
import com.homework.weather.utils.getApiImage
import com.homework.weather.utils.convertToIconNumFromWeather
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun FiveDayForecastArea(
    weatherInfoList: List<WeatherInfo>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 4.dp)
            .testTag("fiveDayForecastArea"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = COMPONENT_BACKGROUND
        ),
    ) {
        groupDailyWeather(weatherInfoList).forEachIndexed { _, weatherData ->
            FiveDayForecastItem(
                day = weatherData.date,
                weather = weatherData.weather,
                minTemp = weatherData.minTemp,
                maxTemp = weatherData.maxTemp,
            )
        }
    }
}

@Composable
fun FiveDayForecastItem(
    day: String,
    weather: String,
    minTemp: Double,
    maxTemp: Double,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(COMPONENT_BACKGROUND),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CommonText(
                modifier = Modifier.weight(1f),
                text = day,
                fontSize = 16.sp,
            )

            Row(
                modifier = Modifier.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CommonText(
                    text = convertToTextFromWeather(weather),
                    fontSize = 16.sp,
                )

                Image(
                    painter = rememberAsyncImagePainter(model = getApiImage(convertToIconNumFromWeather(weather))),
                    contentDescription = "icon image",
                    modifier = Modifier.size(80.dp)
                )
            }

            CommonText(
                modifier = Modifier.weight(2f),
                text = convertToMinTempAndMaxTemp(minTemp = minTemp, maxTemp = maxTemp),
                fontSize = 16.sp,
            )
        }
        HorizontalDivider(color = Color.White, thickness = 0.3.dp)
    }
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