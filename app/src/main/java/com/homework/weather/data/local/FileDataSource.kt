package com.homework.weather.data.local

import android.content.Context
import com.homework.weather.domain.model.CityModel
import com.homework.weather.domain.model.CoordModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONArray
import javax.inject.Inject

class FileDataSource @Inject constructor(
    @ApplicationContext private val context: Context
){
    private val cityList = ArrayList<CityModel>()

    fun getCityDataFromFile(keyword: String): Flow<List<CityModel>> = flow {
        cityList.clear()
        val jsonString = context.assets.open("reduced_citylist.json").reader().readText()
        val resultList = JSONArray(jsonString)

        for(i in 0 until resultList.length()){
            val jsonObject = resultList.getJSONObject(i)
            val cityId = jsonObject.getString("id").toInt()
            val cityName = jsonObject.getString("name")
            val cityLatitude = jsonObject.getString("country")

            val coordObject = jsonObject.getJSONObject("coord")
            val lon = coordObject.getDouble("lon")
            val lat = coordObject.getDouble("lat")

            val coord = CoordModel(lon, lat)
            val city = CityModel(cityId, cityName, cityLatitude, coord)

            // keywor d가 "" 이거나 있는 경우만 추가
            if (keyword.isEmpty() || cityName.contains(keyword, ignoreCase = true)) {
                cityList.add(city)
            }
        }
        emit(cityList)
    }
}