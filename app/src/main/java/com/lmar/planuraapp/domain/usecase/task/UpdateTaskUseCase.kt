package com.lmar.planuraapp.domain.usecase.task

import com.lmar.planuraapp.domain.model.Task
import com.lmar.planuraapp.domain.repository.ITaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateTaskUseCase(
    private val repository: ITaskRepository,
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {
    operator fun invoke(
        task: Task,
        onSuccess: () -> Unit = {},
        onError: (Exception) -> Unit = {}
    ) {
        scope.launch {
            try {
                repository.updateTaskFields(task)
                onSuccess()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}