package com.homework.weather.features.shared

import androidx.lifecycle.ViewModel
import com.homework.weather.domain.model.CityModel
import com.homework.weather.domain.model.CoordModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor() : ViewModel() {
    val initCity = CityModel(id = 1839726, name = "Asan", country = "KR", coord = CoordModel(127.004173, 36.783611))

    private val _selectedCity = MutableStateFlow(initCity)
    val selectedCity: StateFlow<CityModel> = _selectedCity

    fun setSelectedCity(cityModel: CityModel){
        _selectedCity.value = cityModel
    }
}