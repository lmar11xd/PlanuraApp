package com.lmar.planuraapp.domain.usecase.reminder

import com.lmar.planuraapp.domain.model.Reminder
import com.lmar.planuraapp.domain.repository.IReminderRepository

class GetReminderByIdUseCase(
    private val repository: IReminderRepository
) {
    suspend operator fun invoke(reminderId: String): Reminder? {
        return try {
            repository.getReminderById(reminderId)
        } catch (_: Exception) {
            null
        }
    }
}