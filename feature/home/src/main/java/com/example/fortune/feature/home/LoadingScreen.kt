package com.example.fortune.feature.home

import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val LoadingBg      = Color(0xFFF5F0EB)
private val LoadingPrimary = Color(0xFFBF8B5E)
private val LoadingSub     = Color(0xFF888888)

@Composable
fun LoadingScreen() {
    val infiniteTransition = rememberInfiniteTransition(label = "loading")

    // 🔮 크기 애니메이션
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    // 텍스트 투명도 애니메이션
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LoadingBg),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 40.dp)
        ) {
            Text(
                text = "🔮",
                fontSize = 72.sp,
                modifier = Modifier.scale(scale)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "오늘의 운세를 읽는 중...",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = LoadingPrimary,
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "잠시만 기다려주세요",
                fontSize = 14.sp,
                color = LoadingSub.copy(alpha = alpha),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EB)
@Composable
private fun LoadingScreenPreview() {
    LoadingScreen()
}