package com.lmar.planuraapp.presentation.ui.event

import com.lmar.planuraapp.presentation.common.components.SnackbarType

sealed class ReminderEvent {
    data class ShowMessage(val message: String, val type: SnackbarType): ReminderEvent()
    data class ToEditor(val reminderId: String): ReminderEvent()
}