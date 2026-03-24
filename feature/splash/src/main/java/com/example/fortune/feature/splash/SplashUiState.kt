package com.example.fortune.feature.splash

sealed interface SplashUiState {
    data object Loading : SplashUiState
    data object Authenticated : SplashUiState
    data object UnAuthenticated : SplashUiState
    data class Error(val message: String) : SplashUiState
}


