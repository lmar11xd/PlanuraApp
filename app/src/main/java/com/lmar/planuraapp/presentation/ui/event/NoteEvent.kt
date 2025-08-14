package com.lmar.planuraapp.presentation.ui.event

import com.lmar.planuraapp.presentation.common.components.SnackbarType

sealed class NoteEvent {
    data class ShowMessage(val message: String, val type: SnackbarType): NoteEvent()
    data class ToEditor(val noteId: String): NoteEvent()
}