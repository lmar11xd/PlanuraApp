package com.lmar.planuraapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmar.planuraapp.core.utils.generateUniqueId
import com.lmar.planuraapp.domain.model.Task
import com.lmar.planuraapp.domain.usecase.task.AddTaskUseCase
import com.lmar.planuraapp.domain.usecase.task.DeleteTaskUseCase
import com.lmar.planuraapp.domain.usecase.task.GetTasksUseCase
import com.lmar.planuraapp.domain.usecase.task.UpdateTaskCompletedUseCase
import com.lmar.planuraapp.domain.usecase.task.UpdateTaskUseCase
import com.lmar.planuraapp.presentation.common.components.SnackbarEvent
import com.lmar.planuraapp.presentation.common.components.SnackbarType
import com.lmar.planuraapp.presentation.common.event.UiEvent
import com.lmar.planuraapp.presentation.common.event.UiEvent.*
import com.lmar.planuraapp.presentation.ui.event.TaskEvent
import com.lmar.planuraapp.presentation.ui.state.TaskState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val updateTaskCompletedUseCase: UpdateTaskCompletedUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(TaskState())
    val state: StateFlow<TaskState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getTasks()
    }

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.ShowMessage -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        ShowSnackbar(
                            SnackbarEvent(event.message, event.type)
                        )
                    )
                }
            }

            TaskEvent.DeleteTask -> {

            }

            is TaskEvent.SaveTask -> {
                saveTask()
            }

            is TaskEvent.SetCompleted -> {
                updateTaskCompleted(event.taskId, event.completed)
            }

            is TaskEvent.SetDescription -> {
                _state.value = _state.value.copy(
                    currentTask = _state.value.currentTask?.copy(description = event.description)
                )

                saveTask()
            }

            TaskEvent.NewTask -> {
                _state.value = _state.value.copy(
                    currentTask = Task("0", "", false, 0, 0),
                    isTaskEditorVisible = true
                )
            }

            is TaskEvent.UpdateTask -> {
                _state.value = _state.value.copy(
                    currentTask = event.task,
                    isTaskEditorVisible = true
                )
            }

            TaskEvent.HideTaskEditor -> {
                _state.value = _state.value.copy(
                    currentTask = null,
                    isTaskEditorVisible = false
                )
            }
        }
    }

    private fun getTasks() {
        viewModelScope.launch {
            getTasksUseCase()
                .onStart { _state.value = _state.value.copy(isLoading = true) }
                .catch { error ->
                    _state.value = _state.value.copy(isLoading = false)
                    _eventFlow.emit(
                        ShowSnackbar(
                            SnackbarEvent(
                                error.message ?: "Error desconocido",
                                SnackbarType.ERROR
                            )
                        )
                    )
                }
                .collect { tasks ->
                    _state.value = _state.value.copy(
                        tasks = tasks.filter { !it.isCompleted },
                        tasksCompleted = tasks.filter { it.isCompleted },
                        isLoading = false
                    )
                }
        }
    }

    private fun saveTask() {
        viewModelScope.launch {
            val currentTask = _state.value.currentTask ?: return@launch

            if (currentTask.description.isEmpty()) return@launch

            if (currentTask.id == "0") {
                val timestamp = System.currentTimeMillis()

                val newTask = currentTask.copy(
                    id = generateUniqueId(),
                    createdAt = timestamp,
                    updatedAt = timestamp
                )

                addTaskUseCase(newTask, onSuccess = { onEvent(TaskEvent.HideTaskEditor) })
            } else {
                updateTaskUseCase(currentTask, onSuccess = { onEvent(TaskEvent.HideTaskEditor) })
            }
        }
    }

    private fun updateTaskCompleted(taskId: String, isCompleted: Boolean) {
        viewModelScope.launch {
            val timestamp = System.currentTimeMillis()
            updateTaskCompletedUseCase(taskId, isCompleted, timestamp)
        }
    }
}