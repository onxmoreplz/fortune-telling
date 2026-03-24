package com.example.fortune.feature.home

sealed interface HomeUiState {

    /** API 호출 중 */
    data object Loading : HomeUiState

    /** 운세 데이터 로드 성공 */
    data class Success(val fortune: FortuneUiModel) : HomeUiState

    /** API 오류 */
    data class Error(val message: String) : HomeUiState
}

/**
 * 운세 화면 표시용 UI 모델
 */
data class FortuneUiModel(
    val userName: String = "함도영",
    val date: String = "2026년 2월 28일 목요일",
    val fortuneText: String = "함도영 님, 오늘은 온화한 나무의 기운이 땅에 뿌리를 내리는 형국으로, 성실하게 노력해온 일들이 조금씩 빛을 발하기 시작하는 날입니다. 대인관계에서 신뢰를 얻고 평온한 하루를 보내게 될 운세입니다.",
    val luckScore: Int = 88,
    val avoidFoods: List<String> = listOf("너무 매운 음식", "탄산음료", "차가운 얼음물"),
    val avoidActions: List<String> = listOf("충동적인 대량 구매", "다른 사람의 험담에 가담하기", "중요한 결정을 서두르는 것"),
    val recommendColors: List<String> = listOf("하늘색", "연두색", "아이보리"),
    val luckyItem: String = "가죽 지갑",
    val luckyNumber: String = "7",
    val luckyDirection: String = "남동쪽",
    val luckyMessage: String = "\"주변 사람들에게 따뜻한 말 한마디를 건네보세요. 뿌린 친절이 배가 되어 당신에게 돌아올 것이니, 오늘 하루는 여유로운 마음으로 타인을 대하는 것이 좋습니다.\"",
)