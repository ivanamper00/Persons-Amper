package com.amper.personapp.util

sealed class UiState<out T> {
    data class Loading(val isLoading: Boolean): UiState<Nothing>()
    data class Error(val error: Throwable): UiState<Nothing>()
    data class Success<T>(val data: T): UiState<T>()
}