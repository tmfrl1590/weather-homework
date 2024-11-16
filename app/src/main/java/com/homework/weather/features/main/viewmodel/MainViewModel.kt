package com.homework.weather.features.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homework.weather.domain.model.WeatherModel
import com.homework.weather.domain.use_cases.GetWeatherInfoUseCase
import com.homework.weather.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getWeatherInfoUseCase: GetWeatherInfoUseCase,
): ViewModel(){

    private val _getWeatherInfoList = MutableStateFlow<UIState<WeatherModel>>(UIState.Idle)
    val getWeatherInfoList: StateFlow<UIState<WeatherModel>> = _getWeatherInfoList

    fun getCurrentWeatherInfo(lat: Double, lon: Double){
        viewModelScope.launch(Dispatchers.IO) {
            _getWeatherInfoList.emit(UIState.Loading)
            try {
                val result = getWeatherInfoUseCase(lat, lon)
                _getWeatherInfoList.emit(UIState.Success(result))
            } catch (e: Exception){
                _getWeatherInfoList.emit(UIState.Error())
            }
        }
    }
}