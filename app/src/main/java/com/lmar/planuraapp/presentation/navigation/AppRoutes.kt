package com.lmar.planuraapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Notifications

sealed class AppRoutes(val route: String, val label: String) {
    // Pantallas de autenticación
    data object LoginScreen : AppRoutes("login", "Login")
    data object SignUpScreen : AppRoutes("signup", "Registrarse")
    data object ProfileScreen : AppRoutes("profile", "Perfil")
    data object ResetPasswordScreen : AppRoutes("reset_password", "Recuperar Contraseña")

    // Pantallas principales
    data object HomeScreen : AppRoutes("home", "Principal")

    // Pantallas de listado
    data object NoteScreen : AppRoutes("note", "Notas")
    data object TaskScreen : AppRoutes("task", "Tareas")
    data object ReminderScreen : AppRoutes("reminder", "Recordatorios")

    // Pantallas de edición
    data object NoteEditor : AppRoutes("note_editor", "Editar Nota")
    data object TaskEditor : AppRoutes("task_editor", "Editar Tarea")
    data object ReminderEditor : AppRoutes("reminder_editor", "Editar Recordatorio")

    companion object {
        // Menú de navegación inferior
        val bottomDestinations = listOf(
            BottomItem(NoteScreen, Icons.Rounded.Description),
            BottomItem(TaskScreen, Icons.Rounded.CheckCircle),
            BottomItem(ReminderScreen, Icons.Rounded.Notifications),
            BottomItem(ProfileScreen, Icons.Rounded.AccountCircle),
        )
    }

    fun withArgs(vararg args: String): String =
        route + args.joinToString(separator = "&", prefix = "?") { "$it={$it}" }

    fun withParam(key: String, value: String): String =
        "$route?$key=$value"
}