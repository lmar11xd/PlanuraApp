package com.lmar.planuraapp.domain.usecase.task

import com.lmar.planuraapp.domain.repository.ITaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DeleteLogicTaskUseCase(
    private val repository: ITaskRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {
    operator fun invoke(
        taskId: String,
        onSuccess: () -> Unit = {},
        onError: (Exception) -> Unit = {}
    ) {
        scope.launch {
            try {
                repository.deleteLogicTask(taskId)
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}