package com.example.fortune.core.data.repository

import com.example.fortune.core.model.FortuneReq
import com.example.fortune.core.model.FortuneRes
import com.example.fortune.core.network.FortuneNetworkApi
import javax.inject.Inject

class FortuneRepositoryImpl @Inject constructor(
    private val networkApi: FortuneNetworkApi
): FortuneRepository {
    override suspend fun fetchDailyFortune(request: FortuneReq): Result<FortuneRes> {
        return runCatching {
            networkApi.getDailyFortune(
                targetYear = request.targetYear,
                targetMonth = request.targetMonth,
                targetDay = request.targetDay,
                birthYear = request.birthYear,
                birthMonth = request.birthMonth,
                birthDay = request.birthDay,
                birthHour = request.birthHour,
                isLunar = request.isLunar
            )
        }
    }
}