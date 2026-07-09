package com.pdmcourse2026.basictemplate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.pdmcourse2026.basictemplate.screens.auth.AuthViewModel
import com.pdmcourse2026.basictemplate.screens.auth.LoginScreen
import com.pdmcourse2026.basictemplate.screens.auth.SplashScreen
import com.pdmcourse2026.basictemplate.navigation.RankeUCA_App

@Composable
fun AppRoot(
    authViewModel: AuthViewModel = viewModel(factory = AuthViewModel.Factory)
) {
    val isLoggedIn by authViewModel.isLoggedIn.collectAsStateWithLifecycle()
    val userName by authViewModel.userName.collectAsStateWithLifecycle()

    when (isLoggedIn) {
        null -> SplashScreen()
        false -> LoginScreen(viewModel = authViewModel)
        true -> RankeUCA_App(userName = userName, onLogout = { authViewModel.logout() })
    }
}
