package com.homework.weather.navigation

import kotlinx.serialization.Serializable

interface Screens {
    @Serializable
    data object Main: Screens
    @Serializable
    data object Search: Screens
}