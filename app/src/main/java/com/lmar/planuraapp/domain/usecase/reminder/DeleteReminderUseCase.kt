package com.lmar.planuraapp.domain.usecase.reminder

import com.lmar.planuraapp.domain.model.Reminder
import com.lmar.planuraapp.domain.repository.IReminderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeleteReminderUseCase (
    private val repository: IReminderRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {
    operator fun invoke(
        reminderId: String,
        onSuccess: () -> Unit = {},
        onError: (Exception) -> Unit = {}
    ) {
        scope.launch {
            try {
                repository.deleteReminder(reminderId)
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}