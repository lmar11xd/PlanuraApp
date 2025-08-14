package com.lmar.planuraapp.presentation.ui.state

import com.lmar.planuraapp.domain.model.Note

data class NoteState (
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false
)