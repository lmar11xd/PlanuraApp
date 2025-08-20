package com.lmar.planuraapp.presentation.ui.event

import com.lmar.planuraapp.domain.model.ReminderType
import com.lmar.planuraapp.presentation.common.components.SnackbarType

sealed class ReminderEditorEvent {
    data class ShowMessage(val message: String, val type: SnackbarType): ReminderEditorEvent()

    data class SetDescription(val description: String): ReminderEditorEvent()
    data class SetDate(val date: Long): ReminderEditorEvent()
    data class SetHour(val hour: Int): ReminderEditorEvent()
    data class SetMinute(val minute: Int): ReminderEditorEvent()
    data class SetReminderType(val reminderType: ReminderType): ReminderEditorEvent()

    object DeleteReminder: ReminderEditorEvent()
    object SaveReminder: ReminderEditorEvent()

    object ToBack: ReminderEditorEvent()
}