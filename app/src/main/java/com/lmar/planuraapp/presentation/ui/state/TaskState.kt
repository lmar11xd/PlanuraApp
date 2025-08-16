package com.lmar.planuraapp.presentation.ui.state

import com.lmar.planuraapp.domain.model.Task

data class TaskState (
    val tasks: List<Task> = emptyList(),
    val tasksCompleted: List<Task> = emptyList(),

    val isLoading: Boolean = false,

    val isTaskEditorVisible: Boolean = false,
    val currentTask: Task? = null,
    val isCurrentTaskModified: Boolean = false
)