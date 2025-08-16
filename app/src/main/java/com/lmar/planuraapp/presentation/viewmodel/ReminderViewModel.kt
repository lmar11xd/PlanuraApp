package com.lmar.planuraapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmar.planuraapp.domain.usecase.reminder.GetRemindersUseCase
import com.lmar.planuraapp.presentation.common.components.SnackbarEvent
import com.lmar.planuraapp.presentation.common.components.SnackbarType
import com.lmar.planuraapp.presentation.common.event.UiEvent
import com.lmar.planuraapp.presentation.common.event.UiEvent.ShowSnackbar
import com.lmar.planuraapp.presentation.ui.event.ReminderEvent
import com.lmar.planuraapp.presentation.ui.state.ReminderState
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
class ReminderViewModel @Inject constructor(
    private val getRemindersUseCase: GetRemindersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ReminderState())
    val state: StateFlow<ReminderState> = _state

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getReminders()
    }

    fun onEvent(event: ReminderEvent) {
        when (event) {
            is ReminderEvent.ShowMessage -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        ShowSnackbar(
                            SnackbarEvent(event.message, event.type)
                        )
                    )
                }
            }

            is ReminderEvent.ToEditor -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ToReminderEditor(event.reminderId))
                }
            }
        }
    }

    private fun getReminders() {
        viewModelScope.launch {
            getRemindersUseCase()
                .onStart { _state.value = _state.value.copy(isLoading = true) }
                .catch { error ->
                    _state.value = _state.value.copy(isLoading = false)
                    _eventFlow.emit(
                        ShowSnackbar(
                            SnackbarEvent(
                                error.message ?: "Error desconocido",
                                SnackbarType.ERROR
                            )
                        )
                    )
                }
                .collect { reminders ->
                    _state.value = _state.value.copy(reminders = reminders, isLoading = false)
                }
        }

    }
}