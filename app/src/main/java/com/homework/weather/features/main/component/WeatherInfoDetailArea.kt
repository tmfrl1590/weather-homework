package com.homework.weather.features.main.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.homework.weather.R
import com.homework.weather.features.shared.CommonText
import com.homework.weather.ui.theme.COMPONENT_BACKGROUND

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WeatherInfoDetailArea(
    humidity: Int,
    clouds: Int,
    speed: Double,
    pressure: Int,
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        WeatherInfoDetailItem(
            modifier = Modifier.testTag("WeatherInfoDetailItem1"),
            title = stringResource(id = R.string.detail1),
            data = "$humidity%"
        )
        WeatherInfoDetailItem(
            modifier = Modifier.testTag("WeatherInfoDetailItem2"),
            title = stringResource(id = R.string.detail2),
            data = "$clouds%"
        )
        WeatherInfoDetailItem(
            modifier = Modifier.testTag("WeatherInfoDetailItem3"),
            title = stringResource(id = R.string.detail3),
            data = "$speed m/s"
        )
        WeatherInfoDetailItem(
            modifier = Modifier.testTag("WeatherInfoDetailItem4"),
            title = stringResource(id = R.string.detail4),
            data = "$pressure hPa"
        )
    }
}

@Composable
fun WeatherInfoDetailItem(
    modifier: Modifier = Modifier,
    title: String,
    data: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .aspectRatio(1f)
            .padding(6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(COMPONENT_BACKGROUND)
                .padding(12.dp),
        ){
            Column {
                CommonText(
                    text = title,
                    fontSize = 16.sp,
                )
                Spacer(modifier = Modifier.height(20.dp))
                CommonText(
                    modifier = modifier,
                    text = data,
                    fontSize = 32.sp,
                )
            }
        }
    }
}