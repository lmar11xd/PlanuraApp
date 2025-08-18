package com.lmar.planuraapp.presentation.ui.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.lmar.planuraapp.core.ui.theme.PlanuraAppTheme
import com.lmar.planuraapp.presentation.common.components.AppBar
import com.lmar.planuraapp.presentation.common.components.ColorPickerBottomSheet
import com.lmar.planuraapp.presentation.common.components.Dialog
import com.lmar.planuraapp.presentation.common.components.DialogType
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
    /* Recomendación:
    * No es buena práctica meter FocusRequester dentro de tu NoteEditorState,
    * porque FocusRequester es un objeto de UI (un Modifier de Compose),
    * no de estado lógico de tu editor de notas.
    * Por eso lo manejamos aquí en el composable.
    */
    val focusRequesterTitle = remember { FocusRequester() }
    val focusRequesterContent = remember { FocusRequester() }

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

                    if (noteEditorState.noteId != "0") {
                        IconButton(
                            onClick = { onEvent(NoteEditorEvent.DeleteNote) },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Note",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            )

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                PlanuraTextField(
                    value = noteEditorState.noteTitle,
                    onValueChange = { onEvent(NoteEditorEvent.SetTitle(it)) },
                    placeholder = "Título",
                    modifier = Modifier.focusRequester(focusRequesterTitle),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusRequesterContent.requestFocus() }
                    )
                )

                PlanuraTextArea(
                    value = noteEditorState.noteContent,
                    onValueChange = { onEvent(NoteEditorEvent.SetContent(it)) },
                    placeholder = "Escribe algo aquí...",
                    modifier = Modifier.focusRequester(focusRequesterContent),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    )
                )
            }
        }

        Dialog(
            title = "Eliminar Nota",
            message = "¿Estás seguro de que quieres eliminar esta nota?",
            type = DialogType.DELETE,
            showDialog = noteEditorState.isNoteDeletedDialogVisible,
            onDismiss = { onEvent(NoteEditorEvent.HideNoteDeletedDialog) },
            onConfirm = { onEvent(NoteEditorEvent.ConfirmDeleteNote) }
        )

        ColorPickerBottomSheet(
            show = noteEditorState.isColorPickerVisible,
            selectedColor = noteEditorState.noteColor,
            onDismiss = { onEvent(NoteEditorEvent.ShowColorPicker) },
            onColorSelected = { onEvent(NoteEditorEvent.SetColor(it)) }
        )
    }
}

@Preview
@Composable
fun NoteEditorScreenPreview() {
    val noteEditorState = NoteEditorState(
        noteId = "1",
        noteTitle = "Título de la nota",
        noteContent = "Contenido de la nota"
    )

    PlanuraAppTheme {
        NoteEditorScreen(noteEditorState)
    }
}