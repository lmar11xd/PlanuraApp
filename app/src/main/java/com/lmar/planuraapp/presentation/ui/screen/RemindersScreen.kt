package com.lmar.planuraapp.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lmar.planuraapp.presentation.common.components.Loading
import com.lmar.planuraapp.presentation.navigation.handleUiEvents
import com.lmar.planuraapp.presentation.ui.component.ScreenScaffold
import com.lmar.planuraapp.presentation.ui.event.ReminderEvent
import com.lmar.planuraapp.presentation.ui.state.ReminderState
import com.lmar.planuraapp.presentation.viewmodel.ReminderViewModel

@Composable
fun RemindersScreenContainer(
    navController: NavHostController,
    reminderViewModel: ReminderViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    val reminderState by reminderViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        navController.handleUiEvents(
            scope = coroutineScope,
            uiEventFlow = reminderViewModel.eventFlow,
        )
    }

    RemindersScreen(
        reminderState = reminderState,
        onEvent = reminderViewModel::onEvent
    )
}

@Composable
private fun RemindersScreen(
    reminderState: ReminderState = ReminderState(),
    onEvent: (ReminderEvent) -> Unit = {}
) {
    ScreenScaffold(
        title = "Recordatorios",
        withFAB = true,
        onFABClick = { onEvent(ReminderEvent.ToEditor("0")) }
    ) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(reminderState.reminders) { reminder ->
                Text(reminder.description)
            }
        }

        if (reminderState.isLoading) {
            Loading()
        }
    }
}