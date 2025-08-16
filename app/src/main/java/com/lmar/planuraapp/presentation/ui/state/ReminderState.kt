package com.lmar.planuraapp.presentation.ui.state

import com.lmar.planuraapp.domain.model.Reminder

data class ReminderState (
    val reminders: List<Reminder> = emptyList(),
    val isLoading: Boolean = false,
)