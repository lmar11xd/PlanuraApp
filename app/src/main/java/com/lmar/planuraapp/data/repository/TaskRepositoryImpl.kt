package com.lmar.planuraapp.data.repository

import android.util.Log
import com.lmar.planuraapp.data.local.dao.TaskDao
import com.lmar.planuraapp.data.mappers.toDomain
import com.lmar.planuraapp.data.mappers.toEntity
import com.lmar.planuraapp.domain.model.Task
import com.lmar.planuraapp.domain.repository.ITaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : ITaskRepository {

    companion object {
        private const val TAG = "TaskRepositoryImpl"
    }

    init {
        Log.d(TAG, "Inicializando TaskRepositoryImpl")
    }

    override fun getTasks(): Flow<List<Task>> {
        return taskDao.getTasks()
            .map { tasks -> tasks.map { it.toDomain() } }
            .catch { e ->
                Log.e(TAG, "Error al obtener las tareas", e)
                emit(emptyList())
            }
    }

    override suspend fun getTaskById(id: String): Task? {
        return try {
            taskDao.getTaskById(id)?.toDomain()
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener la tarea por ID", e)
            null
        }
    }

    override suspend fun addTask(task: Task) {
        try {
            Log.d(TAG, "Agregando tarea: $task")
            taskDao.insertTask(task.toEntity())
        } catch (e: Exception) {
            Log.e(TAG, "Error al agregar la tarea", e)
            throw e
        }
    }

    override suspend fun updateTask(task: Task) {
        try {
            Log.d(TAG, "Actualizando tarea: $task")
            taskDao.updateTask(task.toEntity())
        } catch (e: Exception) {
            Log.e(TAG, "Error al actualizar la tarea", e)
            throw e
        }
    }

    override suspend fun updateTaskFields(task: Task) {
        try {
            Log.d(TAG, "Actualizando campos de la tarea: $task")
            taskDao.updateTaskFields(
                task.id,
                task.description,
                task.isCompleted,
                task.updatedAt
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error al actualizar los campos de la tarea", e)
            throw e
        }
    }

    override suspend fun updateTaskCompleted(
        taskId: String,
        isCompleted: Boolean,
        updatedAt: Long
    ) {
        try {
            Log.d(TAG, "Actualizando completitud de la tarea con ID: $taskId")
            taskDao.updateTaskCompleted(taskId, isCompleted, updatedAt)
        } catch (e: Exception) {
            Log.e(TAG, "Error al actualizar la completitud de la tarea", e)
            throw e
        }
    }

    override suspend fun deleteTask(taskId: String) {
        try {
            Log.d(TAG, "Eliminando tarea con ID: $taskId")
            taskDao.deleteTask(taskId)
        } catch (e: Exception) {
            Log.e(TAG, "Error al eliminar la tarea", e)
            throw e
        }
    }
}