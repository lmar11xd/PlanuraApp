package com.lmar.planuraapp.domain.usecase.task

import com.lmar.planuraapp.domain.repository.ITaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateTaskCompletedUseCase(
    private val repository: ITaskRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {
    operator fun invoke(
        taskId: String,
        isCompleted: Boolean,
        updatedAt: Long,
        onSuccess: () -> Unit = {},
        onError: (Exception) -> Unit = {}
    ) {
        scope.launch {
            try {
                repository.updateTaskCompleted(taskId, isCompleted, updatedAt)
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}