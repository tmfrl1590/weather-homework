package com.homework.weather.features.main

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import com.homework.weather.MainActivity
import com.homework.weather.di.CityModule
import com.homework.weather.di.WeatherModule
import com.homework.weather.domain.model.City
import com.homework.weather.domain.model.CityModel
import com.homework.weather.domain.model.Clouds
import com.homework.weather.domain.model.Coord
import com.homework.weather.domain.model.CoordModel
import com.homework.weather.domain.model.Main
import com.homework.weather.domain.model.Sys
import com.homework.weather.domain.model.Weather
import com.homework.weather.domain.model.WeatherInfo
import com.homework.weather.domain.model.WeatherModel
import com.homework.weather.domain.model.Wind
import com.homework.weather.features.search.SearchScreen
import com.homework.weather.features.search.filteredCityList
import com.homework.weather.navigation.Screens
import com.homework.weather.ui.theme.WeatherTheme
import com.homework.weather.utils.FileReadState
import com.homework.weather.utils.UIState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(WeatherModule::class, CityModule::class)
class MainScreenKtTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    private lateinit var testNavHostController: TestNavHostController

    @Before
    fun setUp() {
        hiltRule.inject()
        testNavHostController = TestNavHostController(composeTestRule.activity)
        testNavHostController.navigatorProvider.addNavigator(ComposeNavigator())
    }

    @Test
    fun 현재_화면은_메인화면이다() {
        setActivityContent(data = UIState.Success(weatherModel))
        // runOnIdle을 사용하여 NavController가 초기화될 때까지 기다림
        composeTestRule.runOnIdle {
            val actual = testNavHostController.currentDestination?.hasRoute<Screens.Main>()
            assertEquals(true, actual)
        }
    }

    @Test
    fun 검색영역을_클릭하면_검색화면으로_이동한다(){
        setActivityContent(data = UIState.Success(weatherModel))
        composeTestRule.onNodeWithTag("main_search_area").performClick()
        composeTestRule.runOnIdle {
            val actual = testNavHostController.currentDestination?.hasRoute<Screens.Search>()
            assertEquals(true, actual)
        }
    }

    @Test
    fun UIState가_Loading_상태이면_로딩창이_보인다() = runTest{
        setActivityContent(data = UIState.Loading)
        composeTestRule.onNodeWithTag("loading").assertExists()
    }

    @Test
    fun UIState가_Error_상태이면_에러화면이_보인다() = runTest{
        setActivityContent(data = UIState.Error())
        composeTestRule.onNodeWithText("에러 화면").assertExists()
        composeTestRule.onNodeWithTag("error").assertExists()
    }

    @Test
    fun UIState가_Success_이면_오늘의_날씨정보가_보인다() = runTest{
        setActivityContent(data = UIState.Success(weatherModel))
        composeTestRule.onNodeWithText("Asan").assertExists()
        composeTestRule.onNodeWithTag("currentTemp").onChild().assertTextEquals("15.1°")
        composeTestRule.onNodeWithTag("currentWeatherKR").onChild().assertTextEquals("구름")
    }

    @Test
    fun UIState가_Success_이면_하단의_구름_습도_풍속_기압_정보가_보인다() = runTest {
        setActivityContent(data = UIState.Success(weatherModel))
        composeTestRule.onNodeWithTag("WeatherInfoDetailItem1").onChild().assertTextEquals("72%")
        composeTestRule.onNodeWithTag("WeatherInfoDetailItem2").onChild().assertTextEquals("100%")
        composeTestRule.onNodeWithTag("WeatherInfoDetailItem3").onChild().assertTextEquals("1.56 m/s")
        composeTestRule.onNodeWithTag("WeatherInfoDetailItem4").onChild().assertTextEquals("1019 hPa")
    }

    @Test
    fun UIState가_Success_이면_지도가_보인다() = runTest{
        setActivityContent(data = UIState.Success(weatherModel))
        composeTestRule.onNodeWithTag("mapView").assertExists()
    }

    @Test
    fun UIState가_Success_이면_3시간_간격으로_2일간의_날씨정보가_보인다() = runTest{
        setActivityContent(data = UIState.Success(weatherModel))
        composeTestRule.onNodeWithTag("twoDayThreeHourForecastArea").assertExists()
    }

    @Test
    fun UIState가_Success_이면_5일간의_날씨정보가_보인다() = runTest{
        setActivityContent(data = UIState.Success(weatherModel))
        composeTestRule.onNodeWithTag("fiveDayForecastArea").assertExists()
    }

    private fun setActivityContent(data : UIState<WeatherModel>){
        composeTestRule.activity.setContent {
            val loadingState = FileReadState.Success(filteredCityList)

            testNavHostController = TestNavHostController(LocalContext.current)
            testNavHostController.navigatorProvider.addNavigator(ComposeNavigator())
            WeatherTheme {
                NavHost(
                    navController = testNavHostController,
                    startDestination = Screens.Main
                ){
                    composable<Screens.Main> {
                        MainScreen(
                            selectedCity = cityModel,
                            weatherInfoList = data,
                            onSearchAreaClick = {testNavHostController.navigate(Screens.Search)}
                        )
                    }
                    composable<Screens.Search> {
                        SearchScreen(
                            keyword = "",
                            filteredCityList = filteredCityList,
                            loadingState = loadingState,
                            onValueChange = {},
                            onCityItemClick = {},
                            onSearch = {}
                        )
                    }
                }
            }
        }
    }
}

val cityModel = CityModel(
    id = 1839726,
    name = "Asan",
    country = "KR",
    coord = CoordModel(127.004173, 36.783611)
)

val weatherInfo1 = WeatherInfo(
    dt = 1633666800,
    main = Main(
        temp = 288.28,
        feelsLike = 0.0,
        tempMin = 288.28,
        tempMax = 288.28,
        pressure = 1019,
        seaLevel = 0,
        grndLevel = 0,
        humidity = 72,
        tempKf = 0.0
    ),
    weather = listOf(
      Weather(
          id = 804,
          main = "Clouds",
          description = "overcast clouds",
          icon = "04n"
      ),
    ),
    clouds = Clouds(
        all = 100
    ),
    wind = Wind(
        speed = 1.56,
        deg = 0,
        gust = 0.0
    ),
    visibility = 0,
    pop = 0.0,
    sys = Sys(
        pod = ""
    ),
    dtTxt = "2024-11-01 12:00:00"
)

val weatherModel = WeatherModel(
    cod = "",
    message = 200,
    cnt = 40,
    city = City(
        id = 1839726,
        name = "Asan",
        coord = Coord(127.004173, 36.783611),
        population = 0,
        country = "KR",
        timezone = 0,
        sunrise = 0,
        sunset = 0
    ),
    list = listOf(
        weatherInfo1, weatherInfo1, weatherInfo1, weatherInfo1, weatherInfo1, weatherInfo1, weatherInfo1, weatherInfo1
    )
)