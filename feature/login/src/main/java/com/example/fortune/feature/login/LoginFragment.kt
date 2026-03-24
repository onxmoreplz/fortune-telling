package com.example.fortune.feature.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = ComposeView(requireContext()).apply {
        setContent {
            MaterialTheme {
                val formState by viewModel.formState.collectAsStateWithLifecycle()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                // 로그인 성공 -> HomeFragment 이동
                if (uiState is LoginUiState.Success) {
                    findNavController().navigate(R.id.action_login_to_home)
                    return@MaterialTheme
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
    }

}