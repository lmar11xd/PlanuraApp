package com.lmar.planuraapp.presentation.common.event

import com.lmar.planuraapp.presentation.common.components.SnackbarEvent

sealed class UiEvent {
    data class ShowSnackbar(val snackbarEvent: SnackbarEvent) : UiEvent()
    data class ToRoute(val route: String) : UiEvent()

    // Navegación para pantallas de autenticación
    object ToLogin : UiEvent()
    object ToSignUp : UiEvent()
    object ToProfile : UiEvent()
    object ToResetPassword : UiEvent()

    // Navegación para pantallas principales
    object ToNote : UiEvent()
    object ToTask : UiEvent()
    object ToReminder : UiEvent()

    // Navegación para pantallas de edición
    data class ToNoteEditor(val noteId: String) : UiEvent()
    data class ToTaskEditor(val taskId: String) : UiEvent()
    data class ToReminderEditor(val reminderId: String) : UiEvent()

    // Navegación entre pantallas
    object ToHome : UiEvent()
    object ToBack : UiEvent()
}