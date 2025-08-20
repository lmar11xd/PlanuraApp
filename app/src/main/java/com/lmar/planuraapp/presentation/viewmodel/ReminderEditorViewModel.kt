package com.lmar.planuraapp.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmar.planuraapp.core.utils.Constants.PARAM_REMINDERID
import com.lmar.planuraapp.domain.usecase.reminder.AddReminderUseCase
import com.lmar.planuraapp.domain.usecase.reminder.DeleteLogicReminderUseCase
import com.lmar.planuraapp.domain.usecase.reminder.GetReminderByIdUseCase
import com.lmar.planuraapp.domain.usecase.reminder.UpdateReminderUseCase
import com.lmar.planuraapp.presentation.common.components.SnackbarEvent
import com.lmar.planuraapp.presentation.common.event.UiEvent
import com.lmar.planuraapp.presentation.common.event.UiEvent.ShowSnackbar
import com.lmar.planuraapp.presentation.common.event.UiEvent.ToBack
import com.lmar.planuraapp.presentation.ui.event.ReminderEditorEvent
import com.lmar.planuraapp.presentation.ui.state.ReminderEditorState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderEditorViewModel @Inject constructor(
    private val addReminderUseCase: AddReminderUseCase,
    private val updateReminderUseCase: UpdateReminderUseCase,
    private val getReminderByIdUseCase: GetReminderByIdUseCase,
    private val deleteReminderUseCase: DeleteLogicReminderUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _state = MutableStateFlow(ReminderEditorState())
    val state: StateFlow<ReminderEditorState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        // Si noteId es proporcionado, cargar la nota existente
        savedStateHandle.get<String>(PARAM_REMINDERID)?.let { id ->
            loadReminder(id)
        }
    }

    fun onEvent(event: ReminderEditorEvent) {
        when (event) {
            ReminderEditorEvent.DeleteReminder -> {
            }

            ReminderEditorEvent.SaveReminder -> {

            }

            is ReminderEditorEvent.SetDescription -> {
                _state.value = _state.value.copy(
                    reminderDescription = event.description,
                    isReminderModified = true
                )
            }

            is ReminderEditorEvent.SetDate -> {
                _state.value = _state.value.copy(
                    reminderDate = event.date,
                    isReminderModified = true
                )
            }

            is ReminderEditorEvent.SetReminderType -> {
                _state.value = _state.value.copy(
                    reminderType = event.reminderType,
                    isReminderModified = true
                )
            }

            is ReminderEditorEvent.SetHour -> {
                _state.value = _state.value.copy(
                    reminderHour = event.hour,
                    isReminderModified = true
                )
            }

            is ReminderEditorEvent.SetMinute -> {
                _state.value = _state.value.copy(
                    reminderMinute = event.minute,
                    isReminderModified = true
                )
            }

            is ReminderEditorEvent.ShowMessage -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        ShowSnackbar(
                            SnackbarEvent(
                                event.message, event.type
                            )
                        )
                    )
                }
            }

            ReminderEditorEvent.ToBack -> {
                viewModelScope.launch {
                    _eventFlow.emit(ToBack)
                }
            }
        }
    }

    private fun loadReminder(id: String) {
        viewModelScope.launch {
            val reminder = getReminderByIdUseCase(id) ?: return@launch

        }
    }
}