package com.example.fortune.core.data.repository

interface AuthRepository {
    suspend fun checkAuthStatus(): Boolean
    suspend fun login(id: String, password: String)
}