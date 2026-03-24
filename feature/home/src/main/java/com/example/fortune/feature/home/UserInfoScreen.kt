package com.example.fortune.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ── 디자인 토큰 ───────────────────────────────────────────────────────────────
private val BgColor = Color(0xFFF5F0EB)
private val CardColor = Color(0xFFFFFFFF)
private val CardBorder = Color(0xFFE8E0D8)
private val FieldBg = Color(0xFFF5F0EB)
private val PrimaryColor = Color(0xFFBF8B5E)
private val TextDark = Color(0xFF6B6260)
private val TextHint = Color(0xFFBBB0A8)
private val TextSub = Color(0xFF888888)

// ── 지역 데이터 ───────────────────────────────────────────────────────────────
private val regionMap = mapOf(
    "서울특별시" to listOf("전체"),
    "부산광역시" to listOf("전체"),
    "대구광역시" to listOf("전체"),
    "인천광역시" to listOf("전체"),
    "광주광역시" to listOf("전체"),
    "대전광역시" to listOf("전체"),
    "울산광역시" to listOf("전체"),
    "세종특별자치시" to listOf("전체"),
    "경기도" to listOf(
        "수원시",
        "성남시",
        "의정부시",
        "안양시",
        "부천시",
        "광명시",
        "평택시",
        "고양시",
        "과천시",
        "구리시",
        "남양주시",
        "오산시",
        "시흥시",
        "군포시",
        "의왕시",
        "하남시",
        "용인시",
        "파주시",
        "이천시",
        "안성시",
        "김포시",
        "화성시",
        "광주시",
        "양주시",
        "포천시",
        "여주시"
    ),
    "강원도" to listOf("춘천시", "원주시", "강릉시", "동해시", "태백시", "속초시", "삼척시"),
    "충청북도" to listOf("청주시", "충주시", "제천시"),
    "충청남도" to listOf("천안시", "공주시", "보령시", "아산시", "서산시", "논산시", "계룡시", "당진시"),
    "전라북도" to listOf("전주시", "군산시", "익산시", "정읍시", "남원시", "김제시"),
    "전라남도" to listOf("목포시", "여수시", "순천시", "나주시", "광양시"),
    "경상북도" to listOf("포항시", "경주시", "김천시", "안동시", "구미시", "영주시", "영천시", "상주시", "문경시", "경산시"),
    "경상남도" to listOf("창원시", "진주시", "통영시", "사천시", "김해시", "밀양시", "거제시", "양산시"),
    "제주특별자치도" to listOf("제주시", "서귀포시"),
)

@Composable
fun UserInfoScreen(
    onConfirmClick: (name: String, birthDate: String, birthTime: String?, region: String, isLunar: Boolean) -> Unit = { _, _, _, _, _ -> }
) {
    var name by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var birthTime by remember { mutableStateOf("") }
    var selectedProvince by remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf("") }
    var isLunar by remember { mutableStateOf(false) }
    var showProvinceDropdown by remember { mutableStateOf(false) }
    var showCityDropdown by remember { mutableStateOf(false) }

    val cities = if (selectedProvince.isNotEmpty()) regionMap[selectedProvince]
        ?: emptyList() else emptyList()
    val region =
        if (selectedCity.isNotEmpty() && selectedCity != "전체") "$selectedProvince $selectedCity"
        else selectedProvince

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BgColor)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(64.dp))

        // ── 타이틀 ───────────────────────────────────────────────
        Text(
            text = "오늘의 운세",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = PrimaryColor,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "당신의 하루를 밝혀줄 운세를 확인하세요",
            fontSize = 14.sp,
            color = TextSub,
        )

        Spacer(modifier = Modifier.height(40.dp))

        // ── 입력 카드 ────────────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(CardColor)
                .border(1.dp, CardBorder, RoundedCornerShape(20.dp))
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // 이름
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "이름",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextDark
                )
                UserInfoTextField(
                    value = name,
                    onValueChange = { name = it },
                    placeholder = "홍길동",
                )
            }

            // 생년월일
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "생년월일",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextDark
                )
                UserInfoTextField(
                    value = birthDate,
                    onValueChange = { input ->
                        // 숫자만 허용, 자동 하이픈 삽입
                        val digits = input.filter { it.isDigit() }.take(8)
                        birthDate = when {
                            digits.length >= 7 -> "${digits.substring(0, 4)}-${
                                digits.substring(
                                    4,
                                    6
                                )
                            }-${digits.substring(6)}"

                            digits.length >= 5 -> "${digits.substring(0, 4)}-${digits.substring(4)}"
                            else -> digits
                        }
                    },
                    placeholder = "2000-01-01",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
            }

            // 태어난 시간 (선택)
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "태어난 시간 (선택)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextDark
                )
                UserInfoTextField(
                    value = birthTime,
                    onValueChange = { input ->
                        // 숫자만 허용, 자동 콜론 삽입
                        val digits = input.filter { it.isDigit() }.take(4)
                        birthTime = when {
                            digits.length >= 3 -> "${digits.substring(0, 2)}:${digits.substring(2)}"
                            else -> digits
                        }
                    },
                    placeholder = "14:30",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
            }

            // 태어난 지역 - 2단계 드롭다운
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "태어난 지역",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextDark
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // 도/광역시
                    Box(modifier = Modifier.weight(1f)) {
                        RegionDropdownField(
                            value = selectedProvince,
                            placeholder = "도/광역시",
                            isExpanded = showProvinceDropdown,
                            onClick = { showProvinceDropdown = true }
                        )
                        if (showProvinceDropdown) {
                            RegionDropdownList(
                                items = regionMap.keys.toList(),
                                selectedItem = selectedProvince,
                                onItemSelected = {
                                    selectedProvince = it
                                    selectedCity = ""
                                    showProvinceDropdown = false
                                },
                                onDismiss = { showProvinceDropdown = false }
                            )
                        }
                    }

                    // 시 Box 전체 교체
                    Box(modifier = Modifier.weight(1f)) {
                        RegionDropdownField(
                            value = selectedCity,
                            placeholder = "시",
                            isExpanded = showCityDropdown,
                            onClick = {
                                if (selectedProvince.isNotEmpty() && cities.size > 1) {
                                    showCityDropdown = true
                                }
                            },
                            enabled = selectedProvince.isNotEmpty() && cities.size > 1
                        )
                        if (showCityDropdown) {
                            RegionDropdownList(
                                items = cities,
                                selectedItem = selectedCity,
                                onItemSelected = {
                                    selectedCity = it
                                    showCityDropdown = false
                                },
                                onDismiss = { showCityDropdown = false }
                            )
                        }
                    }
                }
            }

            // 양력 사용 토글
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "양력 사용",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextDark
                )
                Switch(
                    checked = !isLunar,
                    onCheckedChange = { isLunar = !it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = PrimaryColor,
                        uncheckedThumbColor = Color.White,
                        uncheckedTrackColor = CardBorder,
                    )
                )
            }

            // 운세 보기 버튼
            Button(
                onClick = {
                    onConfirmClick(name, birthDate, birthTime.ifEmpty { null }, region, isLunar)
                },
                enabled = name.isNotBlank() && birthDate.isNotBlank() && selectedProvince.isNotEmpty(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryColor,
                    disabledContainerColor = PrimaryColor.copy(alpha = 0.5f),
                ),
            ) {
                Text(
                    text = "운세 보기",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "입력한 정보는 운세 생성에만 사용됩니다", fontSize = 12.sp, color = TextSub)
        Spacer(modifier = Modifier.height(32.dp))
    }
}

