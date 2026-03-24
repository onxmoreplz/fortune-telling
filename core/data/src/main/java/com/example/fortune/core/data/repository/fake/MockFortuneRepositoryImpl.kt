package com.example.fortune.core.data.repository.fake

import com.example.fortune.core.data.repository.FortuneRepository
import com.example.fortune.core.model.FortuneReq
import com.example.fortune.core.model.FortuneRes
import javax.inject.Inject

class MockFortuneRepositoryImpl @Inject constructor() : FortuneRepository {
    override suspend fun fetchDailyFortune(request: FortuneReq): Result<FortuneRes> {
        return Result.success(
            FortuneRes(
                userName = "함도영",
                date = "2026년 2월 26일 목요일",
                fortuneText = "오늘은 온화한 나무의 기운이 땅에 뿌리를 내리는 형국으로, " +
                        "성실하게 노력해온 일들이 조금씩 빛을 발하기 시작하는 날입니다. " +
                        "대인관계에서 신뢰를 얻고 평온한 하루를 보내게 될 운세입니다.",
                luckScore = 88,
                avoidFoods = listOf("너무 매운 음식", "탄산음료", "차가운 얼음물"),
                avoidActions = listOf(
                    "충동적인 대량 구매",
                    "다른 사람의 험담에 가담하기",
                    "중요한 결정을 서두르는 것",
                ),
                recommendColors = listOf("하늘색", "연두색", "아이보리"),
                luckyItem = "가죽 지갑",
                luckyNumber = "7",
                luckyDirection = "남동쪽",
                luckyMessage = "\"주변 사람들에게 따뜻한 말 한마디를 건네보세요. " +
                        "뿌린 친절이 배가 되어 당신에게 돌아올 것이니, " +
                        "오늘 하루는 여유로운 마음으로 타인을 대하는 것이 좋습니다.\"",
            )
        )
    }
}