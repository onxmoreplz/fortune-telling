package com.example.fortune.core.model

import kotlinx.serialization.Serializable

@Serializable
data class FortuneReq(
    val targetYear: Int,
    val targetMonth: Int,
    val targetDay: Int,
    val birthYear: Int,
    val birthMonth: Int,
    val birthDay: Int,
    val birthHour: Int, // 생시
    val isLunar: Boolean
)

@Serializable
data class FortuneRes(
    val userName: String,
    val date: String,
    val fortuneText: String,
    val luckScore: Int,
    val avoidFoods: List<String>,
    val avoidActions: List<String>,
    val recommendColors: List<String>,
    val luckyItem: String,
    val luckyNumber: String,
    val luckyDirection: String,
    val luckyMessage: String,
)

@Serializable
data class FortuneDetail(
    val title: String,
    val description: String
)