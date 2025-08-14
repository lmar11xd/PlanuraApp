package com.lmar.planuraapp.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmar.planuraapp.core.utils.Constants.PARAM_NOTEID
import com.lmar.planuraapp.core.utils.generateUniqueId
import com.lmar.planuraapp.domain.enums.NoteColorEnum
import com.lmar.planuraapp.domain.model.Note
import com.lmar.planuraapp.domain.usecase.AddNoteUseCase
import com.lmar.planuraapp.domain.usecase.GetNoteByIdUseCase
import com.lmar.planuraapp.domain.usecase.UpdateNoteUseCase
import com.lmar.planuraapp.presentation.common.event.UiEvent
import com.lmar.planuraapp.presentation.ui.event.NoteEditorEvent
import com.lmar.planuraapp.presentation.ui.state.NoteEditorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteEditorViewModel @Inject constructor(
    private val addNoteUseCase: AddNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(NoteEditorState())
    val state: StateFlow<NoteEditorState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var noteId = ""

    init {
        // Si noteId es proporcionado, cargar la nota existente
        savedStateHandle.get<String>(PARAM_NOTEID)?.let { id ->
            loadNote(id)
        }
    }

    fun onEvent(event: NoteEditorEvent) {
        when (event) {
            is NoteEditorEvent.SetColor -> {
                _state.value = _state.value.copy(noteColor = event.color)
            }

            is NoteEditorEvent.SetContent -> {
                _state.value = _state.value.copy(noteContent = event.content)
            }

            is NoteEditorEvent.SetTitle -> {
                _state.value = _state.value.copy(noteTitle = event.title)
            }

            is NoteEditorEvent.ShowMessage -> {

            }

            NoteEditorEvent.DeleteNote -> {

            }

            NoteEditorEvent.SaveNote -> {
                saveNote()
            }

            NoteEditorEvent.ShowColorPicker -> {

            }

            NoteEditorEvent.ToBack -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ToBack)
                }
            }
        }
    }

    private fun loadNote(id: String) {
        noteId = id

        viewModelScope.launch {
            val note = getNoteByIdUseCase(id) ?: return@launch

            _state.value = _state.value.copy(
                noteTitle = note.title ?: "",
                noteContent = note.content ?: "",
                noteColor = NoteColorEnum.valueOf(note.color),
                noteLastModified = note.updatedAt
            )
        }
    }

    private fun saveNote() {
        if (_state.value.noteTitle.isEmpty() && _state.value.noteContent.isEmpty()) {
            onEvent(NoteEditorEvent.ToBack)
        }

        val timestamp = System.currentTimeMillis()
        if (noteId == "0") {
            val note = Note(
                id = generateUniqueId(),
                title = _state.value.noteTitle,
                content = _state.value.noteContent,
                color = _state.value.noteColor.name,
                createdAt = timestamp,
                updatedAt = timestamp
            )

            addNoteUseCase(
                note,
                onSuccess = { onEvent(NoteEditorEvent.ToBack) }
            )
        } else {
            val note = Note(
                id = noteId,
                title = _state.value.noteTitle,
                content = _state.value.noteContent,
                color = _state.value.noteColor.name,
                createdAt = timestamp, // No se actualiza createdAt
                updatedAt = timestamp
            )

            updateNoteUseCase(
                note,
                onSuccess = { onEvent(NoteEditorEvent.ToBack) }
            )
        }
    }
}