package com.lmar.planuraapp.presentation.ui.event

sealed class HomeEvent {
    object ToProfile: HomeEvent()
    object ToNote: HomeEvent()
    object ToTask: HomeEvent()
    object ToReminder: HomeEvent()
}