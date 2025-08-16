package com.lmar.planuraapp.domain.repository

import com.lmar.planuraapp.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

interface IReminderRepository {
    fun getReminders(): Flow<List<Reminder>>
    suspend fun getReminderById(id: String): Reminder?
    suspend fun addReminder(reminder: Reminder)
    suspend fun updateReminder(reminder: Reminder)
    suspend fun updateReminderFields(reminder: Reminder)
    suspend fun deleteReminder(reminderId: String)
}