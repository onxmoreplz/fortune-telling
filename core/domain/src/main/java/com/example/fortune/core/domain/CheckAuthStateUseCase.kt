package com.example.fortune.core.domain

import javax.inject.Inject

/**
 * 로그인 상태 확인 UseCase
 *
 * @return true  → 로그인 상태 (토큰 유효)
 * @return false → 미로그인 또는 토큰 만료
 */
class CheckAuthStatusUseCase @Inject constructor(
//    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Boolean {
        // TODO: 로그인 로직 추후 구현
        // 현재는 Bypass 처리
        return true
    }
}