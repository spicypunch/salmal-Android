package kr.jm.salmal_android.utils

import androidx.compose.runtime.MutableState

interface LoadingHandler {
    val isLoading: MutableState<Boolean>

    fun showIndicator() {
        isLoading.value = true
    }

    fun hideIndicator() {
        isLoading.value = false
    }
}
