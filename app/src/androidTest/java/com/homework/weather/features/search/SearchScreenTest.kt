package com.homework.weather.features.search

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.testing.TestNavHostController
import com.homework.weather.MainActivity
import com.homework.weather.di.CityModule
import com.homework.weather.di.WeatherModule
import com.homework.weather.domain.model.CityModel
import com.homework.weather.domain.model.CoordModel
import com.homework.weather.domain.model.WeatherModel
import com.homework.weather.features.main.MainScreen
import com.homework.weather.features.main.cityModel
import com.homework.weather.navigation.Screens
import com.homework.weather.ui.theme.WeatherTheme
import com.homework.weather.utils.FileReadState
import com.homework.weather.utils.UIState
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(WeatherModule::class, CityModule::class)
class SearchScreenTest {

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
    fun 현재_화면은_검색화면이다() {
        setActivityContent(data = FileReadState.Success(filteredCityList))
        composeTestRule.runOnIdle {
            val actual = testNavHostController.currentDestination?.hasRoute<Screens.Search>()
            assertEquals(true, actual)
        }
    }

    @Test
    fun FileReadState가_Loading_상태이면_로딩창이_보인다(){
        setActivityContent(data = FileReadState.Loading)
        composeTestRule.onNodeWithTag("loading").assertExists()
    }

    @Test
    fun FileReadState가_Error_상태이면_에러창이_보인다(){
        setActivityContent(data = FileReadState.Error())
        composeTestRule.onNodeWithTag("error").assertExists()
    }

    @Test
    fun FileReadState가_Success_상태이면_검색화면이_보인다(){
        setActivityContent(data = FileReadState.Success(filteredCityList))
        composeTestRule.onNodeWithTag("searchBarArea").assertExists()
    }

    @Test
    fun FileReadState가_Success_상태이면_검색결과가_2개_보인다(){
        setActivityContent(data = FileReadState.Success(filteredCityList))
        composeTestRule.onNodeWithTag("cityListArea").assertExists()
        composeTestRule.onAllNodesWithTag("cityItem").assertCountEquals(filteredCityList.size)
        composeTestRule.onNodeWithText("Asan").assertExists()
        composeTestRule.onNodeWithText("Japan").assertExists()
    }

    private fun setActivityContent(data: FileReadState<List<CityModel>>){
        composeTestRule.activity.setContent {
            val data1: UIState<WeatherModel> = UIState.Idle
            testNavHostController = TestNavHostController(LocalContext.current)
            testNavHostController.navigatorProvider.addNavigator(ComposeNavigator())
            WeatherTheme {
                NavHost(
                    navController = testNavHostController,
                    startDestination = Screens.Search
                ){
                    composable<Screens.Main> {
                        MainScreen(
                            selectedCity = cityModel,
                            weatherInfoList = data1,
                            onSearchAreaClick = { testNavHostController.navigate(Screens.Search) }
                        )
                    }
                    composable<Screens.Search> {
                        SearchScreen(
                            keyword = "",
                            filteredCityList = filteredCityList,
                            loadingState = data,
                            onValueChange = {},
                            onCityItemClick = { testNavHostController.popBackStack() },
                            onSearch = {}
                        )
                    }
                }
            }
        }
    }
}

val cityModel1 = CityModel(
    id = 1839726,
    name = "Asan",
    country = "KR",
    coord = CoordModel(127.004173, 36.783611)
)

val cityModel2 = CityModel(
    id = 1839726,
    name = "Japan",
    country = "JA",
    coord = CoordModel(127.004173, 36.783611)
)

val filteredCityList = listOf(cityModel1, cityModel2)