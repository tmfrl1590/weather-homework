package com.homework.weather.features.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.homework.weather.domain.model.CityModel
import com.homework.weather.domain.use_cases.GetCityDataFromFileUseCase
import com.homework.weather.utils.FileReadState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getCityDataFromFileUseCase: GetCityDataFromFileUseCase,
): ViewModel(){

    private val _getCityListFromJsonFile = MutableStateFlow<List<CityModel>>(emptyList())
    val getCityListFromJsonFile: StateFlow<List<CityModel>> = _getCityListFromJsonFile

    private val _searchKeyword = MutableStateFlow("")
    private val searchKeyword: StateFlow<String> = _searchKeyword

    private val _loadingState = MutableStateFlow<FileReadState<List<CityModel>>>(FileReadState.Idle)
    val loadingState: StateFlow<FileReadState<List<CityModel>>> = _loadingState

    // 필터링된 도시 리스트
    val filteredCityList: StateFlow<List<CityModel>> = combine(
        getCityListFromJsonFile,
        searchKeyword
    ) { cityList, keyword ->
        if (keyword.isEmpty()) {
            cityList // 키워드가 비어있으면 전체 리스트 반환
        } else {
            cityList.filter { it.name.startsWith(keyword, ignoreCase = true) } // 대소문자 구분 X
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    init {
        getCityDataFromFile("")
    }

    // reduced_citylist.json 파일로부터 도시 정보 파싱
    private fun getCityDataFromFile(keyword: String){
        viewModelScope.launch(Dispatchers.IO) {
            _loadingState.emit(FileReadState.Loading)
            try {
                getCityDataFromFileUseCase(keyword).collectLatest {
                    _getCityListFromJsonFile.emit(it)
                    _loadingState.emit(FileReadState.Success(it))
                }
            }catch (e: Exception){
                _loadingState.emit(FileReadState.Error())
            }
        }
    }

    // 검색 키워드 업데이트
    fun updateKeyword(keyword: String) {
        _searchKeyword.value = keyword
    }
}