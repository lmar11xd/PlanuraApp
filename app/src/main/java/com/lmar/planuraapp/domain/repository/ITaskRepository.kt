package com.lmar.planuraapp.domain.repository

import com.lmar.planuraapp.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface ITaskRepository {
    fun getTasks(): Flow<List<Task>>
    suspend fun getTaskById(id: String): Task?
    suspend fun addTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun updateTaskFields(task: Task)
    suspend fun updateTaskCompleted(taskId: String, isCompleted: Boolean, updatedAt: Long)
    suspend fun deleteTask(taskId: String)
}