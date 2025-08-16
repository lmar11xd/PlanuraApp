package com.lmar.planuraapp.domain.usecase.reminder

import com.lmar.planuraapp.domain.model.Reminder
import com.lmar.planuraapp.domain.repository.IReminderRepository
import kotlinx.coroutines.flow.Flow

class GetRemindersUseCase (
    private val repository: IReminderRepository
) {
    operator fun invoke(): Flow<List<Reminder>> {
        return repository.getReminders()
    }
}