package com.homework.weather.data.repository

import com.homework.weather.data.local.FileDataSource
import com.homework.weather.domain.model.CityModel
import com.homework.weather.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CityRepositoryImpl @Inject constructor(
    private val dataSource: FileDataSource,
) : CityRepository {
    override fun getCityFileData(keyword: String): Flow<List<CityModel>> {
        return dataSource.getCityDataFromFile(keyword = keyword)
    }
}