package com.example.fortune.feature.home

import androidx.compose.animation.core.EaseOutCubic
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

// ── 디자인 토큰 ───────────────────────────────────────────────────────────────
private val BgDeep        = Color(0xFFF5F0EB)   // 베이지 배경
private val BgCard        = Color(0xFFFFFFFF)   // 카드 흰색
private val BgCardBorder  = Color(0xFFE8E0D8)   // 카드 테두리
private val Gold          = Color(0xFFBF6B5E)   // 로즈/테라코타
private val GoldLight     = Color(0xFFD4897C)   // 밝은 로즈
private val TextPrimary   = Color(0xFF2C2C2C)   // 본문 텍스트
private val TextSecondary = Color(0xFF888888)   // 보조 텍스트
private val RedChip       = Color(0xFFFFF0EE)   // 피할 음식 칩
private val RedChipBorder = Color(0xFFEFB9B3)
private val PurpleChip       = Color(0xFFF0EEF8)   // 피할 행동 칩
private val PurpleChipBorder = Color(0xFFCCC0E8)
private val GreenChip        = Color(0xFFEFF5F0)   // 추천 색상 칩
private val GreenChipBorder  = Color(0xFFB0D4BA)

private val ChipTextRed    = Color(0xFFBF4A3A)
private val ChipTextPurple = Color(0xFF6B4DB8)
private val ChipTextGreen  = Color(0xFF3A7A52)

/**
 * 홈(운세) 화면
 */
@Composable
fun HomeScreen(
    fortune: FortuneUiModel = FortuneUiModel(),
    onReEnter: () -> Unit = {},
    onRefreshClick: () -> Unit = {}
) {
    val infiniteTransition = rememberInfiniteTransition(label = "refresh")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 3000
                0f at 0
                0f at 2700
                360f at 3000
            }
        ),
        label = "rotation",
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BgDeep)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            HomeTopBar(onReEnter = onReEnter)
            GreetingSection(date = fortune.date, userName = fortune.userName)
            Spacer(modifier = Modifier.height(20.dp))
            FortuneCard(fortune = fortune)
            Spacer(modifier = Modifier.height(16.dp))
            AvoidFoodCard(items = fortune.avoidFoods)
            Spacer(modifier = Modifier.height(16.dp))
            AvoidActionCard(items = fortune.avoidActions)
            Spacer(modifier = Modifier.height(16.dp))
            RecommendColorCard(items = fortune.recommendColors)
            Spacer(modifier = Modifier.height(16.dp))
            LuckyInfoRow(
                item = fortune.luckyItem,
                number = fortune.luckyNumber,
                direction = fortune.luckyDirection,
            )
            Spacer(modifier = Modifier.height(16.dp))
            LuckyMessageCard(message = fortune.luckyMessage)
            Spacer(modifier = Modifier.height(24.dp))
            RefreshButton(rotation = rotation, onClick = onRefreshClick)
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// ── TopBar ────────────────────────────────────────────────────────────────────
@Composable
private fun HomeTopBar(onReEnter: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = "🔮", fontSize = 22.sp)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "오늘의 운세",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
        )
        Spacer(modifier = Modifier.weight(1f))
        TextButton(onClick = onReEnter) {
            Text(text = "다시 입력", fontSize = 14.sp, color = TextSecondary)
        }
    }
}

// ── 날짜 / 인사 ───────────────────────────────────────────────────────────────
@Composable
private fun GreetingSection(date: String, userName: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = date, fontSize = 13.sp, color = TextSecondary)
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = "${userName}님, 안녕하세요.",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
        )
    }
}

// ── 오늘의 운세 카드 ──────────────────────────────────────────────────────────
@Composable
private fun FortuneCard(fortune: FortuneUiModel) {
    FortuneBaseCard(modifier = Modifier.padding(horizontal = 16.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "🔮", fontSize = 48.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "오늘의 운세",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Gold,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = fortune.fortuneText,
                fontSize = 14.sp,
                color = TextPrimary,
                lineHeight = 22.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(20.dp))
            LuckScoreBar(score = fortune.luckScore)
        }
    }
}

@Composable
private fun LuckScoreBar(score: Int) {
    val animatedScore by animateFloatAsState(
        targetValue = score / 100f,
        animationSpec = tween(durationMillis = 1000, easing = EaseOutCubic),
        label = "score",
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = "행운 지수", fontSize = 13.sp, color = TextSecondary, modifier = Modifier.width(64.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(BgCardBorder),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = animatedScore)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Brush.horizontalGradient(listOf(Gold, GoldLight))),
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "$score", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Gold)
    }
}

// ── 피하면 좋을 음식 ──────────────────────────────────────────────────────────
@Composable
private fun AvoidFoodCard(items: List<String>) {
    FortuneBaseCard(modifier = Modifier.padding(horizontal = 16.dp)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "🚫  피하면 좋을 음식",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary,
            )
            Spacer(modifier = Modifier.height(12.dp))
            FlowRow(
                items = items,
                chipColor = Color.Transparent,
                chipBorder = RedChipBorder,
                chipTextColor = ChipTextRed,
                prefix = "🚫"
            )
        }
    }
}

