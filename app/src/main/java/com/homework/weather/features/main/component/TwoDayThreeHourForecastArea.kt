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
import com.homework.weather.utils.convertToKoreanTime
import com.homework.weather.utils.filterPastWeatherData
import com.homework.weather.utils.getApiImage

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