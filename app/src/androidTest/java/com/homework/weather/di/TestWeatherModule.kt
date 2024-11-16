package com.homework.weather.di

import com.homework.weather.BuildConfig
import com.homework.weather.data.remote.service.WeatherService
import com.homework.weather.data.repository.WeatherRepositoryImpl
import com.homework.weather.domain.repository.WeatherRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val TIME_OUT_VALUE: Long = 5000

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [WeatherModule::class]
)
object TestWeatherModule {
    private val json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    @Singleton
    @Provides
    fun logging(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .connectTimeout(TIME_OUT_VALUE, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT_VALUE, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT_VALUE, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherService: WeatherService): WeatherRepository {
        return WeatherRepositoryImpl(weatherService = weatherService)
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Singleton
    @Provides
    fun provideWeatherService(): WeatherService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(logging())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(WeatherService::class.java)
    }
}