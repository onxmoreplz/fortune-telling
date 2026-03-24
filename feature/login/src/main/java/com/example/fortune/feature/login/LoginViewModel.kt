package com.example.fortune.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
//    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    // ── 폼 입력 상태 ──────────────────────────────────────────────
    private val _formState = MutableStateFlow(LoginFormState())
    val formState: StateFlow<LoginFormState> = _formState.asStateFlow()

    // ── 로그인 처리 상태 ──────────────────────────────────────────
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // ── 폼 입력 이벤트 핸들러 ─────────────────────────────────────

    fun onIdChanged(value: String) {
        _formState.update { it.copy(id = value, idError = null) }
    }

    fun onPasswordChanged(value: String) {
        _formState.update { it.copy(password = value, passwordError = null) }
    }

    fun onLoginClicked() {
        if (!validateForm()) return

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            val form = _formState.value
            val result = runCatching {
//                loginUseCase(id = form.id.trim(), password = form.password)
            }

            _uiState.value = result.fold(
                onSuccess = { LoginUiState.Success },
                onFailure = { LoginUiState.Error(it.message ?: "알 수 없는 오류가 발생했습니다.") },
            )
        }
    }

    fun onErrorConsumed() {
        _uiState.value = LoginUiState.Idle
    }



    // ── 유효성 검사 ───────────────────────────────────────────────

    private fun validateForm(): Boolean {
        val form = _formState.value

        val idError = when {
            form.id.isBlank()       -> "아이디를 입력해주세요."
            form.id.length < 4      -> "아이디는 4자 이상 입력해주세요."
            else                    -> null
        }

        val passwordError = when {
            form.password.isBlank() -> "비밀번호를 입력해주세요."
            form.password.length < 6 -> "비밀번호는 6자 이상 입력해주세요."
            else                    -> null
        }

        if (idError != null || passwordError != null) {
            _formState.update { it.copy(idError = idError, passwordError = passwordError) }
            return false
        }

        return true
    }
}