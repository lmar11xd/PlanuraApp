package com.lmar.planuraapp.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.lmar.planuraapp.presentation.common.components.AppBar

@Composable
fun ReminderEditorScreenContainer(navController: NavHostController) {
    ReminderEditorScreen(
        onBack = { navController.popBackStack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReminderEditorScreen(
    onBack: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            AppBar(
                "Editar Recordatorio",
                onBackAction = onBack,
                state = rememberTopAppBarState()
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text("Reminder Editor Screen")
            }
        }
    }
}