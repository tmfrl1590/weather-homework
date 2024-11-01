package com.homework.weather.data

import com.homework.weather.domain.model.CityModel
import com.homework.weather.domain.model.CoordModel
import com.homework.weather.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeCityRepository: CityRepository {
    override fun getCityFileData(keyword: String): Flow<List<CityModel>> {
        return flowOf(listOf(cityModel1, cityModel2))
    }
}

val cityModel1 = CityModel(
    id = 0,
    name = "JAPAN",
    country = "JP",
    coord = CoordModel(
        lat = 0.0,
        lon = 0.0
    )
)

val cityModel2 = CityModel(
    id = 1,
    name = "KOREA",
    country = "KR",
    coord = CoordModel(
        lat = 0.0,
        lon = 0.0
    )
)