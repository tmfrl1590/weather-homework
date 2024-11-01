package com.homework.weather.features.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.homework.weather.domain.model.CityModel
import com.homework.weather.domain.model.CoordModel
import com.homework.weather.features.search.component.CityListArea
import com.homework.weather.features.search.component.SearchBarArea
import com.homework.weather.features.shared.ErrorArea
import com.homework.weather.features.shared.LoadingArea
import com.homework.weather.ui.theme.BACKGROUND
import com.homework.weather.utils.FileReadState

@Composable
fun SearchScreen(
    keyword: String,
    filteredCityList: List<CityModel>,
    loadingState: FileReadState<List<CityModel>>,
    onValueChange: (String) -> Unit,
    onCityItemClick: (CityModel) -> Unit,
    onSearch: (String) -> Unit,
) {
    when (loadingState) {
        is FileReadState.Idle -> {}
        is FileReadState.Loading -> { LoadingArea() }
        is FileReadState.Success -> {
            SearchScreenContent(
                keyword = keyword,
                filteredCityList = filteredCityList,
                onValueChange = onValueChange,
                onCityItemClick = { onCityItemClick(it) },
                onSearch = { onSearch(keyword) }
            )
        }
        is FileReadState.Error -> ErrorArea()
    }
}

@Composable
fun SearchScreenContent(
    keyword: String,
    filteredCityList: List<CityModel>,
    onValueChange: (String) -> Unit,
    onCityItemClick: (CityModel) -> Unit,
    onSearch: (String) -> Unit,
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BACKGROUND)
                .padding(paddingValues)
                .padding(20.dp)
        ) {
            SearchBarArea(
                keyword = keyword,
                onValueChange = onValueChange,
                onSearch = onSearch,
            )

            CityListArea(
                filteredCityList = filteredCityList,
                onCityItemClick = onCityItemClick
            )
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    val item1 = CityModel(
        id = 1,
        name = "Seoul",
        country = "KR",
        coord = CoordModel(37.5665, 126.978),
    )
    val item2 = CityModel(
        id = 1,
        name = "Asan",
        country = "KR",
        coord = CoordModel(37.5665, 126.978),
    )
    SearchScreen(
        keyword = "Seoul",
        filteredCityList = listOf(item1, item2),
        loadingState = FileReadState.Success(emptyList()),
        onValueChange = {},
        onCityItemClick = {},
        onSearch = {},
    )
}