// ── 지역 드롭다운 필드 ────────────────────────────────────────────────────────
@Composable
private fun RegionDropdownField(
    value: String,
    placeholder: String,
    isExpanded: Boolean,
    onClick: () -> Unit,
    enabled: Boolean = true,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (enabled) FieldBg else FieldBg.copy(alpha = 0.6f))
            .border(
                1.dp,
                if (isExpanded) PrimaryColor else Color.Transparent,
                RoundedCornerShape(12.dp)
            )
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = value.ifEmpty { placeholder },
            fontSize = 14.sp,
            color = if (value.isEmpty()) TextHint else TextDark,
        )
        Text(text = "∨", fontSize = 12.sp, color = TextSub)
    }
}

// ── 공통 TextField ────────────────────────────────────────────────────────────
@Composable
private fun UserInfoTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholder, color = TextHint, fontSize = 15.sp) },
        readOnly = readOnly,
        singleLine = true,
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = FieldBg,
            unfocusedContainerColor = FieldBg,
            disabledContainerColor = FieldBg,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedTextColor = TextDark,
            unfocusedTextColor = TextDark,
            cursorColor = PrimaryColor,
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun RegionDropdownList(
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    androidx.compose.ui.window.Popup(
        onDismissRequest = onDismiss,
        alignment = Alignment.TopStart,
    ) {
        Column(
            modifier = Modifier
                .width(180.dp)
                .heightIn(max = 200.dp)  // 최대 높이 제한으로 스크롤 유도
                .clip(RoundedCornerShape(12.dp))
                .background(CardColor)
                .border(1.dp, CardBorder, RoundedCornerShape(12.dp))
                .verticalScroll(rememberScrollState())
        ) {
            items.forEachIndexed { index, item ->
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onItemSelected(item) }
                            .background(
                                if (item == selectedItem) PrimaryColor.copy(alpha = 0.08f)
                                else Color.Transparent
                            )
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = item,
                            fontSize = 14.sp,
                            color = if (item == selectedItem) PrimaryColor else TextDark,
                            fontWeight = if (item == selectedItem) FontWeight.SemiBold else FontWeight.Normal,
                        )
                        if (item == selectedItem) {
                            Text(text = "✓", fontSize = 12.sp, color = PrimaryColor)
                        }
                    }
                    // 구분선 (마지막 아이템 제외)
                    if (index < items.size - 1) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(0.5.dp)
                                .background(CardBorder)
                        )
                    }
                }
            }
        }
    }
}

// ── Preview ───────────────────────────────────────────────────────────────────
@Preview(showBackground = true, backgroundColor = 0xFFF5F0EB)
@Composable
private fun UserInfoScreenPreview() {
    UserInfoScreen()
}