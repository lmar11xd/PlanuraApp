package com.lmar.planuraapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lmar.planuraapp.presentation.common.event.UiEvent
import com.lmar.planuraapp.presentation.ui.event.HomeEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.ToProfile -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ToProfile)
                }
            }

            HomeEvent.ToNote -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ToNote)
                }
            }

            HomeEvent.ToReminder -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ToReminder)
                }
            }

            HomeEvent.ToTask -> {
                viewModelScope.launch {
                    _eventFlow.emit(UiEvent.ToTask)
                }
            }
        }
    }

}