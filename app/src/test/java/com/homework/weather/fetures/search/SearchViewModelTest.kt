package com.homework.weather.fetures.search

import com.google.common.truth.Truth.assertThat
import com.homework.weather.MainDispatcherRule
import com.homework.weather.data.FakeCityRepository
import com.homework.weather.domain.model.CityModel
import com.homework.weather.domain.model.CoordModel
import com.homework.weather.domain.use_cases.GetCityDataFromFileUseCase
import com.homework.weather.features.search.viewmodel.SearchViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {

    private val fakeCityRepository = FakeCityRepository()
    lateinit var getCityDataFromFileUseCase: GetCityDataFromFileUseCase
    lateinit var searchViewModel: SearchViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        getCityDataFromFileUseCase = GetCityDataFromFileUseCase(cityRepository = fakeCityRepository)
        searchViewModel = SearchViewModel(getCityDataFromFileUseCase)
    }

    @Test
    fun json파일로부터_데이터를_정상적으로_파싱한다() = runTest{
        val initValue = searchViewModel.getCityListFromJsonFile.value
        assertThat(initValue).isNotEmpty()
        assertThat(initValue.size).isEqualTo(2)
        assertThat(initValue.size).isNotEqualTo(0)
        assertThat(initValue.size).isNotEqualTo(1)
        assertThat(initValue.size).isNotEqualTo(3)

        val cityModel = initValue[0]
        assertThat(cityModel.id).isEqualTo(0)
        assertThat(cityModel.name).isEqualTo("JAPAN")
        assertThat(cityModel.country).isEqualTo("JP")

        val cityModel2 = initValue[1]
        assertThat(cityModel2.id).isEqualTo(1)
        assertThat(cityModel2.name).isEqualTo("KOREA")
        assertThat(cityModel2.country).isEqualTo("KR")
    }

    @Test
    fun 검색어에_따라_필터링된_도시리스트를_반환한다() = runTest {
        // Given
        val keyword = "JAPAN"

        // When
        searchViewModel.updateKeyword(keyword)

        // Then
        val filteredList = searchViewModel.filteredCityList.first()
        val expectedList = listOf(CityModel(0, "JAPAN", "JP", CoordModel(0.0, 0.0)))

        assertEquals(expectedList, filteredList)
        assertThat(filteredList.size).isEqualTo(1)
        assertThat(filteredList.size).isNotEqualTo(0)
        assertThat(filteredList.size).isNotEqualTo(2)
        assertThat(filteredList[0].id).isEqualTo(0)
        assertThat(filteredList[0].name).isEqualTo("JAPAN")
    }
}