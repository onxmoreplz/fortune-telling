package com.example.fortune

import android.os.Bundle
import androidx.activity.ComponentActivity // 이 임포트가 필요합니다.
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import com.example.fortune.navigation.FortuneNavHost
import dagger.hilt.android.AndroidEntryPoint

/**
 * 앱의 유일한 Activity (Single Activity)
 * Fragment 전환은 Navigation Component 가 담당합니다.
 *
 * 화면 흐름:
 * SplashFragment → (인증 O) HomeFragment
 *                → (인증 X) LoginFragment → HomeFragment
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()  // 상단 StatusBar, 하단 NavigationBar 노출

        setContent {
            MaterialTheme {
                FortuneNavHost()
            }
        }
    }
}