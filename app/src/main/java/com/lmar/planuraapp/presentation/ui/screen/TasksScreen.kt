package com.lmar.planuraapp.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lmar.planuraapp.core.ui.theme.PlanuraAppTheme
import com.lmar.planuraapp.domain.enums.NoteColorEnum
import com.lmar.planuraapp.domain.model.Task
import com.lmar.planuraapp.presentation.common.components.Loading
import com.lmar.planuraapp.presentation.navigation.handleUiEvents
import com.lmar.planuraapp.presentation.ui.component.ScreenScaffold
import com.lmar.planuraapp.presentation.ui.component.TaskEditorForm
import com.lmar.planuraapp.presentation.ui.event.TaskEvent
import com.lmar.planuraapp.presentation.ui.state.TaskState
import com.lmar.planuraapp.presentation.viewmodel.TaskViewModel

@Composable
fun TasksScreenContainer(
    navController: NavHostController,
    tasksViewModel: TaskViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    val taskState by tasksViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        navController.handleUiEvents(
            scope = coroutineScope,
            uiEventFlow = tasksViewModel.eventFlow,
        )
    }

    TasksScreen(
        taskState = taskState,
        onEvent = tasksViewModel::onEvent,
    )
}

@Composable
fun TasksScreen(
    taskState: TaskState = TaskState(),
    onEvent: (TaskEvent) -> Unit = {}
) {
    ScreenScaffold(
        title = "Tareas",
        withFAB = true,
        onFABClick = { onEvent(TaskEvent.NewTask) }
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(taskState.tasks) { task ->
                TaskCard(
                    task = task,
                    onSelected = { onEvent(TaskEvent.UpdateTask(task)) },
                    onCompletedChange = { taskId, isCompleted ->
                        onEvent(TaskEvent.SetCompleted(taskId, isCompleted))
                    }
                )
            }
        }

        if (taskState.tasksCompleted.isNotEmpty()) {
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text("Completadas", color = Color.Gray, style = MaterialTheme.typography.titleSmall)
            }

            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(taskState.tasksCompleted) { task ->
                    TaskCard(
                        task = task,
                        onCompletedChange = { taskId, isCompleted ->
                            onEvent(TaskEvent.SetCompleted(taskId, isCompleted))
                        }
                    )
                }
            }
        }

        if (taskState.currentTask != null) {
            TaskEditorForm(
                show = taskState.isTaskEditorVisible,
                task = taskState.currentTask,
                onDismiss = { onEvent(TaskEvent.HideTaskEditor) },
                onSave = { onEvent(TaskEvent.SetDescription(it)) }
            )

        }

        if (taskState.isLoading) {
            Loading()
        }
    }
}

@Composable
fun TaskCard(
    task: Task,
    onSelected: () -> Unit = {},
    onCompletedChange: (String, Boolean) -> Unit
) {
    val color = if (task.isCompleted) {
        Color.LightGray
    } else {
        Color.DarkGray
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(NoteColorEnum.DEFAULT.container),
                shape = MaterialTheme.shapes.small
            )
            .clickable { onSelected() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = { onCompletedChange(task.id, it) },
            colors = CheckboxDefaults.colors(
                checkedColor = Color.Green,
                uncheckedColor = Color.Gray
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(task.description, color = color, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun TasksScreenPreview() {
    PlanuraAppTheme {
        val tasks = listOf(
            Task("1", "Task 1", false, 0, 0),
            Task("2", "Task 2", false, 0, 0),
            Task("3", "Task 3", false, 0, 0)
        )

        val tasksCompleted = listOf(
            Task("4", "Task 4", true, 0, 0),
            Task("5", "Task 5", true, 0, 0)
        )

        TasksScreen(
            taskState = TaskState(
                tasks = tasks,
                tasksCompleted = tasksCompleted,
                currentTask = tasks[0],
                isTaskEditorVisible = false
            )
        )
    }
}