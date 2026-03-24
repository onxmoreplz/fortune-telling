package com.example.fortune.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.fortune.feature.home.navigation.homeScreen
import com.example.fortune.feature.home.navigation.loadingScreen
import com.example.fortune.feature.home.navigation.userInfoScreen
import com.example.fortune.feature.login.navigation.loginScreen
import com.example.fortune.feature.splash.navigation.splashScreen

@Composable
fun FortuneNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = FortuneRoute.USER_INFO,
    ) {
        splashScreen(
            onAuthenticated = {
                navController.navigate(FortuneRoute.USER_INFO) {
                    popUpTo(FortuneRoute.SPLASH) { inclusive = true }
                }
            },
            onUnauthenticated = {
                navController.navigate(FortuneRoute.LOGIN) {
                    popUpTo(FortuneRoute.SPLASH) { inclusive = true }
                }
            }
        )
        loginScreen(
            onLoginSuccess = {
                navController.navigate(FortuneRoute.USER_INFO) {
                    popUpTo(FortuneRoute.LOGIN) { inclusive = true }
                }
            }
        )
        userInfoScreen(
            onConfirmClick = { name, birthDate, birthTime, region, isLunar ->
                navController.navigate(FortuneRoute.LOADING) {
                    popUpTo(FortuneRoute.USER_INFO) { inclusive = true }
                }
            }
        )
        loadingScreen(
            onLoadingComplete = {
                navController.navigate(FortuneRoute.HOME) {
                    popUpTo(FortuneRoute.LOADING) { inclusive = true }
                }
            }
        )
        homeScreen(
            onReEnter = {
                navController.navigate(FortuneRoute.USER_INFO) {
                    popUpTo(FortuneRoute.HOME) { inclusive = true }
                }
            }
        )
    }
}