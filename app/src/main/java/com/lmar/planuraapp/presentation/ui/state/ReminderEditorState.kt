package com.lmar.planuraapp.presentation.ui.state

import com.lmar.planuraapp.domain.model.ReminderType

data class ReminderEditorState (
    val reminderId: String = "0",
    val reminderDescription: String = "",
    val reminderDate: Long = System.currentTimeMillis(),
    val reminderHour: Int = 12,
    val reminderMinute: Int = 0,
    val reminderType: ReminderType = ReminderType.ONE_TIME,
    val reminderLastModified: Long = System.currentTimeMillis(),
    val isReminderModified: Boolean = false
)