// ── 피하면 좋을 행동 ──────────────────────────────────────────────────────────
@Composable
private fun AvoidActionCard(items: List<String>) {
    FortuneBaseCard(modifier = Modifier
        .padding(horizontal = 16.dp)
        .wrapContentHeight()
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
        ) {
            Text(
                text = "⚠️  피하면 좋을 행동",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary,
            )
            Spacer(modifier = Modifier.height(12.dp))
            FlowRow(
                items = items,
                chipColor = Color.Transparent,
                chipBorder = PurpleChipBorder,
                chipTextColor = ChipTextPurple,
                prefix = "⚠️"
            )
        }
    }
}

// ── 추천 옷 색상 ──────────────────────────────────────────────────────────────
@Composable
private fun RecommendColorCard(items: List<String>) {
    FortuneBaseCard(modifier = Modifier.padding(horizontal = 16.dp)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "🎨  추천 옷 색상",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items.forEach { colorName ->
                    ColorChip(colorName = colorName)
                }
            }
        }
    }
}

@Composable
private fun ColorChip(colorName: String) {
    val dotColor = when (colorName) {
        "하늘색" -> Color(0xFF87CEEB)
        "연두색" -> Color(0xFF90EE90)
        "아이보리" -> Color(0xFFFFFFF0)
        "베이지색" -> Color(0xFFF5F0EB)
        "연한 녹색" -> Color(0xFF90EE90)
        "흰색" -> Color(0xFFFFFFFF)
        "검정색" -> Color(0xFF2C2C2C)
        "빨간색" -> Color(0xFFFF6B6B)
        "파란색" -> Color(0xFF6B9BFF)
        "노란색" -> Color(0xFFFFD700)
        "보라색" -> Color(0xFF9B6FD4)
        "분홍색" -> Color(0xFFFFB6C1)
        "주황색" -> Color(0xFFFFB347)
        else -> Color(0xFFE8E0D8)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(GreenChip)
            .border(1.dp, GreenChipBorder, RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(dotColor)
                .border(1.dp, BgCardBorder, RoundedCornerShape(6.dp))
        )
        Text(text = colorName, fontSize = 13.sp, color = ChipTextGreen)
    }
}

// ── 행운 정보 3칸 ─────────────────────────────────────────────────────────────
@Composable
private fun LuckyInfoRow(item: String, number: String, direction: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // 행운 아이템 (전체 너비)
        LuckyInfoCellWide(emoji = "🎁", label = "행운 아이템", value = item)

        // 행운 숫자 + 방향 (반반)
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            LuckyInfoCell(
                emoji = "#",
                label = "행운 숫자",
                value = number,
                modifier = Modifier.weight(1f)
            )
            LuckyInfoCell(
                emoji = "🧭",
                label = "행운 방향",
                value = direction,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun LuckyInfoCellWide(
    emoji: String,
    label: String,
    value: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(BgCard)
            .border(1.dp, BgCardBorder, RoundedCornerShape(14.dp))
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = emoji, fontSize = 22.sp)
        Column {
            Text(text = label, fontSize = 11.sp, color = TextSecondary)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Gold,
            )
        }
    }
}

@Composable
private fun LuckyInfoCell(
    emoji: String,
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(BgCard)
            .border(1.dp, BgCardBorder, RoundedCornerShape(14.dp))
            .padding(vertical = 18.dp, horizontal = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = emoji, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = label, fontSize = 11.sp, color = TextSecondary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Gold,
                textAlign = TextAlign.Center,
            )
        }
    }
}

// ── 행운 메시지 카드 ──────────────────────────────────────────────────────────
@Composable
private fun LuckyMessageCard(message: String) {
    FortuneBaseCard(modifier = Modifier.padding(horizontal = 16.dp)) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "🌠", fontSize = 32.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "\"$message\"",
                fontSize = 14.sp,
                color = TextPrimary,
                lineHeight = 22.sp,
                textAlign = TextAlign.Center,
            )
        }
    }
}

// ── 운세 다시 보기 버튼 ───────────────────────────────────────────────────────
@Composable
private fun RefreshButton(rotation: Float, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(BgCard)
            .border(1.dp, BgCardBorder, RoundedCornerShape(50.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 18.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "↻",
                fontSize = 20.sp,
                color = Gold,
                modifier = Modifier.rotate(rotation),
            )
            Text(
                text = "운세 다시 보기",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Gold,
            )
        }
    }
}

// ── 공통 카드 컨테이너 ────────────────────────────────────────────────────────
@Composable
private fun FortuneBaseCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(BgCard)
            .border(1.dp, BgCardBorder, RoundedCornerShape(18.dp))
            .padding(20.dp),
        content = content,
    )
}

// ── FlowRow 칩 목록 ───────────────────────────────────────────────────────────
@Composable
private fun FlowRow(
    items: List<String>,
    chipColor: Color,
    chipBorder: Color,
    chipTextColor: Color = TextPrimary,
    prefix: String,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items.chunked(2).forEach { rowItems ->  // 3 → 2로 변경
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                rowItems.forEach { item ->
                    FortuneChip(
                        text = "$prefix $item",
                        bgColor = chipColor,
                        borderColor = chipBorder,
                        textColor = chipTextColor,
                    )
                }
            }
        }
    }
}

@Composable
private fun FortuneChip(
    text: String,
    bgColor: Color,
    borderColor: Color,
    textColor: Color = TextPrimary,
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(20.dp))
            .padding(horizontal = 14.dp, vertical = 8.dp),
    ) {
        Text(text = text, fontSize = 13.sp, color = textColor)
    }
}


// ── Preview ───────────────────────────────────────────────────────────────────
@Preview(showBackground = true, backgroundColor = 0xFFF5F0EB)
@Composable
private fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}