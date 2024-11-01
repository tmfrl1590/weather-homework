package com.homework.weather.features.shared

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

@Composable
fun ErrorArea(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .testTag("error"),
        contentAlignment = Alignment.Center
    ){
        Text(text = "에러 화면")
    }
}