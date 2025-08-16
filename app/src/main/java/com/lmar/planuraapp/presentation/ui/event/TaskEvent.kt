package com.lmar.planuraapp.presentation.ui.event

import com.lmar.planuraapp.domain.model.Task
import com.lmar.planuraapp.presentation.common.components.SnackbarType

sealed class TaskEvent {
    data class ShowMessage(val message: String, val type: SnackbarType): TaskEvent()

    data class SetDescription(val description: String): TaskEvent()
    data class SetCompleted(val taskId: String, val completed: Boolean): TaskEvent()

    object HideTaskEditor: TaskEvent()
    object NewTask: TaskEvent()
    data class UpdateTask(val task: Task): TaskEvent()
    object DeleteTask: TaskEvent()
    object SaveTask: TaskEvent()
}