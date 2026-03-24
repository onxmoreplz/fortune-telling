package com.example.fortune.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                when (val state = uiState) {
                    is HomeUiState.Loading -> {
                        Box(Modifier.fillMaxSize(), Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }

                    is HomeUiState.Success -> {
                        HomeScreen(
                            fortune = state.fortune,
                            onReEnter = {
                                findNavController().navigate(R.id.action_home_to_login)
                            },
                            onRefreshClick = viewModel::fetchFortune
                        )
                    }

                    is HomeUiState.Error -> {
                        Box(Modifier.fillMaxSize(), Alignment.Center) {
                            TextButton(onClick = viewModel::fetchFortune) {
                                Text("오류가 발생했습니다.\n(${state.message})")
                            }
                        }
                    }
                }
            }
        }
    }
}