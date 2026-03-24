package com.example.fortune

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fortune.core.domain.CheckAuthStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkAuthStatusUseCase: CheckAuthStatusUseCase,
) : ViewModel() {

    private val _uiState = MutableSharedFlow<MainUiState>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST // 버퍼가 넘치면 오래된 것 삭제
    )
    val uiState = _uiState.asSharedFlow()

    fun checkAuthStatus() {
        viewModelScope.launch {
            val isLoggedIn = checkAuthStatusUseCase.invoke()
            if (isLoggedIn) {
                // 홈 화면으로 이동
                _uiState.emit(MainUiState.NavigateToHome)
            } else {
                // 로그인 화면으로 이동
                _uiState.emit(MainUiState.NavigateToLogin)
            }
        }
    }

    sealed class MainUiState {
        object Loading : MainUiState()
        object NavigateToHome : MainUiState()
        object NavigateToLogin : MainUiState()
    }
}