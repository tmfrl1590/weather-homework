package com.homework.weather.data.remote.service

import com.google.common.truth.Truth.assertThat
import com.homework.weather.BuildConfig
import com.homework.weather.MainDispatcherRule
import com.homework.weather.data.remote.dto.CityDto
import com.homework.weather.data.remote.dto.CoordDto
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException
import java.util.concurrent.TimeUnit

class WeatherServiceKtTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var weatherService: WeatherService
    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }
    private val contentType = "application/json".toMediaType()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        weatherService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(WeatherService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun 엔드포인트가_정상적으로_호출된다() = runTest{

        //GIVEN
        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))
        val expectedEndPoint = "/data/2.5/forecast?lat=36.783611&lon=127.004173&appid=${BuildConfig.API_KEY}"

        //WHEN
        weatherService.getWeather(36.783611,127.004173)

        //THEN
        val request = mockWebServer.takeRequest()
        assertThat(request.path).isEqualTo(expectedEndPoint)
    }

    @Test
    fun API가_정상적으로_호출된다() = runTest{
        //GIVEN
        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200))

        // WHEN
        val actualResponse = weatherService.getWeather(36.783611, 127.004173)

        // THEN
        assertThat(actualResponse).isNotNull()
        assertThat(actualResponse.cnt).isEqualTo(40)
        assertThat(actualResponse.city).isEqualTo(CityDto(id = 1839726, name = "Asan", coord = CoordDto(36.783611, 127.004173), country = "KR", population = 0, timezone = 32400, sunrise = 1600406400, sunset = 1600449600))
        assertThat(actualResponse.message).isEqualTo(0)
        assertThat(actualResponse.cod).isEqualTo("200")
    }

    @Test(expected = HttpException::class)
    fun API_요청결과_404응답시_HttpException이_발생한다() = runTest{
        //GIVEN
        mockWebServer.enqueue(MockResponse().setResponseCode(404))

        // WHEN
        weatherService.getWeather(36.783611, 127.004173)
    }

    @Test(expected = IOException::class)
    fun API_요청응답이_없으면_IOException이_발생한다() = runTest{
        //GIVEN
        mockWebServer.shutdown()

        // WHEN
        weatherService.getWeather(36.783611, 127.004173)
    }

    @Test(expected = IOException::class)
    fun 네트워크_타임아웃() = runTest {
        mockWebServer.enqueue(MockResponse().setBody(jsonResponse).setResponseCode(200).setBodyDelay(10, TimeUnit.SECONDS))
        weatherService.getWeather(36.783611, 127.004173)
    }

    @Test(expected = HttpException::class)
    fun 잘못된_요청을_하면_400_응답이_반환된다() = runTest {
        mockWebServer.enqueue(MockResponse().setResponseCode(400))
        weatherService.getWeather(0.0, 0.0)
    }
}

val jsonResponse = """
        {
          "cod": "200",
          "message": 0,
          "cnt": 40,
          "city": {
            "id": 1839726,
            "name": "Asan",
            "coord": {
              "lat": 36.783611,
              "lon": 127.004173
            },
            "country": "KR",
            "population": 0,
            "timezone": 32400,
            "sunrise": 1600406400,
            "sunset": 1600449600
          },
          "list": []
        }
    """.trimIndent()