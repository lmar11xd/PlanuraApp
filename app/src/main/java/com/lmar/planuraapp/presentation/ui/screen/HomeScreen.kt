package com.lmar.planuraapp.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lmar.planuraapp.presentation.common.state.AuthState
import com.lmar.planuraapp.presentation.common.viewmodel.auth.AuthViewModel
import com.lmar.planuraapp.presentation.navigation.handleUiEvents
import com.lmar.planuraapp.presentation.ui.event.HomeEvent
import com.lmar.planuraapp.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreenContainer(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val authState by authViewModel.authState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        navController.handleUiEvents(
            scope = coroutineScope,
            uiEventFlow = homeViewModel.eventFlow,
        )
    }

    HomeScreen(
        authState = authState,
        onEvent = homeViewModel::onEvent
    )
}

@Composable
fun HomeScreen(
    authState: AuthState,
    onEvent: (HomeEvent) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text("Home Screen")
        }
    }
}