package com.lmar.planuraapp.domain.usecase.task

import com.lmar.planuraapp.domain.model.Task
import com.lmar.planuraapp.domain.repository.ITaskRepository

class GetTaskByIdUseCase(
    private val repository: ITaskRepository
) {
    suspend operator fun invoke(taskId: String): Task? {
        return try {
            repository.getTaskById(taskId)
        } catch (_: Exception) {
            null
        }
    }
}