package com.homework.weather.domain.repository

import com.homework.weather.domain.model.CityModel
import kotlinx.coroutines.flow.Flow

interface CityRepository {

    fun getCityFileData(keyword: String): Flow<List<CityModel>>

}