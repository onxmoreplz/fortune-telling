package com.example.fortune.core.data.repository

import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(

): AuthRepository {
    override suspend fun checkAuthStatus(): Boolean {
        // 예시 1. 로컬 토큰 존재 여부 확인
        // return dataStore.getToken() != null

        // 예시 2. 서버 API 호출
        // return apiService.validateToken().isSuccessful

        return false
    }

    override suspend fun login(id: String, password: String) {
        //TODO 로그인 API 호출
    }
}