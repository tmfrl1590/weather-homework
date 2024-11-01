package com.homework.weather.features.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.homework.weather.domain.model.City
import com.homework.weather.domain.model.CityModel
import com.homework.weather.domain.model.Coord
import com.homework.weather.domain.model.CoordModel
import com.homework.weather.domain.model.WeatherModel
import com.homework.weather.features.main.component.CurrentWeatherInfoArea
import com.homework.weather.features.main.component.FiveDayForecastArea
import com.homework.weather.features.main.component.MapViewArea
import com.homework.weather.features.main.component.SearchArea
import com.homework.weather.features.main.component.TwoDayThreeHourForecastArea
import com.homework.weather.features.main.component.WeatherInfoDetailArea
import com.homework.weather.features.shared.ErrorArea
import com.homework.weather.features.shared.LoadingArea
import com.homework.weather.ui.theme.BACKGROUND
import com.homework.weather.utils.UIState

@Composable
fun MainScreen(
    selectedCity: CityModel,
    weatherInfoList: UIState<WeatherModel>,
    onSearchAreaClick: () -> Unit,
) {
    when(weatherInfoList){
        is UIState.Idle -> {}
        is UIState.Loading -> LoadingArea()
        is UIState.Success -> {
            MainScreenContent(
                selectedCity = selectedCity,
                weatherInfo = weatherInfoList.data,
                onSearchAreaClick = onSearchAreaClick
            )
        }
        is UIState.Error -> ErrorArea()
    }
}

@Composable
fun MainScreenContent(
    selectedCity: CityModel,
    weatherInfo: WeatherModel?,
    onSearchAreaClick: () -> Unit,
) {
    val scrollState = rememberScrollState()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BACKGROUND)
                .padding(innerPadding)
                .padding(12.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            SearchArea(onSearchAreaClick = onSearchAreaClick)

            CurrentWeatherInfoArea(
                city = selectedCity,
                weather = weatherInfo ?: return@Scaffold
            )

            TwoDayThreeHourForecastArea(
                weatherInfoList = weatherInfo.list
            )

            FiveDayForecastArea(
                weatherInfoList = weatherInfo.list
            )

            MapViewArea(
                selectedCity = selectedCity
            )

            WeatherInfoDetailArea(
                humidity = weatherInfo.list[0].main.humidity,
                clouds = weatherInfo.list[0].clouds.all,
                speed = weatherInfo.list[0].wind.speed,
                pressure = weatherInfo.list[0].main.pressure
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenContentPreview() {
    val item1 = CityModel(
        id = 1839726,
        name = "Asan",
        country = "KR",
        coord = CoordModel(127.004173, 36.783611)
    )

    val weatherModel = WeatherModel(
        cod = "",
        message = 200,
        cnt = 40,
        city = City(
            id = 1839726,
            name = "Asan",
            coord = Coord(127.004173, 36.783611),
            population = 0,
            country = "KR",
            timezone = 0,
            sunrise = 0,
            sunset = 0
        ),
        list = emptyList()
    )

    MainScreenContent(
        selectedCity = item1,
        weatherInfo = weatherModel,
        onSearchAreaClick = {}
    )
}