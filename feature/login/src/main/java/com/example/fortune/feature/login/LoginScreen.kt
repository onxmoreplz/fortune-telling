package com.example.fortune.feature.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ── 디자인 토큰 (이미지 기반 추출) ─────────────────────────────────────────
private val BackgroundColor = Color(0xFFF5F0EB)  // 따뜻한 베이지
private val FieldBackground = Color(0xFFEDE8E1)  // 입력 필드 배경
private val PrimaryRose = Color(0xFFBF6B5E)  // 로그인 버튼 로즈/테라코타
private val PrimaryRoseLight = Color(0xFFD4897C)  // 링크 텍스트
private val LabelGray = Color(0xFF888888)  // 레이블, 힌트 텍스트
private val TextDark = Color(0xFF2C2C2C)  // 본문

/**
 * 로그인 화면
 *
 * @param formState       폼 입력값 상태
 * @param uiState         로그인 처리 상태
 * @param onIdChanged     아이디 변경 이벤트
 * @param onPasswordChanged 비밀번호 변경 이벤트
 * @param onLoginClicked  로그인 버튼 클릭 이벤트
 * @param onSignUpClicked 회원가입 클릭 이벤트
 * @param onErrorConsumed 에러 스낵바 닫기 이벤트
 */
@Composable
fun LoginScreen(
    formState: LoginFormState,
    uiState: LoginUiState,
    onIdChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClicked: () -> Unit,
    onSignUpClicked: () -> Unit = {},
    onErrorConsumed: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(uiState) {
        if (uiState is LoginUiState.Error) {
            snackbarHostState.showSnackbar(
                message = uiState.message,
                duration = SnackbarDuration.Short
            )
            onErrorConsumed()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = BackgroundColor,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 28.dp)
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // ── 상단 안내 문구 ─────────────────────────────────────
            Text(
                text = "로그인하고 오늘의 기운을 확인하세요",
                fontSize = 14.sp,
                color = LabelGray,
            )

            Spacer(modifier = Modifier.height(36.dp))

            // ── 아이디 ─────────────────────────────────────────────
            FieldLabel("아이디")
            Spacer(modifier = Modifier.height(8.dp))
            LoginTextField(
                value = formState.id,
                onValueChange = onIdChanged,
                placeholder = "아이디",
                isError = formState.idError != null,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
            )

            if (formState.idError != null) {
                ErrorText(formState.idError)
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── 비밀번호 ───────────────────────────────────────────
            FieldLabel("비밀번호")
            Spacer(modifier = Modifier.height(8.dp))
            LoginTextField(
                value = formState.password,
                onValueChange = onPasswordChanged,
                placeholder = "비밀번호",
                isError = formState.passwordError != null,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        onLoginClicked()
                    }
                ),
            )
            if (formState.passwordError != null) {
                ErrorText(formState.passwordError)
            }

            Spacer(modifier = Modifier.height(40.dp))

            // ── 로그인 버튼 ────────────────────────────────────────
            Button(
                onClick = onLoginClicked,
                enabled = uiState !is LoginUiState.Loading,
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryRose,
                    disabledContainerColor = PrimaryRose.copy(alpha = 0.6f),
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            ) {
                if (uiState is LoginUiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        strokeWidth = 2.5.dp,
                        color = Color.White,
                    )
                } else {
                    Text(
                        text = "로그인",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ── 회원가입 링크 ──────────────────────────────────────
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                TextButton(onClick = onSignUpClicked) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(SpanStyle(color = LabelGray, fontSize = 14.sp)) {
                                append("처음이신가요? ")
                            }
                            withStyle(
                                SpanStyle(
                                    color = PrimaryRoseLight,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp,
                                )
                            ) {
                                append("회원가입")
                            }
                        }
                    )
                }
            }
        }
    }
}


// ── 재사용 컴포넌트 ────────────────────────────────────────────────────────

@Composable
fun ErrorText(message: String) {
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = message,
        color = MaterialTheme.colorScheme.error,
        fontSize = 12.sp,
        modifier = Modifier.padding(start = 4.dp),
    )
}

@Composable
fun LoginTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isError: Boolean = false,
    visualTransformation: androidx.compose.ui.text.input.VisualTransformation =
        androidx.compose.ui.text.input.VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(text = placeholder, color = LabelGray, fontSize = 15.sp)
        },
        isError = isError,
        singleLine = true,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = FieldBackground,
            unfocusedContainerColor = FieldBackground,
            errorContainerColor = FieldBackground,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedTextColor = TextDark,
            unfocusedTextColor = TextDark,
            cursorColor = PrimaryRose,
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
fun FieldLabel(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = TextDark,
    )
}


// ── Preview ───────────────────────────────────────────────────────────────
@Preview(showBackground = true, backgroundColor = 0xFFF5F0EB)
@Composable
private fun LoginScreenPreview() {
    MaterialTheme {
        LoginScreen(
            formState = LoginFormState(),
            uiState = LoginUiState.Idle,
            onIdChanged = {},
            onPasswordChanged = {},
            onLoginClicked = {},
            onSignUpClicked = {},
            onErrorConsumed = {},
        )
    }
}