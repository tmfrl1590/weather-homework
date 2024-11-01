package com.homework.weather.domain.use_cases

import com.homework.weather.domain.model.CityModel
import com.homework.weather.domain.repository.CityRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCityDataFromFileUseCase @Inject constructor(
    private val cityRepository: CityRepository,
) {
    operator fun invoke(keyword: String): Flow<List<CityModel>> {
        return cityRepository.getCityFileData(keyword = keyword)
    }
}