package com.example.fortune.feature.login

/**
 * 로그인 화면의 UI 상태를 표현하는 Sealed Class
 */
sealed interface LoginUiState {
    /** 초기 / 입력 대기 상태 */
    data object Idle : LoginUiState

    /** 로그인 처리 중 (로딩) */
    data object Loading : LoginUiState

    /** 로그인 성공 */
    data object Success : LoginUiState

    /** 로그인 실패 */
    data class Error(val message: String) : LoginUiState
}

/**
 * 로그인 폼 입력값 상태
 */
data class LoginFormState(
    val id: String = "",
    val password: String = "",

    // 유효성 검사 에러 메시지
    val idError: String? = null,
    val passwordError: String? = null,
)