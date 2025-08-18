package com.lmar.planuraapp.data.repository

import android.util.Log
import com.lmar.planuraapp.data.local.dao.ReminderDao
import com.lmar.planuraapp.data.mappers.toDomain
import com.lmar.planuraapp.data.mappers.toEntity
import com.lmar.planuraapp.domain.model.Reminder
import com.lmar.planuraapp.domain.repository.IReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val reminderDao: ReminderDao
): IReminderRepository {

    companion object {
        private const val TAG = "ReminderRepositoryImpl"
    }

    init {
        Log.d(TAG, "Inicializando ReminderRepositoryImpl")
    }

    override fun getReminders(): Flow<List<Reminder>> {
        return reminderDao.getReminders()
            .map { reminders -> reminders.map { it.toDomain() } }
            .catch { e ->
                Log.e(TAG, "Error al obtener las recordatorios", e)
                emit(emptyList())
            }
    }

    override suspend fun getReminderById(id: String): Reminder? {
        return try {
            reminderDao.getReminderById(id)?.toDomain()
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener el recordatorio por ID", e)
            null
        }
    }

    override suspend fun addReminder(reminder: Reminder) {
        try {
            Log.d(TAG, "Agregando recordatorio: $reminder")
            reminderDao.insertReminder(reminder.toEntity())
        } catch (e: Exception) {
            Log.e(TAG, "Error al agregar el recordatorio", e)
            throw e
        }
    }

    override suspend fun updateReminder(reminder: Reminder) {
        try {
            Log.d(TAG, "Actualizando recordatorio: $reminder")
            reminderDao.updateReminder(reminder.toEntity())
        } catch (e: Exception) {
            Log.e(TAG, "Error al actualizar el recordatorio", e)
            throw e
        }
    }

    override suspend fun updateReminderFields(reminder: Reminder) {
        try {
            Log.d(TAG, "Actualizando campos del recordatorio: $reminder")
            reminderDao.updateReminderFields(
                reminder.id,
                reminder.description,
                reminder.date,
                reminder.reminderType.name,
                reminder.updatedAt
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error al actualizar los campos del recordatorio", e)
            throw e
        }
    }

    override suspend fun deleteReminder(reminderId: String) {
        try {
            Log.d(TAG, "Eliminando recordatorio con ID: $reminderId")
            reminderDao.deleteReminder(reminderId)
        } catch (e: Exception) {
            Log.e(TAG, "Error al eliminar el recordatorio", e)
            throw e
        }
    }

    override suspend fun deleteLogicReminder(reminderId: String) {
        try {
            Log.d(TAG, "Eliminando lógicamente el recordatorio con ID: $reminderId")
            reminderDao.deleteLogicReminder(reminderId)
        } catch (e: Exception) {
            Log.e(TAG, "Error al eliminar lógicamente el recordatorio", e)
            throw e
        }
    }
}