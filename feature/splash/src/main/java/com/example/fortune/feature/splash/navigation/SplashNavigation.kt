package com.example.fortune.feature.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.fortune.feature.splash.SplashScreen

fun NavGraphBuilder.splashScreen(
    onAuthenticated: () -> Unit,
    onUnauthenticated: () -> Unit
) {
    composable("splash") {
        SplashScreen(
            onAuthenticated = onAuthenticated,
            onUnauthenticated = onUnauthenticated
        )
    }
}