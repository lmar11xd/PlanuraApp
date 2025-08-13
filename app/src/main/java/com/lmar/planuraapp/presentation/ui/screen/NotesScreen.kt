package com.lmar.planuraapp.presentation.ui.screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.lmar.planuraapp.presentation.navigation.AppRoutes
import com.lmar.planuraapp.presentation.ui.component.ScreenScaffold

@Composable
fun NotesScreenContainer(
    navController: NavHostController
) {
    NotesScreen(
        onAddNote = { navController.navigate(AppRoutes.NoteEditor.route) }
    )
}

@Composable
private fun NotesScreen(onAddNote: () -> Unit) {
    ScreenScaffold(
        title = "Notas",
        withFAB = true,
        onFABClick = onAddNote
    ) {
        Text("Aquí van tus notas 📝")
    }
}