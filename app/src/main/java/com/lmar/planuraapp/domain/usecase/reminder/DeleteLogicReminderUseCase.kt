package com.lmar.planuraapp.domain.usecase.reminder

import com.lmar.planuraapp.domain.repository.IReminderRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeleteLogicReminderUseCase(
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
                repository.deleteLogicReminder(reminderId)
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}