package com.example.fortune.feature.home

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import com.example.fortune.core.model.FortuneReq
import com.example.fortune.core.model.FortuneRes
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fortune.core.domain.GetDailyFortuneUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getDailyFortuneUseCase: GetDailyFortuneUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        fetchFortune()
    }

    fun fetchFortune() {
        viewModelScope.launch {
            _uiState.value = HomeUiState.Loading

            // TODO: 실제 사용자 정보는 DataStore 에서 가져와야 함
            val request = FortuneReq(
                targetYear = 2026, targetMonth = 2, targetDay = 26,
                birthYear = 1995, birthMonth = 2, birthDay = 28,
                birthHour = 4,
                isLunar = false,
            )

            getDailyFortuneUseCase(request)
                .onSuccess { res ->
                    _uiState.value = HomeUiState.Success(
                        fortune = FortuneUiModel(
                            userName = res.userName,
                            date = res.date,
                            fortuneText = res.fortuneText,
                            luckScore = res.luckScore,
                            avoidFoods = res.avoidFoods,
                            avoidActions = res.avoidActions,
                            recommendColors = res.recommendColors,
                            luckyItem = res.luckyItem,
                            luckyNumber = res.luckyNumber,
                            luckyDirection = res.luckyDirection,
                            luckyMessage = res.luckyMessage,
                        )
                    )
                }
                .onFailure { error ->
                    _uiState.value = HomeUiState.Error(
                        message = error.message ?: "알 수 없는 오류가 발생했습니다."
                    )
                }
        }
    }

}


sealed interface FortuneUiState {
    object Idle : FortuneUiState
    object Loading : FortuneUiState
    data class Success(val data: FortuneRes) : FortuneUiState
    data class Error(val message: String) : FortuneUiState
}

// ── FortuneRes → FortuneUiModel 매핑 ─────────────────────────────────────────
// (core:model 의 FortuneRes 구조에 맞게 수정 필요)
private fun com.example.fortune.core.model.FortuneRes.toUiModel() = FortuneUiModel(
    userName = userName,
    date = date,
    fortuneText = fortuneText,
    luckScore = luckScore,
    avoidFoods = avoidFoods,
    avoidActions = avoidActions,
    recommendColors = recommendColors,
    luckyItem = luckyItem,
    luckyNumber = luckyNumber,
    luckyDirection = luckyDirection,
    luckyMessage = luckyMessage,
)