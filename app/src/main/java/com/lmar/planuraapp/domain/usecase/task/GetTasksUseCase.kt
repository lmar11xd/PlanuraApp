package com.lmar.planuraapp.domain.usecase.task

import com.lmar.planuraapp.domain.model.Task
import com.lmar.planuraapp.domain.repository.ITaskRepository
import kotlinx.coroutines.flow.Flow

class GetTasksUseCase(
    private val repository: ITaskRepository
) {
    operator fun invoke(): Flow<List<Task>> {
        return repository.getTasks()
    }
}