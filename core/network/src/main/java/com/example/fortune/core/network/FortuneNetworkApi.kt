package com.example.fortune.core.network

import com.example.fortune.core.model.FortuneRes
import retrofit2.http.GET
import retrofit2.http.Query

interface FortuneNetworkApi {

    @GET("api/v1/day")
    suspend fun getDailyFortune(
        @Query("targetYear") targetYear: Int,
        @Query("targetMonth") targetMonth: Int,
        @Query("targetDay") targetDay: Int,
        @Query("birthYear") birthYear: Int,
        @Query("birthMonth") birthMonth: Int,
        @Query("birthDay") birthDay: Int,
        @Query("birthHour") birthHour: Int,
        @Query("isLunar") isLunar: Boolean,
        @Query("api-key") apiKey: String = "943156c8f56a4c88fad1ba1379e3bf00" // 여기에 API Key 설정
    ): FortuneRes
}