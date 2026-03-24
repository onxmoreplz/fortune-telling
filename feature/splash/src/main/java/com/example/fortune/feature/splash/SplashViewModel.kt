package com.example.fortune.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fortune.core.domain.CheckAuthStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val checkAuthStatusUseCase: CheckAuthStatusUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        viewModelScope.launch {
            val result = runCatching { checkAuthStatusUseCase() }
            _uiState.value = result.fold(
                onSuccess = { if (it) SplashUiState.Authenticated else SplashUiState.UnAuthenticated },
                onFailure = { SplashUiState.Error(it.message ?: "--- 오류가 발생했습니다. ---") }
            )
        }
    }
}