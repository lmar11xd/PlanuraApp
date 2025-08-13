package com.lmar.planuraapp.presentation.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.lmar.planuraapp.presentation.navigation.AppRoutes
import com.lmar.planuraapp.presentation.ui.component.ScreenScaffold

@Composable
fun RemindersScreenContainer(
    navController: NavHostController
) {
    RemindersScreen(
        onAddReminder = { navController.navigate(AppRoutes.ReminderEditor.route) }
    )
}

@Composable
private fun RemindersScreen(
    onAddReminder: () -> Unit = {}
) {
    ScreenScaffold(
        title = "Recordatorios",
        withFAB = true,
        onFABClick = onAddReminder
    ) {
        Text("Tus recordatorios 🔔")
    }
}