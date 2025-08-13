package com.lmar.planuraapp.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lmar.planuraapp.presentation.common.components.AppBar
import com.lmar.planuraapp.presentation.common.components.Snackbar
import com.lmar.planuraapp.presentation.common.components.SnackbarManager
import com.lmar.planuraapp.presentation.common.components.SnackbarType
import com.lmar.planuraapp.presentation.common.event.UiEvent
import com.lmar.planuraapp.presentation.common.ui.auth.LoginScreenContainer
import com.lmar.planuraapp.presentation.common.ui.auth.ProfileScreenContainer
import com.lmar.planuraapp.presentation.common.ui.auth.ResetPasswordScreenContainer
import com.lmar.planuraapp.presentation.common.ui.auth.SignUpScreenContainer
import com.lmar.planuraapp.presentation.ui.screen.HomeScreenContainer
import com.lmar.planuraapp.presentation.ui.screen.NoteEditorScreenContainer
import com.lmar.planuraapp.presentation.ui.screen.NotesScreenContainer
import com.lmar.planuraapp.presentation.ui.screen.ReminderEditorScreenContainer
import com.lmar.planuraapp.presentation.ui.screen.RemindersScreenContainer
import com.lmar.planuraapp.presentation.ui.screen.TaskEditorScreenContainer
import com.lmar.planuraapp.presentation.ui.screen.TasksScreenContainer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarType by remember { mutableStateOf(SnackbarType.INFO) }

    // Ejemplo simple de “badge” para Recordatorios
    var reminderCount by remember { mutableIntStateOf(2) } // podría venir de un ViewModel

    LaunchedEffect(Unit) {
        // Escuchar mensajes del manager
        SnackbarManager.snackbarFlow.collect { event ->
            snackbarType = event.type
            snackbarHostState.showSnackbar(
                message = event.message,
                withDismissAction = true
            )
        }
    }

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val isMainScreen = currentRoute in AppRoutes.bottomDestinations.map { it.route.route }

    Scaffold(
        topBar = {
            if (isMainScreen) {
                AppBar(
                    withBackButton = false,
                    actions = {
                        IconButton(
                            onClick = { },
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Profile",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                )

            }
        },
        bottomBar = {
            if (isMainScreen) {
                BottomBar(
                    navController = navController,
                    destinations = AppRoutes.bottomDestinations,
                    reminderCount = reminderCount
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    Snackbar(snackbarData = data, type = snackbarType)
                }
            )
        }
    ) {
        NavHost(navController, startDestination = AppRoutes.NoteScreen.route) {
            composable(route = AppRoutes.LoginScreen.route) {
                LoginScreenContainer(navController)
            }

            composable(route = AppRoutes.SignUpScreen.route) {
                SignUpScreenContainer(navController)
            }

            composable(route = AppRoutes.ProfileScreen.route) {
                ProfileScreenContainer(navController)
            }

            composable(route = AppRoutes.ResetPasswordScreen.route) {
                ResetPasswordScreenContainer(navController)
            }

            composable(AppRoutes.HomeScreen.route) {
                HomeScreenContainer(navController)
            }

            composable(AppRoutes.NoteScreen.route) {
                NotesScreenContainer(navController)
            }

            composable(AppRoutes.TaskScreen.route) {
                TasksScreenContainer(navController)
            }

            composable(AppRoutes.ReminderScreen.route) {
                RemindersScreenContainer(navController)
            }

            // Editores

            composable(AppRoutes.NoteEditor.route) {
                NoteEditorScreenContainer(navController)
            }

            composable(AppRoutes.TaskEditor.route) {
                TaskEditorScreenContainer(navController)
            }

            composable(AppRoutes.ReminderEditor.route) {
                ReminderEditorScreenContainer(navController)
            }

        }
    }

}

fun NavController.handleUiEvents(
    scope: CoroutineScope,
    uiEventFlow: Flow<UiEvent>
) {
    scope.launch {
        uiEventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    SnackbarManager.showMessage(
                        message = event.snackbarEvent.message,
                        type = event.snackbarEvent.type
                    )
                }

                UiEvent.ToBack -> popBackStack()
                UiEvent.ToHome -> navigate(AppRoutes.HomeScreen.route)

                UiEvent.ToSignUp -> navigate(AppRoutes.SignUpScreen.route)
                UiEvent.ToLogin -> navigate(AppRoutes.LoginScreen.route)
                UiEvent.ToProfile -> navigate(AppRoutes.ProfileScreen.route)
                UiEvent.ToResetPassword -> navigate(AppRoutes.ResetPasswordScreen.route)

                UiEvent.ToNote -> navigate(AppRoutes.NoteScreen.route)
                UiEvent.ToTask -> navigate(AppRoutes.TaskScreen.route)
                UiEvent.ToReminder -> navigate(AppRoutes.ReminderScreen.route)

                UiEvent.ToNoteEditor -> navigate(AppRoutes.NoteEditor.route)
                UiEvent.ToTaskEditor -> navigate(AppRoutes.TaskEditor.route)
                UiEvent.ToReminderEditor -> navigate(AppRoutes.ReminderEditor.route)

                is UiEvent.ToRoute -> navigate(event.route)
            }
        }
    }
}