package com.example.fortune.feature.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint

/**
 * 앱 시작 시 로그인 상태를 확인하는 Fragment
 *
 * 인증 O → HomeFragment 이동
 * 인증 X → LoginFragment 이동
 */

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                // 상태에 따라 Fragment 전환
                when (uiState) {
                    SplashUiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color(0xFF0E0B1A)),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color(0xFFD4A843))
                        }
                    }

                    SplashUiState.Authenticated -> {
                        findNavController().navigate(R.id.action_splash_to_home)
                    }

                    SplashUiState.UnAuthenticated -> {
                        findNavController().navigate(R.id.action_splash_to_login)
                    }

                    is SplashUiState.Error -> {
                        // 에러 시에도 Login 으로 이동
                        findNavController().navigate(R.id.action_splash_to_login)
                    }
                }
            }

        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }
}