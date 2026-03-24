package com.example.fortune.core.data.repository

import com.example.fortune.core.model.FortuneReq
import com.example.fortune.core.model.FortuneRes

interface FortuneRepository {
    suspend fun fetchDailyFortune(request: FortuneReq): Result<FortuneRes>
}