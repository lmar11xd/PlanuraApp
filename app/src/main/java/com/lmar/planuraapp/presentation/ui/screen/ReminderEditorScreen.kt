package com.lmar.planuraapp.presentation.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lmar.planuraapp.core.ui.theme.PlanuraAppTheme
import com.lmar.planuraapp.domain.model.ReminderType
import com.lmar.planuraapp.presentation.common.components.AppBar
import com.lmar.planuraapp.presentation.common.components.DropdownOption
import com.lmar.planuraapp.presentation.common.components.FormDatePickerDialog
import com.lmar.planuraapp.presentation.common.components.FormTextField
import com.lmar.planuraapp.presentation.common.components.FormTimePickerDialog
import com.lmar.planuraapp.presentation.common.components.SingleDropdown
import com.lmar.planuraapp.presentation.navigation.handleUiEvents
import com.lmar.planuraapp.presentation.ui.event.ReminderEditorEvent
import com.lmar.planuraapp.presentation.ui.state.ReminderEditorState
import com.lmar.planuraapp.presentation.viewmodel.ReminderEditorViewModel

@Composable
fun ReminderEditorScreenContainer(
    navController: NavHostController,
    reminderEditorViewModel: ReminderEditorViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    val reminderEditorState by reminderEditorViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        navController.handleUiEvents(
            scope = coroutineScope,
            uiEventFlow = reminderEditorViewModel.eventFlow,
        )
    }

    ReminderEditorScreen(
        reminderEditorState = reminderEditorState,
        onEvent = reminderEditorViewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReminderEditorScreen(
    reminderEditorState: ReminderEditorState = ReminderEditorState(),
    onEvent: (ReminderEditorEvent) -> Unit = {}
) {
    BackHandler(onBack = { onEvent(ReminderEditorEvent.ToBack) })

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            AppBar(
                if (reminderEditorState.reminderId == "0") "Recordatorio" else "Editar Recordatorio",
                onBackAction = { onEvent(ReminderEditorEvent.ToBack) },
                state = rememberTopAppBarState()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                FormTextField(
                    value = reminderEditorState.reminderDescription,
                    label = "Descripción",
                    icon = Icons.Default.EditNote,
                    hasBorder = false,
                    onValueChange = { onEvent(ReminderEditorEvent.SetDescription(it)) },
                )

                Spacer(modifier = Modifier.height(8.dp))

                FormDatePickerDialog(
                    title = "Fecha",
                    initialDateMillis = reminderEditorState.reminderDate,
                    hasBorder = false,
                    onDateSelected = { onEvent(ReminderEditorEvent.SetDate(it)) }
                )

                Spacer(modifier = Modifier.height(8.dp))

                FormTimePickerDialog(
                    title = "Hora",
                    initialHour = reminderEditorState.reminderHour,
                    initialMinute = reminderEditorState.reminderMinute,
                    hasBorder = false,
                    onTimeSelected = { hour, minute ->
                        onEvent(ReminderEditorEvent.SetHour(hour))
                        onEvent(ReminderEditorEvent.SetMinute(minute))
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                SingleDropdown(
                    title = "Tipo",
                    hasBorder = false,
                    options = ReminderType.entries.toTypedArray().map {
                        DropdownOption(
                            it.label,
                            it.name
                        )
                    },
                    selectedOption = DropdownOption(
                        reminderEditorState.reminderType.label,
                        reminderEditorState.reminderType.name
                    ),
                    onOptionSelected = {
                        onEvent(
                            ReminderEditorEvent.SetReminderType(
                                ReminderType.valueOf(
                                    it.value
                                )
                            )
                        )
                    }
                )

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ReminderEditorScreenPreview() {
    PlanuraAppTheme {
        val reminderEditorState = ReminderEditorState(
            reminderDescription = "Recordatorio de prueba",
            reminderDate = System.currentTimeMillis(),
            reminderHour = 12,
            reminderMinute = 0,
            reminderType = ReminderType.DAILY
        )
        ReminderEditorScreen(reminderEditorState)
    }
}