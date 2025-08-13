package com.lmar.planuraapp.presentation.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.lmar.planuraapp.presentation.navigation.AppRoutes
import com.lmar.planuraapp.presentation.ui.component.ScreenScaffold

@Composable
fun TasksScreenContainer(
    navController: NavHostController
) {
    TasksScreen(
        onAddTask = { navController.navigate(AppRoutes.TaskEditor.route) }
    )
}

@Composable
fun TasksScreen(onAddTask: () -> Unit) {
    ScreenScaffold(
        title = "Tareas",
                withFAB = true,
        onFABClick = onAddTask
    ) {
        Text("Lista de tareas ✅")
    }
}