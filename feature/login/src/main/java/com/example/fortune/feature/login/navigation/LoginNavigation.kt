package com.example.fortune.feature.login.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.fortune.feature.login.LoginScreen
import com.example.fortune.feature.login.LoginUiState
import com.example.fortune.feature.login.LoginViewModel

fun NavGraphBuilder.loginScreen(
    onLoginSuccess: () -> Unit
) {
    composable("login") {
        val viewModel: LoginViewModel = hiltViewModel()
        val formState by viewModel.formState.collectAsStateWithLifecycle()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        LaunchedEffect(uiState) {
            if (uiState is LoginUiState.Success) {
                onLoginSuccess()
            }
        }

        LoginScreen(
            formState = formState,
            uiState = uiState,
            onIdChanged = viewModel::onIdChanged,
            onPasswordChanged = viewModel::onPasswordChanged,
            onLoginClicked = viewModel::onLoginClicked,
            onSignUpClicked = { /* TODO: 회원가입 */ },
            onErrorConsumed = viewModel::onErrorConsumed,
        )
    }
}