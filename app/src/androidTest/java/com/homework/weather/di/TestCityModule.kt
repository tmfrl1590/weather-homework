package com.homework.weather.di

import com.homework.weather.data.local.FileDataSource
import com.homework.weather.data.repository.CityRepositoryImpl
import com.homework.weather.domain.repository.CityRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [WeatherModule::class]
)
object TestCityModule {

    @Provides
    @Singleton
    fun provideCityRepository(
        dataSource: FileDataSource,
    ): CityRepository {
        return CityRepositoryImpl(dataSource = dataSource)
    }
}