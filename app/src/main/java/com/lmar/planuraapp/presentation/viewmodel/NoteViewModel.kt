package com.lmar.planuraapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmar.planuraapp.domain.usecase.GetNotesUseCase
import com.lmar.planuraapp.presentation.common.components.SnackbarEvent
import com.lmar.planuraapp.presentation.common.components.SnackbarType
import com.lmar.planuraapp.presentation.common.event.UiEvent
import com.lmar.planuraapp.presentation.ui.event.NoteEvent
import com.lmar.planuraapp.presentation.ui.state.NoteState
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
class NoteViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(NoteState())
    val state: StateFlow<NoteState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getNotes()
    }

    private fun getNotes() {
        viewModelScope.launch {
            getNotesUseCase()
                .onStart {
                    _state.value = _state.value.copy(isLoading = true)
                }
                .catch { error ->
                    _state.value = _state.value.copy(isLoading = false)
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            SnackbarEvent(
                                error.message ?: "Error desconocido",
                                SnackbarType.ERROR
                            )
                        )
                    )
                }
                .collect { notes ->
                    _state.value = _state.value.copy(notes = notes, isLoading = false)
                }
        }
    }

    fun onEvent(event: NoteEvent) {
        when (event) {
            is NoteEvent.ShowMessage -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        UiEvent.ShowSnackbar(
                            SnackbarEvent(event.message, event.type)
                        )
                    )
                }
            }

            is NoteEvent.ToEditor -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ToNoteEditor(event.noteId))
                }
            }
        }
    }
}