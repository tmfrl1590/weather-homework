package com.homework.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.homework.weather.features.main.MainScreen
import com.homework.weather.features.main.viewmodel.MainViewModel
import com.homework.weather.features.search.SearchScreen
import com.homework.weather.features.search.viewmodel.SearchViewModel
import com.homework.weather.features.shared.SharedViewModel
import com.homework.weather.navigation.Screens
import com.homework.weather.ui.theme.WeatherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherTheme {
                val navController = rememberNavController()
                val sharedViewModel: SharedViewModel = hiltViewModel()
                NavHost(
                    modifier = Modifier
                        .fillMaxSize(),
                    navController = navController,
                    startDestination = Screens.Main
                ) {
                    composable<Screens.Main>{
                        val selectedCity by sharedViewModel.selectedCity.collectAsState()
                        val mainViewModel = hiltViewModel<MainViewModel>()
                        val weatherInfoList by mainViewModel.getWeatherInfoList.collectAsState()

                        LaunchedEffect(key1 = selectedCity) {
                            mainViewModel.getCurrentWeatherInfo(selectedCity.coord.lat, selectedCity.coord.lon)
                        }

                        MainScreen(
                            selectedCity = selectedCity,
                            weatherInfoList = weatherInfoList,
                            onSearchAreaClick = { navController.navigate(Screens.Search) }
                        )
                    }
                    composable<Screens.Search> {
                        var keyword by remember { mutableStateOf("") }
                        val searchViewModel = hiltViewModel<SearchViewModel>()
                        val filteredCityList by searchViewModel.filteredCityList.collectAsState()
                        val loadingState by searchViewModel.loadingState.collectAsState()
                        SearchScreen(
                            keyword = keyword,
                            filteredCityList = filteredCityList,
                            loadingState = loadingState,
                            onValueChange = { keyword = it },
                            onCityItemClick = {
                                sharedViewModel.setSelectedCity(it)
                                navController.popBackStack()
                            },
                            onSearch = { searchViewModel.updateKeyword(keyword = it) }
                        )
                    }
                }
            }
        }
    }
}