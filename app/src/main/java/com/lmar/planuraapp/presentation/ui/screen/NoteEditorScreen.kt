package com.lmar.planuraapp.presentation.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lmar.planuraapp.presentation.common.components.AppBar
import com.lmar.planuraapp.presentation.common.components.ColorPickerBottomSheet
import com.lmar.planuraapp.presentation.navigation.handleUiEvents
import com.lmar.planuraapp.presentation.ui.component.PlanuraTextArea
import com.lmar.planuraapp.presentation.ui.component.PlanuraTextField
import com.lmar.planuraapp.presentation.ui.event.NoteEditorEvent
import com.lmar.planuraapp.presentation.ui.state.NoteEditorState
import com.lmar.planuraapp.presentation.viewmodel.NoteEditorViewModel

@Composable
fun NoteEditorScreenContainer(
    navController: NavHostController,
    noteEditorViewModel: NoteEditorViewModel = hiltViewModel()
) {
    val coroutineScope = rememberCoroutineScope()

    val noteEditorState by noteEditorViewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        navController.handleUiEvents(
            scope = coroutineScope,
            uiEventFlow = noteEditorViewModel.eventFlow,
        )
    }

    NoteEditorScreen(
        noteEditorState = noteEditorState,
        onEvent = noteEditorViewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NoteEditorScreen(
    noteEditorState: NoteEditorState = NoteEditorState(),
    onEvent: (NoteEditorEvent) -> Unit = {}
) {
    BackHandler(onBack = { onEvent(NoteEditorEvent.SaveNote) })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(noteEditorState.noteColor.container))
    ) {
        Column {
            AppBar(
                "Nota",
                onBackAction = { onEvent(NoteEditorEvent.SaveNote) },
                state = rememberTopAppBarState(),
                actions = {
                    IconButton(
                        onClick = { onEvent(NoteEditorEvent.ShowColorPicker) },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Palette,
                            contentDescription = "Select Color",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                PlanuraTextField(
                    value = noteEditorState.noteTitle,
                    onValueChange = { onEvent(NoteEditorEvent.SetTitle(it)) },
                    placeholder = "Título"
                )

                PlanuraTextArea(
                    value = noteEditorState.noteContent,
                    onValueChange = { onEvent(NoteEditorEvent.SetContent(it)) },
                    placeholder = "Escribe algo aquí..."
                )
            }
        }

        ColorPickerBottomSheet(
            show = noteEditorState.isColorPickerVisible,
            selectedColor = noteEditorState.noteColor,
            onDismiss = { onEvent(NoteEditorEvent.ShowColorPicker) },
            onColorSelected = { onEvent(NoteEditorEvent.SetColor(it)) }
        )
    }
}