package com.example.fortune.feature.home.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.fortune.feature.home.HomeScreen
import com.example.fortune.feature.home.HomeUiState
import com.example.fortune.feature.home.HomeViewModel
import com.example.fortune.feature.home.LoadingScreen
import com.example.fortune.feature.home.UserInfoScreen
import kotlinx.coroutines.delay

fun NavGraphBuilder.homeScreen(
    onReEnter: () -> Unit
) {
    composable("home") {
        val viewModel: HomeViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        when (uiState) {
            is HomeUiState.Loading -> {
                Box(
                    Modifier.fillMaxWidth(), Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is HomeUiState.Error -> {
                Box(Modifier.fillMaxSize(), Alignment.Center) {
                    TextButton(onClick = viewModel::fetchFortune) {
                        Text("오류가 발생했습니다.\n(${(uiState as HomeUiState.Error).message})")
                    }
                }
            }

            is HomeUiState.Success -> {
                HomeScreen(
                    fortune = (uiState as HomeUiState.Success).fortune,
                    onReEnter = onReEnter,
                    onRefreshClick = viewModel::fetchFortune
                )
            }
        }
    }
}

fun NavGraphBuilder.userInfoScreen(
    onConfirmClick: (name: String, birthDate: String, birthTime: String?, region: String, isLunar: Boolean) -> Unit
) {
    composable("user_info") {
        UserInfoScreen(onConfirmClick = onConfirmClick)
    }
}

fun NavGraphBuilder.loadingScreen(
    onLoadingComplete: () -> Unit
) {
    composable("loading") {
        LaunchedEffect(Unit) {
            delay(5000L)
            onLoadingComplete()
        }
        LoadingScreen()
    }
}