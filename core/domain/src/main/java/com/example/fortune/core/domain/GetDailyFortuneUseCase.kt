package com.example.fortune.core.domain

import com.example.fortune.core.data.repository.FortuneRepository
import com.example.fortune.core.model.FortuneReq
import com.example.fortune.core.model.FortuneRes
import javax.inject.Inject

class GetDailyFortuneUseCase @Inject constructor(
    private val fortuneRepository: FortuneRepository,
) {
    suspend operator fun invoke(request: FortuneReq): Result<FortuneRes> {
        return fortuneRepository.fetchDailyFortune(request)
    }
}