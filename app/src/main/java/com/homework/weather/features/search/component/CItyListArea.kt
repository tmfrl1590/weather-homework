package com.homework.weather.features.search.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.homework.weather.domain.model.CityModel
import com.homework.weather.domain.model.CoordModel
import com.homework.weather.features.shared.CommonText

@Composable
fun CityListArea(
    filteredCityList: List<CityModel>,
    onCityItemClick: (CityModel) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(20.dp)
            .testTag("cityListArea"),
    ) {
        items(filteredCityList.size) { cityModel ->
            CityItem(
                cityModel = filteredCityList[cityModel],
                onClick = { onCityItemClick(it) }
            )
        }
    }
}

@Composable
fun CityItem(
    cityModel: CityModel,
    onClick: (CityModel) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(cityModel) }
            .padding(top = 8.dp)
            .testTag("cityItem"),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CommonText(
            modifier = Modifier
                .fillMaxWidth(),
            text = cityModel.name,
            fontSize = 20.sp
        )
        CommonText(
            modifier = Modifier
                .fillMaxWidth(),
            text = cityModel.country,
            fontSize = 16.sp
        )
        HorizontalDivider(
            color = Color.White,
            thickness = 0.3.dp
        )
    }
}

@Preview
@Composable
fun CityItemPreview() {
    val cityModel = CityModel(
        id = 1,
        name = "Seoul",
        country = "KR",
        coord = CoordModel(
            lon = 126.9778,
            lat = 37.5665
        )
    )
    CityItem(
        cityModel = cityModel,
        onClick = {},
    )
}