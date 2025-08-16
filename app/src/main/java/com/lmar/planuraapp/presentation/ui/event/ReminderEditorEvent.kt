package com.lmar.planuraapp.presentation.ui.event

import com.lmar.planuraapp.domain.model.ReminderType
import com.lmar.planuraapp.presentation.common.components.SnackbarType

sealed class ReminderEditorEvent {
    data class ShowMessage(val message: String, val type: SnackbarType): ReminderEditorEvent()

    data class SetContent(val content: String): ReminderEditorEvent()
    data class SetDate(val date: Long): ReminderEditorEvent()
    data class SetTime(val time: Long): ReminderEditorEvent()
    data class SetReminderType(val reminderType: ReminderType): ReminderEditorEvent()

    object DeleteNote: ReminderEditorEvent()
    object SaveNote: ReminderEditorEvent()

    object ToBack: ReminderEditorEvent()
}