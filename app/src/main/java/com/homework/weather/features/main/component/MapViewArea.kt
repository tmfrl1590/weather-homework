package com.homework.weather.features.main.component

import android.view.Gravity
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.homework.weather.domain.model.CityModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationOverlay
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapViewArea(
    selectedCity: CityModel,
) {
    // 현재 위치 좌표
    val currentLocation by remember {
        mutableStateOf(LatLng(selectedCity.coord.lat, selectedCity.coord.lon))
    }

    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        // 카메라 초기 위치 설정
        position = CameraPosition(currentLocation, 11.0)
    }

    LaunchedEffect(currentLocation) {
        cameraPositionState.move(CameraUpdate.zoomIn())
    }

    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                isScaleBarEnabled = true, // 축척 막대기 보이기 true
                isLocationButtonEnabled = true, // 좌측 하단 현재 위치 버튼 보이기 true
                logoGravity = Gravity.BOTTOM or Gravity.END,
                isLogoClickEnabled = false, // 네이버 로고 클릭 false
                isRotateGesturesEnabled = true, // 회전 제스처 true
            )
        )
    }

    NaverMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .testTag("mapView"),
        locationSource = rememberFusedLocationSource(isCompassEnabled = true),
        uiSettings = mapUiSettings,
        cameraPositionState = cameraPositionState,
    ){
        LocationOverlay(position = currentLocation)

        Marker(
            state = MarkerState(position = LatLng(currentLocation.latitude, currentLocation.longitude)),
            width = 24.dp,
            height = 32.dp,
            onClick = {
                cameraPositionState.move(CameraUpdate.scrollTo(LatLng(currentLocation.latitude, currentLocation.longitude)))
                true
            },
        )
    }

}