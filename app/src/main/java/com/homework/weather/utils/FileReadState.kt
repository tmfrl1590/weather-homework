package com.homework.weather.utils

sealed class FileReadState<out T> {
    data object Idle : FileReadState<Nothing>()
    data object Loading : FileReadState<Nothing>()
    data class Success<out T>(val data: T) : FileReadState<T>()
    data class Error(val message: String? = null) : FileReadState<Nothing>()
}