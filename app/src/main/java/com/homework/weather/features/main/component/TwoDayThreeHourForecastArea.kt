package com.homework.weather.features.main.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.homework.weather.domain.model.WeatherInfo
import com.homework.weather.features.shared.CommonText
import com.homework.weather.ui.theme.COMPONENT_BACKGROUND
import com.homework.weather.utils.convertToCelsiusFromKelvin
import com.homework.weather.utils.getApiImage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TwoDayThreeHourForecastArea(
    weatherInfoList: List<WeatherInfo>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .testTag("twoDayThreeHourForecastArea"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = COMPONENT_BACKGROUND
        ),
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            contentPadding = PaddingValues(4.dp)
        ) {
            itemsIndexed(
                items = filterPastWeatherData(weatherInfoList),
            ){ _, item ->
                TwoDayThreeHourForecastItem(
                    time = convertToKoreanTime(item.dtTxt),
                    temperature = convertToCelsiusFromKelvin(item.main.temp),
                    imageUrl = getApiImage(item.weather[0].icon),
                )
            }
        }
    }
}

@Composable
fun TwoDayThreeHourForecastItem(
    time: String,
    temperature: String,
    imageUrl: String,
) {
   Column(
       modifier = Modifier
           .fillMaxHeight()
           .wrapContentHeight()
           .padding(12.dp),
   ) {
       CommonText(
           modifier = Modifier.align(CenterHorizontally),
           text = time,
           fontSize = 16.sp
       )
       CommonText(
           modifier = Modifier.align(CenterHorizontally),
           text = temperature,
           fontSize = 16.sp
       )
       Image(
           painter = rememberAsyncImagePainter(model = imageUrl),
           contentDescription = "icon image",
           modifier = Modifier.size(60.dp)
       )
   }
}

private fun filterPastWeatherData(list: List<WeatherInfo>): List<WeatherInfo> {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val currentDate = Date()

    // 현재 시간을 기준으로 과거의 데이터만 필터링
    return list.filter { data ->
        val dataDate = dateFormat.parse(data.dtTxt)
        dataDate != null && dataDate.after(currentDate)
    }
}

private fun convertToKoreanTime(dateStr: String): String {
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