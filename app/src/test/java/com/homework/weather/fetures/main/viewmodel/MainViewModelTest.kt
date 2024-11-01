package com.homework.weather.fetures.main.viewmodel

import com.google.common.truth.Truth.assertThat
import com.homework.weather.MainDispatcherRule
import com.homework.weather.data.FakeWeatherRepository
import com.homework.weather.domain.use_cases.GetWeatherInfoUseCase
import com.homework.weather.features.main.viewmodel.MainViewModel
import com.homework.weather.utils.UIState
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    private val fakeWeatherRepository = FakeWeatherRepository()
    lateinit var getWeatherInfoUseCase: GetWeatherInfoUseCase
    lateinit var mainViewModel: MainViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        getWeatherInfoUseCase = GetWeatherInfoUseCase(weatherRepository = fakeWeatherRepository)
        mainViewModel = MainViewModel(getWeatherInfoUseCase)
    }

    @Test
    fun 응답데이터의_초기값이_null_인지_확인() = runTest{
        val initValue = mainViewModel.getWeatherInfoList.value
        assertThat(initValue).isEqualTo(UIState.Idle)

        val weatherModel = initValue.data ?: return@runTest
        assertThat(weatherModel.cnt).isEqualTo(null)
        assertThat(weatherModel.message).isEqualTo(null)
    }

    @Test
    fun 정상응답_데이터를_확인한다() = runTest {
        val weatherModel = getWeatherInfoUseCase(0.0, 0.0)
        assertThat(weatherModel.cod).isEqualTo("200")
        assertThat(weatherModel.cod).isNotEqualTo("0")
        assertThat(weatherModel.message).isEqualTo(0)
        assertThat(weatherModel.cnt).isEqualTo(1)
        assertThat(weatherModel.list).isEmpty()
    }

}